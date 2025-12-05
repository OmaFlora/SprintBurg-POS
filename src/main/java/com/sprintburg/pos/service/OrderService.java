package com.sprintburg.pos.service;

import com.sprintburg.pos.dto.OrderItemRequest;
import com.sprintburg.pos.dto.OrderRequest;
import com.sprintburg.pos.dto.OrderTotalResponse;
import com.sprintburg.pos.exception.InsufficientStockException;
import com.sprintburg.pos.model.Product;
import com.sprintburg.pos.model.Sale;
import com.sprintburg.pos.model.SaleItem;
import com.sprintburg.pos.repository.ProductRepository;
import com.sprintburg.pos.repository.SaleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;

    private static final BigDecimal EXTRA_INGREDIENT_COST = new BigDecimal("0.50");
    private static final BigDecimal TAX_RATE = new BigDecimal("0.16");

    public OrderService(ProductRepository productRepository, SaleRepository saleRepository) {
        this.productRepository = productRepository;
        this.saleRepository = saleRepository;
    }

    public OrderTotalResponse calculateOrderTotal(OrderRequest request) {

        validateOrderRequest(request);

        List<Long> allProductIds = request.getItems().stream()
                .flatMap(item -> {
                    List<Long> ids = new ArrayList<>();
                    ids.add(item.getProductId());
                    if (item.getAddIngredients() != null) ids.addAll(item.getAddIngredients());
                    return ids.stream();
                })
                .collect(Collectors.toList());

        Map<Long, Product> productMap = productRepository.findAllById(allProductIds).stream()
                .collect(Collectors.toMap(Product::getId, p -> p));

        BigDecimal preDiscountSubtotal = BigDecimal.ZERO;

        for (OrderItemRequest item : request.getItems()) {
            Product baseProduct = productMap.get(item.getProductId());
            if (baseProduct == null) {
                throw new IllegalArgumentException("Producto base no encontrado con ID: " + item.getProductId());
            }

            BigDecimal itemPrice = baseProduct.getPrice();

            if (item.getAddIngredients() != null) {
                for (Long extraId : item.getAddIngredients()) {
                    itemPrice = itemPrice.add(EXTRA_INGREDIENT_COST);
                }
            }

            preDiscountSubtotal = preDiscountSubtotal.add(itemPrice.multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        BigDecimal discountAmount = BigDecimal.ZERO; // Siempre cero
        BigDecimal finalSubtotal = preDiscountSubtotal;

        BigDecimal taxes = finalSubtotal.multiply(TAX_RATE).setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = finalSubtotal.add(taxes).setScale(2, RoundingMode.HALF_UP);

        return new OrderTotalResponse(discountAmount, finalSubtotal, taxes, total);
    }

    @Transactional
    public Sale processPaymentAndSaveOrder(OrderRequest request) {

        OrderTotalResponse totals = calculateOrderTotal(request);

        BigDecimal totalPaid = (request.getCashAmount() != null ? request.getCashAmount() : BigDecimal.ZERO)
                .add(request.getCardAmount() != null ? request.getCardAmount() : BigDecimal.ZERO);

        if (totalPaid.compareTo(totals.getTotal()) < 0) {
            throw new IllegalStateException("El monto pagado (" + totalPaid + ") es insuficiente para cubrir el total (" + totals.getTotal() + ")");
        }

        checkAndDecreaseStock(request);

        Sale newSale = createSaleEntity(request, totals);

        newSale.setItems(createSaleItems(request, newSale));

        return saleRepository.save(newSale);
    }

    private void validateOrderRequest(OrderRequest request) {
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new IllegalArgumentException("El pedido debe contener al menos un artículo.");
        }
        if (request.getEmployeeId() == null) {
            throw new IllegalArgumentException("El ID del empleado es requerido para la auditoría de ventas.");
        }
        for (OrderItemRequest item : request.getItems()) {
            if (item.getProductId() == null || item.getQuantity() <= 0) {
                throw new IllegalArgumentException("Producto ID y Cantidad deben ser válidos.");
            }
        }
    }

    private Sale createSaleEntity(OrderRequest request, OrderTotalResponse totals) {
        Sale sale = new Sale();
        sale.setEmployeeId(request.getEmployeeId());
        sale.setCustomerId(request.getCustomerId());
        sale.setSaleDate(LocalDateTime.now());
        sale.setSubtotal(totals.getFinalSubtotal()); // El subtotal registrado es el final (post-descuento)
        sale.setDiscount(totals.getDiscountAmount());
        sale.setTaxes(totals.getTaxes());
        sale.setTotal(totals.getTotal());

        BigDecimal cash = request.getCashAmount() != null ? request.getCashAmount() : BigDecimal.ZERO;
        BigDecimal card = request.getCardAmount() != null ? request.getCardAmount() : BigDecimal.ZERO;

        sale.setPaymentMethod(
                cash.compareTo(BigDecimal.ZERO) > 0 && card.compareTo(BigDecimal.ZERO) == 0 ? "CASH" :
                        card.compareTo(BigDecimal.ZERO) > 0 && cash.compareTo(BigDecimal.ZERO) == 0 ? "CARD" :
                                "OTHER"
        );
        sale.setCashReceived(cash);

        BigDecimal totalPaid = cash.add(card);
        BigDecimal change = totalPaid.subtract(totals.getTotal());
        sale.setChangeGiven(change.compareTo(BigDecimal.ZERO) > 0 ? change : BigDecimal.ZERO);
        sale.setTicketNumber(request.getTicketNumber());

        return sale;
    }

    private List<SaleItem> createSaleItems(OrderRequest request, Sale sale) {

        List<SaleItem> items = new ArrayList<>();

        for (OrderItemRequest itemRequest : request.getItems()) {

            BigDecimal calculatedPricePerUnit = calculatePricePerUnit(itemRequest);

            SaleItem saleItem = new SaleItem();
            saleItem.setSale(sale);
            saleItem.setProductId(itemRequest.getProductId());
            saleItem.setQuantity(itemRequest.getQuantity());
            saleItem.setUnitPriceAtSale(calculatedPricePerUnit);

            String customization = "ADD: " + itemRequest.getAddIngredients() + " | REMOVE: " + itemRequest.getRemoveIngredients();
            saleItem.setCustomizationDetails(customization);

            items.add(saleItem);
        }
        return items;
    }

    private BigDecimal calculatePricePerUnit(OrderItemRequest itemRequest) {
        BigDecimal price = new BigDecimal("5.00");
        if (itemRequest.getAddIngredients() != null) {
            price = price.add(EXTRA_INGREDIENT_COST.multiply(BigDecimal.valueOf(itemRequest.getAddIngredients().size())));
        }
        return price;
    }


    private void checkAndDecreaseStock(OrderRequest request) {
        Map<Long, Integer> itemsToDecrease = request.getItems().stream()
                .collect(Collectors.groupingBy(
                        OrderItemRequest::getProductId,
                        Collectors.summingInt(OrderItemRequest::getQuantity)
                ));

        List<Product> productsToUpdate = productRepository.findAllById(itemsToDecrease.keySet());

        for (Product product : productsToUpdate) {
            Integer requiredQuantity = itemsToDecrease.get(product.getId());
            if (product.getStock() == null || product.getStock() < requiredQuantity) {
                throw new InsufficientStockException("Stock insuficiente para: " + product.getName() +
                        ". Stock actual: " + product.getStock() +
                        ", Requerido: " + requiredQuantity);
            }
            product.setStock(product.getStock() - requiredQuantity);
        }

        productRepository.saveAll(productsToUpdate);
    }
}