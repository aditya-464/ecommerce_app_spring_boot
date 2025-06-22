package com.adityagiri.ecommerce_app.service;

import com.adityagiri.ecommerce_app.dto.shipment.CreateOrUpdateShipmentResponseDTO;
import com.adityagiri.ecommerce_app.dto.shipment.ShipmentResponseDTO;
import com.adityagiri.ecommerce_app.enums.ShipmentStatus;

import java.util.List;

public interface ShipmentService {
    CreateOrUpdateShipmentResponseDTO createShipment(String orderRefNumber);
    CreateOrUpdateShipmentResponseDTO updateShipmentStatus(String trackingNumber, ShipmentStatus shipmentStatus);
    ShipmentResponseDTO getShipmentByTrackingNumber(String trackingNumber);
    List<ShipmentResponseDTO> getAllShipments(Long buyerId);
}
