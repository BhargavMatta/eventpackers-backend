package com.eventpackers.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderRequest {
    private String customerName;
    private String doorNumber;
    private String landmark;
    private String pincode;
    private int totalAmount; // ✅ Added totalAmount field
    private LocalDateTime deliveryDateTime;
    private List<OrderItemRequest> items;

    // Getters and setters
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDoorNumber() {
        return doorNumber;
    }

    public void setDoorNumber(String doorNumber) {
        this.doorNumber = doorNumber;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public int getTotalAmount() { // ✅ Getter
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) { // ✅ Setter
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getDeliveryDateTime() {
        return deliveryDateTime;
    }

    public void setDeliveryDateTime(LocalDateTime deliveryDateTime) {
        this.deliveryDateTime = deliveryDateTime;
    }

    public List<OrderItemRequest> getItems() {
        return items;
    }

    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }
}
