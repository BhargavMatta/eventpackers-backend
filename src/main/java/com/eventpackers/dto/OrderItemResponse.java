package com.eventpackers.dto;

import com.eventpackers.model.OrderItem;

public class OrderItemResponse {
    private Long id;
    private String itemName;
    private double price;
    private int quantity;
    private String serviceName;

    public OrderItemResponse() {}

    public OrderItemResponse(OrderItem item) {
        this.id = item.getId();
        this.itemName = item.getItemName();
        this.price = item.getPrice(); // Use double
        this.quantity = item.getQuantity();
        this.serviceName = item.getServiceName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
