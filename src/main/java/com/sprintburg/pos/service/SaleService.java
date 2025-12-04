package com.sprintburg.pos.service;

import com.sprintburg.pos.model.Sale;
import com.sprintburg.pos.repository.SaleRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SaleService {

    private final SaleRepository saleRepository;

    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public List<Sale> findSalesByDateRange(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null || start.isAfter(end)) {
            throw new IllegalArgumentException("El rango de fechas no es válido.");
        }
        return saleRepository.findBySaleDateBetween(start, end);
    }

    public Sale findSaleById(Long id) {
        Optional<Sale> sale = saleRepository.findById(id);
        if (sale.isEmpty()) {
            throw new IllegalArgumentException("Venta no encontrada con ID: " + id);
        }
        return sale.get();
    }

    public Map<String, BigDecimal> getFinancialSummary(LocalDateTime start, LocalDateTime end) {
        BigDecimal totalRevenue = saleRepository.sumTotalByDateRange(start, end);

        BigDecimal totalDiscount = saleRepository.sumDiscountByDateRange(start, end);

        totalRevenue = totalRevenue != null ? totalRevenue : BigDecimal.ZERO;
        totalDiscount = totalDiscount != null ? totalDiscount : BigDecimal.ZERO;

        Map<String, BigDecimal> summary = new HashMap<>();
        summary.put("totalRevenue", totalRevenue);
        summary.put("totalDiscount", totalDiscount);
        summary.put("netSales", totalRevenue.subtract(totalDiscount));

        return summary;
    }
}