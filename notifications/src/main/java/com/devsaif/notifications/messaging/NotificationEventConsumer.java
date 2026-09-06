package com.devsaif.notifications.messaging;

import com.devsaif.notifications.model.Notification;
import com.devsaif.notifications.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NotificationEventConsumer {

    private final NotificationService notificationService;

    @RabbitListener(queues = "notification-queue")
    public void sendNotificationEventConsumer(Notification notification) throws Exception {
        notificationService.createNotification(notification);

    }
}
