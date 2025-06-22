package com.adityagiri.ecommerce_app.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrUpdateOrCancelOrderResponseDTO {
    private String message;
    private String refNumber;
}
