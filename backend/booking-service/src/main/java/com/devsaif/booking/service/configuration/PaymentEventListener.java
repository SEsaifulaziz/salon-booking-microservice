package com.devsaif.booking.service.configuration;

import com.devsaif.booking.service.domain.BookingStatus;
import com.devsaif.booking.service.messaging.PaymentSuccessfulEvent;
import com.devsaif.booking.service.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventListener {

    private final BookingService bookingService;

    @RabbitListener(queues = RabbitMQConfig.BOOKING_QUEUE)
    public void onPaymentSuccessful(PaymentSuccessfulEvent event) throws Exception {
        bookingService.updateBooking(
                event.getBookingId(),
                BookingStatus.CONFIRMED
        );
    }
}
