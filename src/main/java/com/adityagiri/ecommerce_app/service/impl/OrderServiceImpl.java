package com.adityagiri.ecommerce_app.service.impl;

import com.adityagiri.ecommerce_app.dto.order.OrderResponseDTO;
import com.adityagiri.ecommerce_app.entity.Cart;
import com.adityagiri.ecommerce_app.entity.Order;
import com.adityagiri.ecommerce_app.entity.User;
import com.adityagiri.ecommerce_app.enums.OrderStatus;
import com.adityagiri.ecommerce_app.repository.CartRepository;
import com.adityagiri.ecommerce_app.repository.OrderRepository;
import com.adityagiri.ecommerce_app.repository.UserRepository;
import com.adityagiri.ecommerce_app.service.OrderService;
import java.time.LocalDateTime;

public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, CartRepository cartRepository){
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    public OrderResponseDTO createOrder(Long buyerId, String shippingAddress, String contactNumber){
        User buyer = userRepository.findById(buyerId)
                .orElseThrow(()->new RuntimeException("No user found with given id: " + buyerId));

        Cart cart = cartRepository.findByBuyerId(buyerId)
                .orElseThrow(()->new RuntimeException("No cart found with given user id: "+ buyerId));

        Order newOrder = new Order();
        newOrder.setOrderDate(LocalDateTime.now());
        newOrder.setAmount(cart.getAmount());
        newOrder.setTotalItems(cart.getTotalItems());
        newOrder.setShippingAddress(shippingAddress);
        newOrder.setContactNumber(contactNumber);
        newOrder.setOrderStatus(OrderStatus.PAYMENT_NOT_DONE);
        newOrder.setBuyer(buyer);

        Order savedOrder = orderRepository.save(newOrder);

        return new OrderResponseDTO("Order created successfully!", savedOrder.getRefNumber());
    }

    public OrderResponseDTO updateOrderStatus(String refNumber, OrderStatus orderStatus){
        Order order = orderRepository.findByRefNumber(refNumber)
                .orElseThrow(()->new RuntimeException("No order found with given ref number: " + refNumber));

        order.setOrderStatus(orderStatus);

        Order savedOrder = orderRepository.save(order);

        return new OrderResponseDTO("Order status updated successfully!", refNumber);
    }
}
