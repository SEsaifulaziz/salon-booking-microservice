package com.devsaif.notifications.service;

import com.devsaif.notifications.model.NotificationEntity;
import com.devsaif.notifications.payload.dto.NotificationDTO;

import java.util.List;


public interface NotificationService {

    NotificationDTO createNotification(NotificationDTO notificationDTO);

    List<NotificationEntity> getAllNotificationsByUserId(Long userId);
    List<NotificationEntity> getAllNotificationsBySalonId(Long salonId);

    NotificationEntity markNotificationAsRead(Long notificationId);

}
