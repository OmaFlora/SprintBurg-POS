package com.sprintburg.pos.dto;

import java.util.List;

public class OrderItemRequest {

    private Long productId;
    private int quantity;
    private List<Long> addIngredients;
    private List<Long> removeIngredients;

    public OrderItemRequest(Long productId, int quantity, List<Long> addIngredients, List<Long> removeIngredients) {
        this.productId = productId;
        this.quantity = quantity;
        this.addIngredients = addIngredients;
        this.removeIngredients = removeIngredients;
    }

    public OrderItemRequest() {

    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<Long> getAddIngredients() {
        return addIngredients;
    }

    public void setAddIngredients(List<Long> addIngredients) {
        this.addIngredients = addIngredients;
    }

    public List<Long> getRemoveIngredients() {
        return removeIngredients;
    }

    public void setRemoveIngredients(List<Long> removeIngredients) {
        this.removeIngredients = removeIngredients;
    }
}