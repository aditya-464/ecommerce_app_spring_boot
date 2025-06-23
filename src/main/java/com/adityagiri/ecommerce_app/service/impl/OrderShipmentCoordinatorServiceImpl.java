package com.adityagiri.ecommerce_app.service.impl;

import com.adityagiri.ecommerce_app.dto.order.CreateOrUpdateOrCancelOrderResponseDTO;
import com.adityagiri.ecommerce_app.entity.Order;
import com.adityagiri.ecommerce_app.entity.Shipment;
import com.adityagiri.ecommerce_app.enums.OrderStatus;
import com.adityagiri.ecommerce_app.repository.OrderRepository;
import com.adityagiri.ecommerce_app.service.OrderShipmentCoordinatorService;
import org.springframework.stereotype.Service;

@Service
public class OrderShipmentCoordinatorServiceImpl implements OrderShipmentCoordinatorService {

    private final OrderRepository orderRepository;
    public OrderShipmentCoordinatorServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public CreateOrUpdateOrCancelOrderResponseDTO updateOrderStatus(String refNumber, OrderStatus orderStatus) {
        Order order = orderRepository.findByRefNumber(refNumber)
                .orElseThrow(() -> new RuntimeException("No order found with given ref number: " + refNumber));

        order.setOrderStatus(orderStatus);

        Order savedOrder = orderRepository.save(order);

        return new CreateOrUpdateOrCancelOrderResponseDTO("Order status updated successfully!", refNumber);
    }

    public CreateOrUpdateOrCancelOrderResponseDTO updateShipmentObject(String refNumber, Shipment shipment) {
        Order order = orderRepository.findByRefNumber(refNumber)
                .orElseThrow(() -> new RuntimeException("No order found with given ref number: " + refNumber));

        order.setShipment(shipment);
        Order savedOrder = orderRepository.save(order);

        return new CreateOrUpdateOrCancelOrderResponseDTO("Shipment details updated in order successfully!", refNumber);
    }
}
