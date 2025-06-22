package com.adityagiri.ecommerce_app.dto.payment;

import com.adityagiri.ecommerce_app.enums.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentRequestDTO {
    private PaymentMode paymentMode;
    private String cardNumber;
    private String cvv;
    private String dateOfExpiry;
    private String cardHolderName;
}
