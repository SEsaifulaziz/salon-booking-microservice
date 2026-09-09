package com.devsaif.notifications.messaging;


import com.devsaif.notifications.configuration.RabbitMQConfig;
import com.devsaif.notifications.model.Notification;
import com.devsaif.notifications.repository.NotificationRepository;
import com.devsaif.notifications.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PaymentEventListener {

    private static final String PAYMENT_SUCCESSFUL = "PAYMENT_SUCCESSFUL";

    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;

    @RabbitListener(queues = RabbitMQConfig.NOTIFICATION_QUEUE)
    public void onPaymentSuccessful(PaymentSuccessfulEvent event) throws Exception {

        boolean alreadyExists = notificationRepository
                .existsByPaymentOrderIdAndType(
                        event.getPaymentOrderId(),
                        PAYMENT_SUCCESSFUL
                );
        if(alreadyExists){
            return;
        }

            Notification notification = new Notification();
            notification.setType(PAYMENT_SUCCESSFUL);
            notification.setDescription(
                    "Your payment was successful and your booking is confirmed."
            );
            notification.setUserId(event.getUserId());
            notification.setBookingId(event.getBookingId());
            notification.setSalonId(event.getSalonId());
            notification.setCreatedAt(LocalDateTime.now());

            notificationService.createNotification(notification);

    }
}