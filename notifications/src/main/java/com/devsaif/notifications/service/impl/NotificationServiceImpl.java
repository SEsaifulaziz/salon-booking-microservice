package com.devsaif.notifications.service.impl;

import com.devsaif.notifications.model.NotificationEntity;
import com.devsaif.notifications.payload.dto.NotificationDTO;
import com.devsaif.notifications.repository.NotificationRepository;
import com.devsaif.notifications.service.NotificationService;
import com.devsaif.notifications.service.client.BookingFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final BookingFeignClient bookingFeignClient;


    @Override
    public NotificationDTO createNotification(NotificationDTO notificationDTO) {
        return null;
    }

    @Override
    public List<NotificationEntity> getAllNotificationsByUserId(Long userId) {
        return List.of();
    }

    @Override
    public List<NotificationEntity> getAllNotificationsBySalonId(Long salonId) {
        return List.of();
    }

    @Override
    public NotificationEntity markNotificationAsRead(Long notificationId) {
        return null;
    }
}
