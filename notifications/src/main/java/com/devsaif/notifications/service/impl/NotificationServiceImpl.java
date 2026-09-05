package com.devsaif.notifications.service.impl;

import com.devsaif.notifications.mapper.NotificationMapper;
import com.devsaif.notifications.model.Notification;
import com.devsaif.notifications.payload.dto.BookingDTO;
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
    public NotificationDTO createNotification(Notification notification) throws Exception{

        Notification savedNotification = notificationRepository.save(notification);

        BookingDTO  bookingDTO = bookingFeignClient.getBookingsByBookingId(
                savedNotification.getBookingId()).getBody();

        NotificationDTO notificationDTO = NotificationMapper.toDTO(
                savedNotification, bookingDTO
        );

        return notificationDTO;
    }

    @Override
    public List<Notification> getAllNotificationsByUserId(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    @Override
    public List<Notification> getAllNotificationsBySalonId(Long salonId) {
        return notificationRepository.findBySalonId(salonId);
    }

    @Override
    public Notification markNotificationAsRead(Long notificationId) throws Exception {

        return notificationRepository.findById(notificationId).map(
                notification -> {
                    notification.setIsRead(true);
                    return notificationRepository.save(notification);
                }
        ).orElseThrow(() -> new Exception("Notification not found"));
    }
}
