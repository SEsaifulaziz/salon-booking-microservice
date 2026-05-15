package com.devsaif.payment.service.model;

import com.devsaif.payment.service.domain.PaymentMethod;
import com.devsaif.payment.service.domain.PaymentOrderStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PaymentOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private PaymentOrderStatus status = PaymentOrderStatus.PENDING;

    @Column(nullable = false)
    private PaymentMethod  paymentMethod;

    private String paymentLinkedId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long salonId;

}
