package com.adityagiri.ecommerce_app.service.impl;

import com.adityagiri.ecommerce_app.dto.payment.CreatePaymentRequestDTO;
import com.adityagiri.ecommerce_app.dto.payment.CreatePaymentResponseDTO;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderService orderService;

    public PaymentServiceImpl(PaymentRepository paymentRepository, OrderRepository orderRepository, UserRepository userRepository, OrderService orderService) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderService = orderService;
    }

    @Transactional
    public CreatePaymentResponseDTO makePayment(Long buyerId, String refNumber, CreatePaymentRequestDTO createPaymentRequestDTO) {
        boolean userExists = userRepository.existsById(buyerId);
        if (!userExists) {
            throw new RuntimeException("No user found with given id: " + buyerId);
        }

        Order order = orderRepository.findByRefNumber(refNumber)
                .orElseThrow(() -> new RuntimeException("No order found with given ref number: " + refNumber));

        Payment newPayment = new Payment();
        newPayment.setAmount(order.getAmount());
        newPayment.setDate(LocalDateTime.now());
        newPayment.setPaymentStatus(PaymentStatus.SUCCESS);
        newPayment.setPaymentMode(createPaymentRequestDTO.getPaymentMode());
        newPayment.setOrder(order);

        Payment savedPayment = paymentRepository.save(newPayment);

        orderService.updateOrderStatus(order.getRefNumber(), OrderStatus.ORDER_PLACED);
        orderService.updatePaymentObject(order.getRefNumber(), savedPayment);

        return new CreatePaymentResponseDTO("Payment done successfully!", savedPayment.getRefNumber());
    }

    public PaymentResponseDTO getPaymentByRefNumber(String refNumber){
        Payment payment = paymentRepository.findByRefNumber(refNumber)
                .orElseThrow(()->new RuntimeException("No payment found with given ref number: " + refNumber));

        return convertToPaymentResponseDTO(payment);
    }

    public List<PaymentResponseDTO> getAllPayments(Long buyerId) {
        boolean userExists = userRepository.existsById(buyerId);
        if (!userExists) {
            throw new RuntimeException("No user found with given id: " + buyerId);
        }

        List<Payment> paymentsList = paymentRepository.findAllByBuyerId(buyerId);
        List<PaymentResponseDTO> finalPaymentsList = new ArrayList<>();
        for (Payment p : paymentsList) {
            finalPaymentsList.add(convertToPaymentResponseDTO(p));
        }
        return finalPaymentsList;
    }

    private PaymentResponseDTO convertToPaymentResponseDTO(Payment p) {
        return new PaymentResponseDTO(
                p.getRefNumber(),
                p.getAmount(),
                p.getDate(),
                p.getPaymentStatus(),
                p.getPaymentMode(),
                p.getOrder().getRefNumber()
        );
    }

}
