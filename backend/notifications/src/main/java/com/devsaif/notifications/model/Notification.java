package com.devsaif.notifications.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_notification_payment_order_type",
                        columnNames = {"paymentOrderId", "type"}
                )
        }
)
@Data
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String type;

    private String description;

    private Boolean isRead = false;

    private Long userId;

    private Long bookingId;

    private Long salonId;

    private Long paymentOrderId;

    private LocalDateTime createdAt;
}
