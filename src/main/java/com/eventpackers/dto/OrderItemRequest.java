package com.eventpackers.dto;

public class OrderItemRequest {
    private Long itemId;
    private Long subItemId;
    private int quantity;

    // Getter and Setter for itemId
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    // Getter and Setter for subItemId
    public Long getSubItemId() {
        return subItemId;
    }

    public void setSubItemId(Long subItemId) {
        this.subItemId = subItemId;
    }

    // Getter and Setter for quantity
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
