package com.devsaif.notifications.service.client;

import com.devsaif.notifications.payload.dto.BookingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("BOOKING-SERVICE")
public interface BookingFeignClient {

    @GetMapping("/api/bookings/id/{bookingId}")
    public ResponseEntity<BookingDTO> getBookingsByBookingId(
            @PathVariable Long bookingId
    ) throws Exception;


}
