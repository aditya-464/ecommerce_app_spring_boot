package com.adityagiri.ecommerce_app.service;

import com.adityagiri.ecommerce_app.dto.product.ProductCreateRequestDTO;
import com.adityagiri.ecommerce_app.dto.product.ProductResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    ProductResponseDTO createProduct(ProductCreateRequestDTO productCreateRequestDTO);
    ProductResponseDTO getProductById(Long id);
    List<ProductResponseDTO> getAllProductsBySellerId(Long id);
    List<ProductResponseDTO> getAllProducts();
    List<ProductResponseDTO> searchProductsByName(String name);
}
