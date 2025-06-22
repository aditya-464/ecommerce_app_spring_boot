package com.adityagiri.ecommerce_app.service.impl;

import com.adityagiri.ecommerce_app.dto.shipment.CreateOrUpdateShipmentResponseDTO;
import com.adityagiri.ecommerce_app.dto.shipment.ShipmentResponseDTO;
import com.adityagiri.ecommerce_app.entity.Order;
import com.adityagiri.ecommerce_app.entity.Shipment;
import com.adityagiri.ecommerce_app.enums.OrderStatus;
import com.adityagiri.ecommerce_app.enums.PaymentStatus;
import com.adityagiri.ecommerce_app.enums.ShipmentStatus;
import com.adityagiri.ecommerce_app.repository.OrderRepository;
import com.adityagiri.ecommerce_app.repository.ShipmentRepository;
import com.adityagiri.ecommerce_app.repository.UserRepository;
import com.adityagiri.ecommerce_app.service.OrderService;
import com.adityagiri.ecommerce_app.service.ShipmentService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderService orderService;

    public ShipmentServiceImpl(ShipmentRepository shipmentRepository, OrderRepository orderRepository, UserRepository userRepository, OrderService orderService) {
        this.shipmentRepository = shipmentRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderService = orderService;
    }

    public CreateOrUpdateShipmentResponseDTO createShipment(String orderRefNumber) {
        Order order = orderRepository.findByRefNumber(orderRefNumber)
                .orElseThrow(() -> new RuntimeException("No order found with given id: " + orderRefNumber));

        if (!order.getPayment().getPaymentStatus().equals(PaymentStatus.SUCCESS)) {
            throw new RuntimeException("Please complete payment before creating shipment!");
        }

        Shipment shipment = new Shipment();
        shipment.setShippingAddress(order.getShippingAddress());
        shipment.setContactNumber(order.getContactNumber());
        shipment.setCarrier("Delhivery");
        shipment.setStatus(ShipmentStatus.SHIPPED);
        shipment.setShippedAt(LocalDateTime.now());
        shipment.setDeliveredAt(LocalDateTime.now());
        shipment.setOrder(order);

        Shipment savedShipment = shipmentRepository.save(shipment);

        orderService.updateOrderStatus(order.getRefNumber(), OrderStatus.SHIPPED);
        orderService.updateShipmentObject(order.getRefNumber(), savedShipment);

        return new CreateOrUpdateShipmentResponseDTO("Shipment created successfully!", savedShipment.getTrackingNumber());
    }

    public CreateOrUpdateShipmentResponseDTO updateShipmentStatus(String trackingNumber, ShipmentStatus shipmentStatus){
        Shipment shipment = shipmentRepository.findByTrackingNumber(trackingNumber)
                .orElseThrow(()->new RuntimeException("No shipment found for given tracking number: " + trackingNumber));

        shipment.setStatus(shipmentStatus);
        Shipment updatedShipment = shipmentRepository.save(shipment);

        return new CreateOrUpdateShipmentResponseDTO("Shipment status updated successfully!", updatedShipment.getTrackingNumber());
    }

    public ShipmentResponseDTO getShipmentByTrackingNumber(String trackingNumber){
        Shipment shipment = shipmentRepository.findByTrackingNumber(trackingNumber)
                .orElseThrow(()->new RuntimeException("No shipment found with given tracking number: " + trackingNumber));

        return convertToShipmentResponseDTO(shipment);
    }

    public List<ShipmentResponseDTO> getAllShipments(Long buyerId) {
        boolean userExists = userRepository.existsById(buyerId);
        if (!userExists) {
            throw new RuntimeException("No user found with given id: " + buyerId);
        }

        List<Shipment> shipmentsList = shipmentRepository.findAllByBuyerId(buyerId);
        List<ShipmentResponseDTO> finalShipmentsList = new ArrayList<>();

        for (Shipment s : shipmentsList) {
            finalShipmentsList.add(convertToShipmentResponseDTO(s));
        }

        return finalShipmentsList;
    }

    private ShipmentResponseDTO convertToShipmentResponseDTO(Shipment s) {
        return new ShipmentResponseDTO(
                s.getShippingAddress(),
                s.getContactNumber(),
                s.getTrackingNumber(),
                s.getCarrier(),
                s.getStatus(),
                s.getShippedAt(),
                s.getDeliveredAt(),
                s.getOrder().getRefNumber()
        );
    }
}
