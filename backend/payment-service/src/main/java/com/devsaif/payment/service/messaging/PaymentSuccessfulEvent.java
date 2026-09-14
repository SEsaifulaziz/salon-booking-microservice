package com.devsaif.payment.service.messaging;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSuccessfulEvent implements Serializable {

    private Long bookingId;
    private Long paymentOrderId;
    private Long userId;
    private Long salonId;
    private Long amount;
}