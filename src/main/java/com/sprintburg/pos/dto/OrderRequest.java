package com.sprintburg.pos.dto;

import java.math.BigDecimal;
import java.util.List;

public class OrderRequest {

    private List<OrderItemRequest> items;

    private Long employeeId;

    private Long customerId;

    private String discountCode;

    private BigDecimal cardAmount;

    private BigDecimal cashAmount;

    // --- Constructor ---

    public OrderRequest(List<OrderItemRequest> items, Long employeeId, Long customerId, String discountCode, BigDecimal cardAmount, BigDecimal cashAmount) {
        this.items = items;
        this.employeeId = employeeId;
        this.customerId = customerId;
        this.discountCode = discountCode;
        this.cardAmount = cardAmount;
        this.cashAmount = cashAmount;
    }

    // --- Getters y Setters ---

    public List<OrderItemRequest> getItems() {
        return items;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public BigDecimal getCardAmount() {
        return cardAmount;
    }

    public BigDecimal getCashAmount() {
        return cashAmount;
    }

    private String ticketNumber;

    public String getTicketNumber() { return ticketNumber; }

}