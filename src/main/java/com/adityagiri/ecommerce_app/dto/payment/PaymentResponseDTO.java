package com.adityagiri.ecommerce_app.dto.payment;

import com.adityagiri.ecommerce_app.enums.PaymentMode;
import com.adityagiri.ecommerce_app.enums.PaymentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDTO {
    private String refNumber;
    private Double amount;
    private LocalDateTime date;
    private PaymentStatus paymentStatus;
    private PaymentMode paymentMode;
    private String orderRefNumber;
}
