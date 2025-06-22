package com.adityagiri.ecommerce_app.controller;

import com.adityagiri.ecommerce_app.dto.order.CreateOrderDTO;
import com.adityagiri.ecommerce_app.dto.order.OrderResponseDTO;
import com.adityagiri.ecommerce_app.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create/{buyerId}")
    public OrderResponseDTO createOrder(@PathVariable Long buyerId, @RequestBody CreateOrderDTO createOrderDTO) {
        return orderService.createOrder(buyerId, createOrderDTO.getShippingAddress(), createOrderDTO.getShippingAddress());
    }
}
