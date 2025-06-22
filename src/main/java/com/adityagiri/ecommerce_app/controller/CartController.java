package com.adityagiri.ecommerce_app.controller;

import com.adityagiri.ecommerce_app.dto.cart.AddToCartRequestDTO;
import com.adityagiri.ecommerce_app.dto.cart.CartResponseDTO;
import com.adityagiri.ecommerce_app.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add/{buyerId}")
    public ResponseEntity<String> addToCart(@PathVariable Long buyerId, @RequestBody AddToCartRequestDTO addToCartRequestDTO) {
        cartService.addToCart(buyerId, addToCartRequestDTO);
        return new ResponseEntity<>("Item added to cart successfully!", HttpStatus.OK);
    }

    @GetMapping("/get/{buyerId}")
    public CartResponseDTO getCartDetails(@PathVariable Long buyerId) {
        return cartService.getCartDetails(buyerId);
    }

    @DeleteMapping("/delete/{buyerId}")
    public ResponseEntity<String> deleteCart(@PathVariable Long buyerId) {
        cartService.deleteCart(buyerId);
        return new ResponseEntity<>("Cart cleared successfully!", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{buyerId}/item/{cartItemId}")
    public ResponseEntity<String> removeCartItem(@PathVariable Long buyerId, @PathVariable Long cartItemId) {
        cartService.removeCartItem(buyerId, cartItemId);
        return new ResponseEntity<>("Cart item removed successfully!", HttpStatus.OK);
    }

    @PutMapping("/update/{buyerId}/item/{cartItemId}/{operation}")
    public ResponseEntity<String> updateCartItemQuantity(@PathVariable Long buyerId, @PathVariable Long cartItemId, @PathVariable String operation){
        cartService.updateCartItem(buyerId, cartItemId, operation);
        return new ResponseEntity<>("Cart item updated successfully!", HttpStatus.OK);
    }
}
