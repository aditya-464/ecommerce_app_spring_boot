package com.adityagiri.ecommerce_app.service;

import com.adityagiri.ecommerce_app.dto.cart.AddToCartRequestDTO;
import com.adityagiri.ecommerce_app.dto.cart.CartResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface CartService {
    void addToCart(Long buyerId, AddToCartRequestDTO addToCartRequestDTO);
    CartResponseDTO getCartDetails(Long buyerId);
    void deleteCart(Long buyerId);
    void removeCartItem(Long buyerId, Long cartItemId);
}
