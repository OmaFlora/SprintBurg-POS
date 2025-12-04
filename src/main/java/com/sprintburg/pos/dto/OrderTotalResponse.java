package com.sprintburg.pos.dto;

import java.math.BigDecimal;

public class OrderTotalResponse {

    private BigDecimal discountAmount;

    private BigDecimal finalSubtotal;

    private BigDecimal taxes;

    private BigDecimal total;

    public OrderTotalResponse(BigDecimal discountAmount, BigDecimal finalSubtotal, BigDecimal taxes, BigDecimal total) {
        this.discountAmount = discountAmount;
        this.finalSubtotal = finalSubtotal;
        this.taxes = taxes;
        this.total = total;
    }

    public OrderTotalResponse() {
    }

    // --- Getters y Setters ---

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public BigDecimal getFinalSubtotal() {
        return finalSubtotal;
    }

    public BigDecimal getTaxes() {
        return taxes;
    }

    public BigDecimal getTotal() {
        return total;
    }
}