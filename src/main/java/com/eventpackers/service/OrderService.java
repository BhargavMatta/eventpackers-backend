package com.eventpackers.service;

import com.eventpackers.dto.OrderRequest;
import com.eventpackers.dto.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderRequest request);

    OrderResponse updateOrder(Long id, OrderRequest request);

    void deleteOrder(Long id);

    List<OrderResponse> getAllOrders();

    OrderResponse getOrderById(Long id);
}
