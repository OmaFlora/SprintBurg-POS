package com.sprintburg.pos.controller;

import com.sprintburg.pos.model.Sale;
import com.sprintburg.pos.service.SaleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
public class SaleJSPController {

    private final SaleService saleService;

    public SaleJSPController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping("/history")
    public String showHistoryPage(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Model model) {

        if (startDate == null) startDate = LocalDate.now();
        if (endDate == null) endDate = LocalDate.now();

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        List<Sale> sales = saleService.findSalesByDateRange(startDateTime, endDateTime);

        BigDecimal totalRevenue = sales.stream()
                .map(Sale::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("sales", sales);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("startDate", startDate); // Para mantener el input lleno
        model.addAttribute("endDate", endDate);     // Para mantener el input lleno

        return "history"; // Busca history.jsp
    }
}