package com.adityagiri.ecommerce_app.repository;

import com.adityagiri.ecommerce_app.dto.product.ProductResponseDTO;
import com.adityagiri.ecommerce_app.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findBySellerId(Long sellerId);
    List<Product> findByNameContainingIgnoreCase(String name);
}
