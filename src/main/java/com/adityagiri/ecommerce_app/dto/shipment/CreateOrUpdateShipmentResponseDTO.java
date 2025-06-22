package com.adityagiri.ecommerce_app.dto.shipment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrUpdateShipmentResponseDTO {
    private String message;
    private String trackingNumber;
}
