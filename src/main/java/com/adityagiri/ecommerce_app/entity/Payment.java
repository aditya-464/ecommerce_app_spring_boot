package com.adityagiri.ecommerce_app.entity;

import com.adityagiri.ecommerce_app.enums.PaymentMode;
import com.adityagiri.ecommerce_app.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String refNumber;

    private Double amount;
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;


    @PrePersist
    public void generateRefNumber() {
        if (this.refNumber == null) {
            this.refNumber = "PAY" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
        }
    }
}
