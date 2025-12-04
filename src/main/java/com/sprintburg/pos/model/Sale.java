package com.sprintburg.pos.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "sale")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime saleDate;

    @Column(nullable = false)
    private Long employeeId;

    private Long customerId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal discount = BigDecimal.ZERO;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal taxes;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Column(nullable = false, length = 10)
    private String paymentMethod;

    @Column(precision = 10, scale = 2)
    private BigDecimal cashReceived = BigDecimal.ZERO;

    @Column(precision = 10, scale = 2)
    private BigDecimal changeGiven = BigDecimal.ZERO;


    @Column(name = "ticket_number")
    private String ticketNumber; // <--- NUEVO CAMPO

    public String getTicketNumber() { return ticketNumber; }
    public void setTicketNumber(String ticketNumber) { this.ticketNumber = ticketNumber; }

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SaleItem> items;

    public Sale() {
    }

    public Long getId() {
        return id;
    }

    public void setSaleDate(LocalDateTime saleDate) {
        this.saleDate = saleDate;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public void setTaxes(BigDecimal taxes) {
        this.taxes = taxes;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setCashReceived(BigDecimal cashReceived) {
        this.cashReceived = cashReceived;
    }

    public void setChangeGiven(BigDecimal changeGiven) {
        this.changeGiven = changeGiven;
    }

    public void setItems(List<SaleItem> items) {
        this.items = items;
        if (items != null) {
            items.forEach(item -> item.setSale(this));
        }
    }
}