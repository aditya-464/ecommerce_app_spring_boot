package com.adityagiri.ecommerce_app.entity;

import com.adityagiri.ecommerce_app.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String refNumber;

    private LocalDateTime orderDate;
    private Double amount;
    private Integer totalItems;
    private String shippingAddress;
    private String contactNumber;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(mappedBy = "order")
    private Payment payment;

    @OneToOne(mappedBy = "order")
    private Shipment shipment;


    @PrePersist
    public void generateRefNumber() {
        if (this.refNumber == null) {
            this.refNumber = "ORD" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
        }
    }
}
