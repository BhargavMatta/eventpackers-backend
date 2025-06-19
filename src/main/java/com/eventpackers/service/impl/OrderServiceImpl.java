package com.eventpackers.service.impl;

import com.eventpackers.dto.OrderItemRequest;
import com.eventpackers.dto.OrderRequest;
import com.eventpackers.dto.OrderResponse;
import com.eventpackers.model.*;
import com.eventpackers.repository.*;
import com.eventpackers.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private SubItemRepository subItemRepository;

    @Override
    public OrderResponse createOrder(OrderRequest request) {
        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setStatus("Pending");
        order.setDeliveryDateTime(request.getDeliveryDateTime());
        order.setDoorNumber(request.getDoorNumber());
        order.setLandmark(request.getLandmark());
        order.setPincode(request.getPincode());

        List<OrderItem> orderItems = request.getOrderItems().stream().map(itemRequest -> {
            OrderItem item = new OrderItem();
            item.setItem(itemRepository.findById(itemRequest.getItemId()).orElse(null));
            item.setSubItem(subItemRepository.findById(itemRequest.getSubItemId()).orElse(null));
            item.setQuantity(itemRequest.getQuantity());
            item.setOrder(order);
            return item;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);

        Order savedOrder = orderRepository.save(order);
        return mapToOrderResponse(savedOrder);
    }

    @Override
    public OrderResponse updateOrder(Long id, OrderRequest request) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) return null;

        order.setStatus(request.getStatus());
        order.setDeliveryDateTime(request.getDeliveryDateTime());
        order.setDoorNumber(request.getDoorNumber());
        order.setLandmark(request.getLandmark());
        order.setPincode(request.getPincode());

        Order savedOrder = orderRepository.save(order);
        return mapToOrderResponse(savedOrder);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream().map(this::mapToOrderResponse).collect(Collectors.toList());
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        return orderRepository.findById(id).map(this::mapToOrderResponse).orElse(null);
    }

    private OrderResponse mapToOrderResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setUserId(order.getUserId());
        response.setStatus(order.getStatus());
        response.setDeliveryDateTime(order.getDeliveryDateTime());
        response.setDoorNumber(order.getDoorNumber());
        response.setLandmark(order.getLandmark());
        response.setPincode(order.getPincode());

        response.setOrderItems(
                order.getOrderItems().stream().map(orderItem -> {
                    var res = new com.eventpackers.dto.OrderItemResponse();
                    res.setId(orderItem.getId());
                    res.setItemId(orderItem.getItem() != null ? orderItem.getItem().getId() : null);
                    res.setItemName(orderItem.getItem() != null ? orderItem.getItem().getName() : null);
                    res.setSubItemId(orderItem.getSubItem() != null ? orderItem.getSubItem().getId() : null);
                    res.setSubItemName(orderItem.getSubItem() != null ? orderItem.getSubItem().getName() : null);
                    res.setSubItemPrice(orderItem.getSubItem() != null ? orderItem.getSubItem().getPrice() : 0.0);
                    res.setQuantity(orderItem.getQuantity());
                    res.setTotalPrice(orderItem.getSubItem() != null
                            ? orderItem.getSubItem().getPrice() * orderItem.getQuantity()
                            : 0.0);
                    return res;
                }).collect(Collectors.toList())
        );

        return response;
    }
}
