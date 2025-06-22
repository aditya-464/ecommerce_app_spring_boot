package com.adityagiri.ecommerce_app.entity;

import com.adityagiri.ecommerce_app.enums.ShipmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shipments")
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String shippingAddress;
    private String contactNumber;

    @Column(unique = true, nullable = false)
    private String trackingNumber;

    private String carrier;
    private ShipmentStatus status;
    private LocalDateTime shippedAt;
    private LocalDateTime deliveredAt;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;


    @PrePersist
    public void generateRefNumber() {
        if (this.trackingNumber == null) {
            this.trackingNumber = "TRACK" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
        }
    }
}
