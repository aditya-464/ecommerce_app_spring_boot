package com.adityagiri.ecommerce_app.dto.order;

import com.adityagiri.ecommerce_app.entity.OrderItem;
import com.adityagiri.ecommerce_app.enums.OrderStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    private String refNumber;
    private LocalDateTime orderDate;
    private Double amount;
    private Integer totalItems;
    private String shippingAddress;
    private String contactNumber;
    private OrderStatus orderStatus;
    private Long buyerId;
    private List<OrderItemResponseDTO> orderItems = new ArrayList<>();
    private String shipmentTrackingNumber;
    private String paymentRefNumber;

}
