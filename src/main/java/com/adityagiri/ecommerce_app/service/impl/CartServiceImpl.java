package com.adityagiri.ecommerce_app.service.impl;

import com.adityagiri.ecommerce_app.dto.cart.AddToCartRequestDTO;
import com.adityagiri.ecommerce_app.entity.Cart;
import com.adityagiri.ecommerce_app.entity.CartItem;
import com.adityagiri.ecommerce_app.entity.User;
import com.adityagiri.ecommerce_app.repository.CartRepository;
import com.adityagiri.ecommerce_app.repository.UserRepository;
import com.adityagiri.ecommerce_app.service.CartService;

public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public CartServiceImpl(CartRepository cartRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }


    public String addToCart(AddToCartRequestDTO addToCartRequestDTO) {
        User buyer = userRepository.findById(addToCartRequestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("No user found with given user id: " + addToCartRequestDTO.getUserId()));
        Cart cart = cartRepository.findByBuyerId(addToCartRequestDTO.getUserId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setBuyer(buyer);
                    newCart.setAmount(0.0);
                    newCart.setTotalItems(0);
                    return cartRepository.save(newCart);
                });

        CartItem existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProductId().equals(addToCartRequestDTO.getProductId()))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + addToCartRequestDTO.getQuantity());
            existingItem.setTotal(existingItem.getQuantity() * existingItem.getPrice());
        }
        else{
            CartItem newItem = new CartItem();
            newItem.setProductId(addToCartRequestDTO.getProductId());
            newItem.setQuantity(addToCartRequestDTO.getQuantity());
            newItem.setPrice(addToCartRequestDTO.getPrice());
            newItem.setTotal(addToCartRequestDTO.getQuantity() * addToCartRequestDTO.getPrice());
            newItem.setCart(cart);

            cart.getCartItems().add(newItem);
        }

        double totalAmount = cart.getCartItems().stream()
                .mapToDouble(CartItem::getTotal)
                .sum();

        int totalItems = cart.getCartItems().stream()
                .mapToInt(item -> item.getQuantity().intValue())
                .sum();

        cart.setAmount(totalAmount);
        cart.setTotalItems(totalItems);

        Cart savedCart = cartRepository.save(cart);

        return "Item added to cart!";
    }
}
