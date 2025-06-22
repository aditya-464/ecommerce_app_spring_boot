package com.adityagiri.ecommerce_app.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderDTO {
    private String shippingAddress;
    private String contactNumber;
}
