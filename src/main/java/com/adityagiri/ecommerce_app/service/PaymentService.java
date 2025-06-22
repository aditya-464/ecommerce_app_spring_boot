package com.adityagiri.ecommerce_app.service;

import com.adityagiri.ecommerce_app.dto.payment.CreatePaymentRequestDTO;
import com.adityagiri.ecommerce_app.dto.payment.CreatePaymentResponseDTO;
import com.adityagiri.ecommerce_app.dto.payment.PaymentResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaymentService {
    CreatePaymentResponseDTO makePayment(Long buyerId, String refNumber, CreatePaymentRequestDTO createPaymentRequestDTO);
    PaymentResponseDTO getPaymentByRefNumber(String refNumber);
    List<PaymentResponseDTO> getAllPayments(Long buyerId);
}
