package com.sprintburg.pos.service;

import com.sprintburg.pos.dto.OrderItemRequest;
import com.sprintburg.pos.dto.OrderRequest;
import com.sprintburg.pos.dto.OrderTotalResponse;
import com.sprintburg.pos.model.Product;
import com.sprintburg.pos.repository.ProductRepository;
import com.sprintburg.pos.repository.SaleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private SaleRepository saleRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void testCalculateOrderTotal() {
        Product burger = new Product();
        burger.setId(1L);
        burger.setName("Hamburguesa Test");
        burger.setPrice(new BigDecimal("10.00"));
        burger.setStock(100);

        List<Product> productList = List.of(burger);
        Mockito.when(productRepository.findAllById(Mockito.anyList())).thenReturn(productList);

        OrderItemRequest item = new OrderItemRequest();
        item.setProductId(1L);
        item.setQuantity(2);
        item.setAddIngredients(new ArrayList<>());

        OrderRequest request = new OrderRequest();
        request.setItems(List.of(item));
        request.setEmployeeId(1L);
        request.setDiscountCode("");

        OrderTotalResponse response = orderService.calculateOrderTotal(request);

        Assertions.assertEquals(new BigDecimal("23.20"), response.getTotal());

        System.out.println("✅ Test de cálculo matemático aprobado");
    }
}