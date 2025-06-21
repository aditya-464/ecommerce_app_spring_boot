package com.adityagiri.ecommerce_app.controller;

import com.adityagiri.ecommerce_app.dto.cart.AddToCartRequestDTO;
import com.adityagiri.ecommerce_app.dto.cart.CartResponseDTO;
import com.adityagiri.ecommerce_app.entity.Cart;
import com.adityagiri.ecommerce_app.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    public CartController(CartService cartService){
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public String addToCart(@RequestBody AddToCartRequestDTO addToCartRequestDTO){
        return cartService.addToCart(addToCartRequestDTO);
    }

    @GetMapping("/{id}")
    public CartResponseDTO getCartDetails(@PathVariable Long id){
        return cartService.getCartDetails(id);
    }
}
