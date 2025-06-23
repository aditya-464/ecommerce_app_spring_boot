package com.adityagiri.ecommerce_app.service.impl;

import com.adityagiri.ecommerce_app.dto.order.CreateOrUpdateOrCancelOrderResponseDTO;
import com.adityagiri.ecommerce_app.dto.order.OrderItemResponseDTO;
import com.adityagiri.ecommerce_app.dto.order.OrderResponseDTO;
import com.adityagiri.ecommerce_app.entity.*;
import com.adityagiri.ecommerce_app.enums.OrderStatus;
import com.adityagiri.ecommerce_app.enums.ShipmentStatus;
import com.adityagiri.ecommerce_app.repository.CartRepository;
import com.adityagiri.ecommerce_app.repository.OrderRepository;
import com.adityagiri.ecommerce_app.repository.UserRepository;
import com.adityagiri.ecommerce_app.service.OrderService;
import com.adityagiri.ecommerce_app.service.ShipmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ShipmentService shipmentService;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, CartRepository cartRepository, ShipmentService shipmentService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.shipmentService = shipmentService;
    }

    @Transactional
    public CreateOrUpdateOrCancelOrderResponseDTO createOrder(Long buyerId, String shippingAddress, String contactNumber) {
        User buyer = userRepository.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("No user found with given id: " + buyerId));

        Cart cart = cartRepository.findByBuyerId(buyerId)
                .orElseThrow(() -> new RuntimeException("No cart found with given user id: " + buyerId));

        Order newOrder = new Order();
        newOrder.setOrderDate(LocalDateTime.now());
        newOrder.setAmount(cart.getAmount());
        newOrder.setTotalItems(cart.getTotalItems());
        newOrder.setShippingAddress(shippingAddress);
        newOrder.setContactNumber(contactNumber);
        newOrder.setOrderStatus(OrderStatus.PAYMENT_NOT_DONE);
        newOrder.setBuyer(buyer);

        Order savedOrder = orderRepository.save(newOrder);

        return new CreateOrUpdateOrCancelOrderResponseDTO("Order created successfully!", savedOrder.getRefNumber());
    }

    @Transactional
    public CreateOrUpdateOrCancelOrderResponseDTO updateOrderStatus(String refNumber, OrderStatus orderStatus) {
        Order order = orderRepository.findByRefNumber(refNumber)
                .orElseThrow(() -> new RuntimeException("No order found with given ref number: " + refNumber));

        order.setOrderStatus(orderStatus);

        Order savedOrder = orderRepository.save(order);

        return new CreateOrUpdateOrCancelOrderResponseDTO("Order status updated successfully!", refNumber);
    }

    @Transactional
    public CreateOrUpdateOrCancelOrderResponseDTO updatePaymentObject(String refNumber, Payment payment) {
        Order order = orderRepository.findByRefNumber(refNumber)
                .orElseThrow(() -> new RuntimeException("No order found with given ref number: " + refNumber));

        order.setPayment(payment);

        Order savedOrder = orderRepository.save(order);

        return new CreateOrUpdateOrCancelOrderResponseDTO("Payment details updated in order successfully!", refNumber);
    }

    @Transactional
    public CreateOrUpdateOrCancelOrderResponseDTO updateShipmentObject(String refNumber, Shipment shipment) {
        Order order = orderRepository.findByRefNumber(refNumber)
                .orElseThrow(() -> new RuntimeException("No order found with given ref number: " + refNumber));

        order.setShipment(shipment);
        Order savedOrder = orderRepository.save(order);

        return new CreateOrUpdateOrCancelOrderResponseDTO("Shipment details updated in order successfully!", refNumber);
    }

    @Transactional
    public CreateOrUpdateOrCancelOrderResponseDTO cancelOrder(String refNumber) {
        Order order = orderRepository.findByRefNumber(refNumber)
                .orElseThrow(() -> new RuntimeException("No order found with given ref number: " + refNumber));

        order.setOrderStatus(OrderStatus.CANCELED);
        orderRepository.save(order);

        shipmentService.updateShipmentStatus(order.getShipment().getTrackingNumber(), ShipmentStatus.CANCELED);

        return new CreateOrUpdateOrCancelOrderResponseDTO("Order canceled!", refNumber);
    }

    public OrderResponseDTO getOrderByRefNumber(String refNumber){
        Order order = orderRepository.findByRefNumber(refNumber)
                .orElseThrow(()->new RuntimeException("No order found with given ref number: " + refNumber));

        return convertToOrderResponseDTO(order);
    }

    public List<OrderResponseDTO> getAllOrders(Long buyerId){
        User buyer = userRepository.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("No user found with given id: " + buyerId));

        List<Order> ordersList = orderRepository.findByBuyerId(buyerId);
        List<OrderResponseDTO> finalOrdersList = new ArrayList<>();

        for(Order o: ordersList){
            finalOrdersList.add(convertToOrderResponseDTO(o));
        }

        return finalOrdersList;
    }

    private OrderResponseDTO convertToOrderResponseDTO(Order order){
        OrderResponseDTO o = new OrderResponseDTO();
        o.setRefNumber(order.getRefNumber());
        o.setOrderDate(order.getOrderDate());
        o.setAmount(order.getAmount());
        o.setTotalItems(order.getTotalItems());
        o.setShippingAddress(order.getShippingAddress());
        o.setContactNumber(order.getContactNumber());
        o.setOrderStatus(order.getOrderStatus());
        o.setBuyerId(order.getBuyer().getId());
        o.setShipmentTrackingNumber(order.getShipment().getTrackingNumber());
        o.setPaymentRefNumber(order.getPayment().getRefNumber());

        List<OrderItemResponseDTO> finalOrderItemsList = new ArrayList<>();

        for(OrderItem ot: order.getOrderItems()){
            finalOrderItemsList.add(convertToOrderItemResponseDTO(ot));
        }

        o.setOrderItems(finalOrderItemsList);

        return o;
    }

    private OrderItemResponseDTO convertToOrderItemResponseDTO(OrderItem orderItem){
        OrderItemResponseDTO o = new OrderItemResponseDTO();
        o.setProductId(orderItem.getProductId());
        o.setQuantity(orderItem.getQuantity());
        o.setPrice(orderItem.getPrice());
        o.setTotal(orderItem.getTotal());

        return o;
    }

}
