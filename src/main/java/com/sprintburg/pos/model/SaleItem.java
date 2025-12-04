package com.sprintburg.pos.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;

@Entity
@Table(name = "sale_item")
public class SaleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id", nullable = false)
    @JsonIgnore
    private Sale sale;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPriceAtSale;

    @Column(length = 500)
    private String customizationDetails;

    public SaleItem() {
    }

    public Long getId() {
        return id;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setUnitPriceAtSale(BigDecimal unitPriceAtSale) {
        this.unitPriceAtSale = unitPriceAtSale;
    }

    public void setCustomizationDetails(String customizationDetails) {
        this.customizationDetails = customizationDetails;
    }
}