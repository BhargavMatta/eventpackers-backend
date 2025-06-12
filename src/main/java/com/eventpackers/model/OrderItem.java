package com.eventpackers.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;
    private double price;
    private int quantity;

    private String serviceName; // Optional: set only if item was selected via service

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
