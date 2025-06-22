package com.adityagiri.ecommerce_app.service;

import com.adityagiri.ecommerce_app.dto.order.CreateOrUpdateOrCancelOrderResponseDTO;
import com.adityagiri.ecommerce_app.dto.order.OrderItemResponseDTO;
import com.adityagiri.ecommerce_app.dto.order.OrderResponseDTO;
import com.adityagiri.ecommerce_app.entity.Payment;
import com.adityagiri.ecommerce_app.entity.Shipment;
import com.adityagiri.ecommerce_app.enums.OrderStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    CreateOrUpdateOrCancelOrderResponseDTO createOrder(Long buyerId, String shippingAddress, String contactNumber);
    CreateOrUpdateOrCancelOrderResponseDTO updateOrderStatus(String refNumber, OrderStatus orderStatus);
    CreateOrUpdateOrCancelOrderResponseDTO updatePaymentObject(String refNumber, Payment payment);
    CreateOrUpdateOrCancelOrderResponseDTO updateShipmentObject(String refNumber, Shipment shipment);
    CreateOrUpdateOrCancelOrderResponseDTO cancelOrder(String refNumber);
    OrderResponseDTO getOrderByRefNumber(String refNumber);
    List<OrderResponseDTO> getAllOrders(Long buyerId);
}
