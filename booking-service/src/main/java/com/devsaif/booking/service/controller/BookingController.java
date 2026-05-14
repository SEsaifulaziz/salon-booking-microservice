package com.devsaif.booking.service.controller;

import com.devsaif.booking.service.dto.*;
import com.devsaif.booking.service.mapper.BookingMapper;
import com.devsaif.booking.service.model.Booking;
import com.devsaif.booking.service.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(
            @RequestParam Long salonId,
            @RequestBody BookingRequest bookingRequest
            ) throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);

        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(salonId);

        Set<ServiceDTO> serviceDTOSet = new HashSet<>();

        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setId(1L);
        serviceDTO.setPrice(399);
        serviceDTO.setDuration(45);

        serviceDTOSet.add(serviceDTO);

        Booking booking = bookingService.createBooking(bookingRequest,
                userDTO,
                salonDTO,
                serviceDTOSet);

        return ResponseEntity.ok().body(booking);
    }


    @GetMapping("/customer/")
    public ResponseEntity<Set<BookingDTO>> getBookingsByCustomer(
            @RequestParam Long salonId,
            @RequestParam Long customerId
    ){
        List<Booking> bookings = bookingService.getBookingsByCustomerId(1L);

        return ResponseEntity.ok(getBookingDTOs(bookings));
    }

    private Set<BookingDTO> getBookingDTOs(List<Booking> bookingSet){
        return bookingSet.stream().map(booking ->  {
            return BookingMapper.toDTO(booking);
        }).collect(Collectors.toSet());

    }

}
