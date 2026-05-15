package com.devsaif.booking.service.controller;

import com.devsaif.booking.service.domain.BookingStatus;
import com.devsaif.booking.service.dto.*;
import com.devsaif.booking.service.mapper.BookingMapper;
import com.devsaif.booking.service.model.Booking;
import com.devsaif.booking.service.model.SalonReport;
import com.devsaif.booking.service.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        salonDTO.setOpeningTime(LocalTime.now());
        salonDTO.setClosingTime(LocalTime.now().plusHours(10));

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

    ){
        List<Booking> bookings = bookingService.getBookingsByCustomerId(1L);

        return ResponseEntity.ok(getBookingDTOs(bookings));
    }


    @GetMapping("/salon/")
    public ResponseEntity<Set<BookingDTO>> getBookingsBySalon(

    ){
        List<Booking> bookings = bookingService.getBookingBySalonId(1L);

        return ResponseEntity.ok(getBookingDTOs(bookings));
    }

    @GetMapping("/id/{bookingId}")
    public ResponseEntity<BookingDTO> getBookingsByBookingId(@PathVariable Long bookingId) throws Exception {
        Booking bookings = bookingService.getBookingById(bookingId);

        return ResponseEntity.ok(BookingMapper.toDTO(bookings));
    }


    @PutMapping("/id/{bookingId}/status")
    public ResponseEntity<BookingDTO> updateBookingStatus(
            @PathVariable Long bookingId,
            @RequestParam BookingStatus status
    ) throws Exception {
        Booking booking = bookingService.updateBooking(bookingId, status);

        return ResponseEntity.ok(BookingMapper.toDTO(booking));
    }

    @GetMapping("/slot/salon/{salonId}/date/{date}")
    public ResponseEntity<List<SlotDto>> getBookedSlot(
            @PathVariable Long salonId,
            @PathVariable @RequestParam(required = false) LocalDate date) throws Exception {

        List<Booking> bookings = bookingService.getBookingsByDate(date, salonId);

        List<SlotDto>  slotDTOs = bookings.stream()
                .map(booking -> {
                    SlotDto slotDto = new SlotDto();
                    slotDto.setStartTime(booking.getStartTime());
                    slotDto.setEndTime(booking.getEndTime());
                    return slotDto;
                }).collect(Collectors.toList());

        return ResponseEntity.ok(slotDTOs);
    }

    @GetMapping("/report")
    public ResponseEntity<SalonReport> getBookingsReport(

    ) throws Exception {
        SalonReport salonReport = bookingService.getSalonReport(1L);

        return  ResponseEntity.ok(salonReport);
    }




    private Set<BookingDTO> getBookingDTOs(List<Booking> bookingSet){
        return bookingSet.stream().map(booking ->  {
            return BookingMapper.toDTO(booking);
        }).collect(Collectors.toSet());
    }



}
