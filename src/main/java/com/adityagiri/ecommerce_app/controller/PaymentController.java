package com.adityagiri.ecommerce_app.controller;

import com.adityagiri.ecommerce_app.dto.payment.CreatePaymentRequestDTO;
import com.adityagiri.ecommerce_app.dto.payment.CreatePaymentResponseDTO;
import com.adityagiri.ecommerce_app.dto.payment.PaymentResponseDTO;
import com.adityagiri.ecommerce_app.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create/{buyerId}/order/{refNumber}")
    public CreatePaymentResponseDTO makePayment(@PathVariable Long buyerId, @PathVariable String refNumber, @RequestBody CreatePaymentRequestDTO createPaymentRequestDTO) {
        return paymentService.makePayment(buyerId, refNumber, createPaymentRequestDTO);
    }

    @GetMapping("/get/{paymentRefNumber}")
    public PaymentResponseDTO getPaymentByRefNumber(@PathVariable String paymentRefNumber){
        return paymentService.getPaymentByRefNumber(paymentRefNumber);
    }

    @GetMapping("/get-all/{buyerId}")
    public List<PaymentResponseDTO> getAllPayments(@PathVariable Long buyerId){
        return paymentService.getAllPayments(buyerId);
    }
}
