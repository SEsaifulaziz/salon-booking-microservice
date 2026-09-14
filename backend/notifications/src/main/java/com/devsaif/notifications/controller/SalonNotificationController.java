package com.devsaif.notifications.controller;

import com.devsaif.notifications.mapper.NotificationMapper;
import com.devsaif.notifications.model.Notification;
import com.devsaif.notifications.payload.dto.BookingDTO;
import com.devsaif.notifications.payload.dto.NotificationDTO;
import com.devsaif.notifications.service.NotificationService;
import com.devsaif.notifications.service.client.BookingFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications/salon-owner")
public class SalonNotificationController {

    private final  NotificationService notificationService;
    private final BookingFeignClient  bookingFeignClient;


    @GetMapping("/salon/{salonId}")
    public ResponseEntity<List<NotificationDTO>> getAllNotificationsBySalonId(
            @PathVariable Long salonId
    ){
        List<Notification> notifications = notificationService.getAllNotificationsBySalonId(salonId);

        List<NotificationDTO> notificationDTOS = notifications.stream()
                .map((notification ->{
                    BookingDTO bookingDTO = null;
                    try {
                        bookingDTO = bookingFeignClient.getBookingsByBookingId(
                                notification.getBookingId()).getBody();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    return NotificationMapper.toDTO(notification, bookingDTO);
                })).collect(Collectors.toList());

        return ResponseEntity.ok(notificationDTOS);
    }

}
