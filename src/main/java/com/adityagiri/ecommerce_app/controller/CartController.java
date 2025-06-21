package com.adityagiri.ecommerce_app.controller;

import com.adityagiri.ecommerce_app.dto.cart.AddToCartRequestDTO;
import com.adityagiri.ecommerce_app.service.CartService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
