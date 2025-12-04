package com.sprintburg.pos.controller;

import com.sprintburg.pos.model.Sale;
import com.sprintburg.pos.service.SaleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping("/history")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER')")
    public ResponseEntity<List<Sale>> getSalesHistory(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {

        List<Sale> sales = saleService.findSalesByDateRange(start, end);
        return ResponseEntity.ok(sales);
    }

    @GetMapping("/{saleId}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER')")
    public ResponseEntity<Sale> getSaleDetails(@PathVariable Long saleId) {
        Sale sale = saleService.findSaleById(saleId);
        return ResponseEntity.ok(sale);
    }

    @GetMapping("/reports/summary")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Map<String, BigDecimal>> getFinancialSummary(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {

        Map<String, BigDecimal> summary = saleService.getFinancialSummary(start, end);
        return ResponseEntity.ok(summary);
    }
}