package com.adityagiri.ecommerce_app.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponseDTO {
    private Long id;
    private Long productId;
    private Long quantity;
    private Double price;
    private Double total;
}
