package com.adityagiri.ecommerce_app.repository;

import com.adityagiri.ecommerce_app.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT p FROM Payment p WHERE p.order.buyer.id = :buyerId")
    List<Payment> findAllByBuyerId(@Param("buyerId") Long buyerId);
}
