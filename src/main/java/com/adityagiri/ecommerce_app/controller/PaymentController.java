package com.adityagiri.ecommerce_app.controller;

import com.adityagiri.ecommerce_app.dto.payment.CreatePaymentRequestDTO;
import com.adityagiri.ecommerce_app.dto.payment.PaymentResponseDTO;
import com.adityagiri.ecommerce_app.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create/{buyerId}/order/{refNumber}")
    public PaymentResponseDTO makePayment(@PathVariable Long buyerId, @PathVariable String refNumber, @RequestBody CreatePaymentRequestDTO createPaymentRequestDTO) {
        return paymentService.makePayment(buyerId, refNumber, createPaymentRequestDTO);
    }
}
