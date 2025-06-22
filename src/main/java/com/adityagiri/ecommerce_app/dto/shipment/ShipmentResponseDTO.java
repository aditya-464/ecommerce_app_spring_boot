package com.adityagiri.ecommerce_app.dto.shipment;

import com.adityagiri.ecommerce_app.enums.ShipmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentResponseDTO {
    private String shippingAddress;
    private String contactNumber;
    private String trackingNumber;
    private String carrier;
    private ShipmentStatus status;
    private LocalDateTime shippedAt;
    private LocalDateTime deliveredAt;
    private String orderRefNumber;
}
