package com.sprintburg.pos.controller;

import com.sprintburg.pos.dto.OrderRequest;
import com.sprintburg.pos.dto.OrderTotalResponse;
import com.sprintburg.pos.model.Sale;
import com.sprintburg.pos.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/calculate")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER')")
    public ResponseEntity<OrderTotalResponse> calculateTotal(@RequestBody OrderRequest orderRequest) {
        OrderTotalResponse totalResponse = orderService.calculateOrderTotal(orderRequest);
        return ResponseEntity.ok(totalResponse);
    }
    @PostMapping("/pay")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER')")
    public ResponseEntity<Sale> processPayment(@RequestBody OrderRequest orderRequest) {
        Sale recordedSale = orderService.processPaymentAndSaveOrder(orderRequest);

        recordedSale.setItems(null);

        return ResponseEntity.ok(recordedSale);
    }
}