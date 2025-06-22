package com.adityagiri.ecommerce_app.controller;

import com.adityagiri.ecommerce_app.dto.shipment.CreateOrUpdateShipmentResponseDTO;
import com.adityagiri.ecommerce_app.dto.shipment.ShipmentResponseDTO;
import com.adityagiri.ecommerce_app.service.ShipmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {

    private final ShipmentService shipmentService;
    public ShipmentController(ShipmentService shipmentService){
        this.shipmentService = shipmentService;
    }

    @PostMapping("/create/order/{refNumber}")
    public CreateOrUpdateShipmentResponseDTO createShipment(@PathVariable String refNumber){
        return shipmentService.createShipment(refNumber);
    }

    @GetMapping("/get/{shipmentTrackingNumber}")
    public ShipmentResponseDTO getShipmentByTrackingNumber(@PathVariable String shipmentTrackingNumber){
        return shipmentService.getShipmentByTrackingNumber(shipmentTrackingNumber);
    }

    @GetMapping("/get-all/{buyerId}")
    public List<ShipmentResponseDTO> getAllShipments(@PathVariable Long buyerId){
        return shipmentService.getAllShipments(buyerId);
    }
}
