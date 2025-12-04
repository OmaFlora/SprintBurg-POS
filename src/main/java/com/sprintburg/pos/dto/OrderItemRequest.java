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

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public List<Long> getAddIngredients() {
        return addIngredients;
    }


    public List<Long> getRemoveIngredients() {
        return removeIngredients;
    }
}