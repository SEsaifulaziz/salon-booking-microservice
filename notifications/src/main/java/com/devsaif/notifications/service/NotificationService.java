package com.devsaif.notifications.service;

import com.devsaif.notifications.model.Notification;
import com.devsaif.notifications.payload.dto.NotificationDTO;

import java.util.List;


public interface NotificationService {

    NotificationDTO createNotification(Notification notification) throws Exception;

    List<Notification> getAllNotificationsByUserId(Long userId);
    List<Notification> getAllNotificationsBySalonId(Long salonId);

    Notification markNotificationAsRead(Long notificationId) throws Exception;

}
