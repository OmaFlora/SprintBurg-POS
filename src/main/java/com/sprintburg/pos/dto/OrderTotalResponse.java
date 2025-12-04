package com.sprintburg.pos.dto;

import java.math.BigDecimal;

public class OrderTotalResponse {

    private BigDecimal preDiscountSubtotal;

    private BigDecimal discountAmount;

    private BigDecimal finalSubtotal;

    private BigDecimal taxes;

    private BigDecimal total;

    public OrderTotalResponse(BigDecimal preDiscountSubtotal, BigDecimal discountAmount, BigDecimal finalSubtotal, BigDecimal taxes, BigDecimal total) {
        this.preDiscountSubtotal = preDiscountSubtotal;
        this.discountAmount = discountAmount;
        this.finalSubtotal = finalSubtotal;
        this.taxes = taxes;
        this.total = total;
    }

    public OrderTotalResponse() {
    }

    // --- Getters y Setters ---

    public BigDecimal getPreDiscountSubtotal() {
        return preDiscountSubtotal;
    }

    public void setPreDiscountSubtotal(BigDecimal preDiscountSubtotal) {
        this.preDiscountSubtotal = preDiscountSubtotal;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getFinalSubtotal() {
        return finalSubtotal;
    }

    public void setFinalSubtotal(BigDecimal finalSubtotal) {
        this.finalSubtotal = finalSubtotal;
    }

    public BigDecimal getTaxes() {
        return taxes;
    }

    public void setTaxes(BigDecimal taxes) {
        this.taxes = taxes;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}