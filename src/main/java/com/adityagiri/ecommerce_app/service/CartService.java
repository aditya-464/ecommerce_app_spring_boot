package com.adityagiri.ecommerce_app.service;

import com.adityagiri.ecommerce_app.dto.cart.AddToCartRequestDTO;
import com.adityagiri.ecommerce_app.dto.cart.CartResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface CartService {
    String addToCart(AddToCartRequestDTO addToCartRequestDTO);
    CartResponseDTO getCartDetails(Long id);
    void deleteCart(Long id);
//    String removeCartItem(Long id, Long cartItemId);
}
