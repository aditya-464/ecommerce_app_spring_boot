package com.adityagiri.ecommerce_app.repository;

import com.adityagiri.ecommerce_app.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByRefNumber(String refNumber);
    List<Order> findByBuyerId(Long buyerId);
}
