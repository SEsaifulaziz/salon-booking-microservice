package com.devsaif.payment.service.messaging;

import com.devsaif.payment.service.model.PaymentOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingEvenProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendBookingUpdateEven(PaymentOrder paymentOrder) {
        rabbitTemplate.convertAndSend("booking-queue", paymentOrder);
    }
}
