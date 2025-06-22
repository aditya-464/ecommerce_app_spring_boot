package com.adityagiri.ecommerce_app.controller;

import com.adityagiri.ecommerce_app.dto.order.CreateOrderDTO;
import com.adityagiri.ecommerce_app.dto.order.CreateOrUpdateOrCancelOrderResponseDTO;
import com.adityagiri.ecommerce_app.dto.order.OrderResponseDTO;
import com.adityagiri.ecommerce_app.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create/{buyerId}")
    public CreateOrUpdateOrCancelOrderResponseDTO createOrder(@PathVariable Long buyerId, @RequestBody CreateOrderDTO createOrderDTO) {
        return orderService.createOrder(buyerId, createOrderDTO.getShippingAddress(), createOrderDTO.getShippingAddress());
    }

    @PutMapping("/cancel/{orderRefNumber}")
    public CreateOrUpdateOrCancelOrderResponseDTO cancelOrder(@PathVariable String orderRefNumber) {
        return orderService.cancelOrder(orderRefNumber);
    }

    @GetMapping("/get/{orderRefNumber}")
    public OrderResponseDTO getOrderByRefNumber(@PathVariable String orderRefNumber) {
        return orderService.getOrderByRefNumber(orderRefNumber);
    }

    @GetMapping("/get-all/{buyerId}")
    public List<OrderResponseDTO> getAllOrders(@PathVariable Long buyerId) {
        return orderService.getAllOrders(buyerId);
    }
}
