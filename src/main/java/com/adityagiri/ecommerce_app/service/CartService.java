package com.adityagiri.ecommerce_app.service;

import com.adityagiri.ecommerce_app.dto.cart.AddToCartRequestDTO;
import com.adityagiri.ecommerce_app.dto.cart.CartResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface CartService {
    String addToCart(AddToCartRequestDTO addToCartRequestDTO);
    CartResponseDTO getCartDetails(Long buyerId);
    void deleteCart(Long buyerId);
    void removeCartItem(Long buyerId, Long cartItemId);
}
