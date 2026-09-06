//package com.devsaif.notifications.configuration;
//
//import com.devsaif.notifications.messaging.PaymentSuccessfulEvent;
//import com.devsaif.notifications.model.Notification;
//import com.devsaif.notifications.service.NotificationService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//
//@Component
//@RequiredArgsConstructor
//public class PaymentEventListener {
//
//    private final NotificationService notificationService;
//
//    @RabbitListener(queues = RabbitMQConfig.NOTIFICATION_QUEUE)
//    public void onPaymentSuccessful(PaymentSuccessfulEvent event) {
//        try {
//            Notification notification = new Notification();
//            notification.setType("PAYMENT_SUCCESSFUL");
//            notification.setDescription("Your payment was successful and your booking is confirmed.");
//            notification.setUserId(event.getUserId());
//            notification.setBookingId(event.getBookingId());
//            notification.setSalonId(event.getSalonId());
//            notification.setCreatedAt(LocalDateTime.now());
//
//            notificationService.createNotification(notification);
//        } catch (Exception e) {
//            System.err.println("Failed to create notification for booking " + event.getBookingId() + ": " + e.getMessage());
//        }
//    }
//}