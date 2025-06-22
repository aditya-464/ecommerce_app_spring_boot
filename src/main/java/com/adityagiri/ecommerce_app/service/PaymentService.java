package com.adityagiri.ecommerce_app.service;

import com.adityagiri.ecommerce_app.dto.payment.CreatePaymentRequestDTO;
import com.adityagiri.ecommerce_app.dto.payment.PaymentResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService {
    PaymentResponseDTO makePayment(Long buyerId, String refNumber, CreatePaymentRequestDTO createPaymentRequestDTO);
}
