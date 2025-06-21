package com.adityagiri.ecommerce_app.controller;

import com.adityagiri.ecommerce_app.dto.cart.AddToCartRequestDTO;
import com.adityagiri.ecommerce_app.dto.cart.CartResponseDTO;
import com.adityagiri.ecommerce_app.entity.Cart;
import com.adityagiri.ecommerce_app.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{buyerId}")
    public CartResponseDTO getCartDetails(@PathVariable Long buyerId){
        return cartService.getCartDetails(buyerId);
    }

    @DeleteMapping("/{buyerId}")
    public ResponseEntity<String> deleteCart(@PathVariable Long buyerId){
        cartService.deleteCart(buyerId);
        return new ResponseEntity<>("Cart cleared successfully!", HttpStatus.OK);
    }
}
