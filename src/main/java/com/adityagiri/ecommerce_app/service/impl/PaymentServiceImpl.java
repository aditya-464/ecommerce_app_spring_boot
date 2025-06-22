package com.adityagiri.ecommerce_app.service.impl;

import com.adityagiri.ecommerce_app.dto.payment.CreatePaymentRequestDTO;
import com.adityagiri.ecommerce_app.dto.payment.PaymentResponseDTO;
import com.adityagiri.ecommerce_app.entity.Order;
import com.adityagiri.ecommerce_app.entity.Payment;
import com.adityagiri.ecommerce_app.enums.OrderStatus;
import com.adityagiri.ecommerce_app.enums.PaymentStatus;
import com.adityagiri.ecommerce_app.repository.OrderRepository;
import com.adityagiri.ecommerce_app.repository.PaymentRepository;
import com.adityagiri.ecommerce_app.repository.UserRepository;
import com.adityagiri.ecommerce_app.service.OrderService;
import com.adityagiri.ecommerce_app.service.PaymentService;

import java.time.LocalDateTime;

public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderService orderService;
    public PaymentServiceImpl(PaymentRepository paymentRepository, OrderRepository orderRepository, UserRepository userRepository, OrderService orderService){
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderService = orderService;
    }

    public PaymentResponseDTO makePayment(Long buyerId, String refNumber, CreatePaymentRequestDTO createPaymentRequestDTO){
        boolean userExists = userRepository.existsById(buyerId);
        if(!userExists){
            throw new RuntimeException("No user found with given id: " + buyerId);
        }

        Order order = orderRepository.findByRefNumber(refNumber)
                .orElseThrow(()->new RuntimeException("No order found with given ref number: " + refNumber));

        Payment newPayment = new Payment();
        newPayment.setAmount(order.getAmount());
        newPayment.setDate(LocalDateTime.now());
        newPayment.setPaymentStatus(PaymentStatus.SUCCESS);
        newPayment.setPaymentMode(createPaymentRequestDTO.getPaymentMode());
        newPayment.setOrder(order);

        Payment savedPayment = paymentRepository.save(newPayment);

        orderService.updateOrderStatus(order.getRefNumber(), OrderStatus.ORDER_PLACED);

        return new PaymentResponseDTO("Payment done successfully!", savedPayment.getRefNumber());
    }
}
