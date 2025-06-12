package com.eventpackers.controller;

import com.eventpackers.dto.OrderRequest;
import com.eventpackers.dto.OrderResponse;
import com.eventpackers.dto.OrderItemRequest;
import com.eventpackers.model.Order;
import com.eventpackers.model.OrderItem;
import com.eventpackers.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping
    public Order placeOrder(@RequestBody OrderRequest request) {
        Order order = new Order();
        order.setCustomerName(request.getCustomerName());
        order.setDoorNumber(request.getDoorNumber());
        order.setLandmark(request.getLandmark());
        order.setPincode(request.getPincode());
        order.setTotalAmount(request.getTotalAmount());

        List<OrderItem> items = request.getItems().stream().map(itemRequest -> {
            OrderItem item = new OrderItem();
            item.setItemName(itemRequest.getItemName());
            item.setPrice(itemRequest.getPrice());
            item.setQuantity(itemRequest.getQuantity());
            item.setServiceName(itemRequest.getServiceName());
            item.setOrder(order);
            return item;
        }).collect(Collectors.toList());

        order.setItems(items);
        return orderRepository.save(order);
    }

    @GetMapping
    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(OrderResponse::new).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OrderResponse getOrderById(@PathVariable Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        return new OrderResponse(order);
    }

    @PutMapping("/{id}/status")
    public String updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            return "Order not found";
        }
        order.setOrderStatus(status);
        orderRepository.save(order);
        return "Order status updated successfully";
    }

    @PutMapping("/{id}/delivery-time")
    public String updateDeliveryTime(@PathVariable Long id, @RequestParam String deliveryDateTime) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            return "Order not found";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime parsedDateTime = LocalDateTime.parse(deliveryDateTime, formatter);
        order.setDeliveryDateTime(parsedDateTime);

        orderRepository.save(order);
        return "Delivery time updated successfully";
    }
}
