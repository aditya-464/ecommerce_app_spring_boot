package com.adityagiri.ecommerce_app.repository;

import com.adityagiri.ecommerce_app.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    Optional<Shipment> findByTrackingNumber(String trackingNumber);

    @Query("SELECT s FROM Shipment s WHERE s.order.buyer.id = :buyerId")
    List<Shipment> findAllByBuyerId(@Param("buyerId") Long buyerId);
}
