package com.adityagiri.ecommerce_app.service;

import com.adityagiri.ecommerce_app.dto.payment.CreatePaymentRequestDTO;
import com.adityagiri.ecommerce_app.dto.payment.CreatePaymentResponseDTO;
import com.adityagiri.ecommerce_app.dto.payment.PaymentResponseDTO;

import java.util.List;

public interface PaymentService {
    CreatePaymentResponseDTO makePayment(Long buyerId, String refNumber, CreatePaymentRequestDTO createPaymentRequestDTO);
    PaymentResponseDTO getPaymentByRefNumber(String refNumber);
    List<PaymentResponseDTO> getAllPayments(Long buyerId);
}
