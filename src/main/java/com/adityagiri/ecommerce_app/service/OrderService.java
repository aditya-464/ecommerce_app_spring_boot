package com.adityagiri.ecommerce_app.service;

import com.adityagiri.ecommerce_app.dto.order.OrderResponseDTO;
import com.adityagiri.ecommerce_app.enums.OrderStatus;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    OrderResponseDTO createOrder(Long buyerId, String shippingAddress, String contactNumber);
    OrderResponseDTO updateOrderStatus(String refNumber, OrderStatus orderStatus);
//    void cancelOrder(Long buyerId, Long orderId);
}
