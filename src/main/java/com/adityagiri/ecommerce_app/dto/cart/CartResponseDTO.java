package com.adityagiri.ecommerce_app.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDTO {
    private Long id;
    private Double amount;
    private Integer totalItems;
    private List<CartItemResponseDTO> items;
}
