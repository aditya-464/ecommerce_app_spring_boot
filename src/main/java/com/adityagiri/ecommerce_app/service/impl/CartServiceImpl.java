package com.adityagiri.ecommerce_app.service.impl;

import com.adityagiri.ecommerce_app.dto.cart.AddToCartRequestDTO;
import com.adityagiri.ecommerce_app.dto.cart.CartItemResponseDTO;
import com.adityagiri.ecommerce_app.dto.cart.CartResponseDTO;
import com.adityagiri.ecommerce_app.entity.Cart;
import com.adityagiri.ecommerce_app.entity.CartItem;
import com.adityagiri.ecommerce_app.entity.User;
import com.adityagiri.ecommerce_app.repository.CartRepository;
import com.adityagiri.ecommerce_app.repository.UserRepository;
import com.adityagiri.ecommerce_app.service.CartService;
import jakarta.persistence.OptimisticLockException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public CartServiceImpl(CartRepository cartRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }


    @Transactional
    public void addToCart(Long buyerId, AddToCartRequestDTO addToCartRequestDTO) {
        User buyer = userRepository.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("No user found with given user id: " + buyerId));
        Cart cart = cartRepository.findByBuyerId(buyerId)
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
        } else {
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
    }

    public CartResponseDTO getCartDetails(Long buyerId) {
        User buyer = userRepository.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("No user found with given id: " + buyerId));

        Cart cart = cartRepository.findByBuyerId(buyerId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + buyerId));

        return convertToCartResponseDTO(cart);
    }

    @Transactional
    public void deleteCart(Long buyerId) {
        User buyer = userRepository.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean cartExists = cartRepository.existsByBuyerId(buyerId);
        if (!cartExists) {
            throw new RuntimeException("Cart not found for user: " + buyerId);
        }
        buyer.setCart(null); // This is critical
        userRepository.save(buyer); // Persist this change

        cartRepository.deleteByBuyerId(buyerId);
    }

    @Transactional
    public void removeCartItem(Long buyerId, Long cartItemId) {
        boolean buyer = userRepository.existsById(buyerId);
        if (!buyer) {
            throw new RuntimeException("No user found with given id: " + buyerId);
        }

        Cart cart = cartRepository.findByBuyerId(buyerId)
                .orElseThrow(() -> new RuntimeException("No cart found for given user: " + buyerId));

        boolean removed = cart.getCartItems().removeIf(item -> item.getId().equals(cartItemId));
        if (!removed) {
            throw new RuntimeException("CartItem not found in user's cart: " + cartItemId);
        }
        double totalAmount = cart.getCartItems().stream()
                .mapToDouble(CartItem::getTotal)
                .sum();

        int totalItems = cart.getCartItems().stream()
                .mapToInt(item -> item.getQuantity().intValue())
                .sum();

        cart.setAmount(totalAmount);
        cart.setTotalItems(totalItems);

        cartRepository.save(cart);
    }

    @Transactional
    public void updateCartItem(Long buyerId, Long cartItemId, String operation) {
        boolean userExists = userRepository.existsById(buyerId);
        if (!userExists) {
            throw new RuntimeException("No user found with given id: " + buyerId);
        }

        try {
            Cart cart = cartRepository.findByBuyerId(buyerId)
                    .orElseThrow(() -> new RuntimeException("Cart not found with given user id: " + buyerId));

            CartItem cartItem = cart.getCartItems().stream()
                    .filter(item -> item.getId().equals(cartItemId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Cart item not found"));

            Long newQuantity = cartItem.getQuantity();
            if(operation.equals("increment")){
                newQuantity += 1;
            }
            else{
                if(cartItem.getQuantity()<=1){
                    throw new RuntimeException("Item quantity cannot be less than 1");
                }
                newQuantity -= 1;
            }

            cartItem.setQuantity(newQuantity);
            cartItem.setTotal(cartItem.getPrice() * newQuantity);

            double newAmount = cart.getCartItems().stream()
                    .mapToDouble(CartItem::getTotal)
                    .sum();

            int totalItems = cart.getCartItems().stream()
                    .mapToInt(item -> item.getQuantity().intValue())
                    .sum();

            cart.setAmount(newAmount);
            cart.setTotalItems(totalItems);

            cartRepository.save(cart);
        } catch (OptimisticLockException e) {
            throw new RuntimeException("Cart was updated by another request. Please reload and try again");
        }
    }

    private CartResponseDTO convertToCartResponseDTO(Cart cart) {
        List<CartItemResponseDTO> cartItemsFinalList = new ArrayList<>();
        for (CartItem c : cart.getCartItems()) {
            cartItemsFinalList.add(new CartItemResponseDTO(
                    c.getId(),
                    c.getProductId(),
                    c.getQuantity(),
                    c.getPrice(),
                    c.getTotal()
            ));
        }

        return new CartResponseDTO(
                cart.getId(),
                cart.getAmount(),
                cart.getTotalItems(),
                cartItemsFinalList
        );
    }
}
