package com.adityagiri.ecommerce_app.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentResponseDTO {
    private String message;
    private String refNumber;
}
