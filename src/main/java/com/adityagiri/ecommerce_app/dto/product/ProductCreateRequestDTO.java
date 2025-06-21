package com.adityagiri.ecommerce_app.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateRequestDTO {
    private String name;
    private String description;
    private Double price;
    private Integer stocks;
    private Boolean available;
    private Long sellerId;
}
