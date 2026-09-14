package com.devsaif.booking.service.controller;

import com.devsaif.booking.service.domain.BookingStatus;
import com.devsaif.booking.service.domain.PaymentMethod;
import com.devsaif.booking.service.dto.*;
import com.devsaif.booking.service.mapper.BookingMapper;
import com.devsaif.booking.service.model.Booking;
import com.devsaif.booking.service.model.SalonReport;
import com.devsaif.booking.service.service.BookingService;
import com.devsaif.booking.service.service.client.PaymentFeignClient;
import com.devsaif.booking.service.service.client.SalonFeignClient;
import com.devsaif.booking.service.service.client.ServiceOfferingFeignClient;
import com.devsaif.booking.service.service.client.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final UserFeignClient  userFeignClient;
    private final SalonFeignClient  salonFeignClient;
    private final ServiceOfferingFeignClient serviceOfferingFeignClient;
    private final PaymentFeignClient  paymentFeignClient;


    @PostMapping
    public ResponseEntity<PaymentLinkResponse> createBooking(
            @RequestParam Long salonId,
            @RequestParam PaymentMethod paymentMethod,
            @RequestBody BookingRequest bookingRequest,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {
        UserDTO userDTO = userFeignClient.getUserProfile(jwt).getBody();

        SalonDTO salonDTO = salonFeignClient.getSalonById(salonId).getBody();

        Set<ServiceDTO> serviceDTO = serviceOfferingFeignClient.getServiceByIds
                (bookingRequest.getServiceIds()).getBody();

        if(serviceDTO == null ||serviceDTO.isEmpty()){
            throw new Exception("Service not found");
        }

        Booking booking = bookingService.createBooking(
                bookingRequest,
                userDTO,
                salonDTO,
                serviceDTO);

        BookingDTO bookingDTO = BookingMapper.toDTO(booking);

        PaymentLinkResponse res = paymentFeignClient.createPaymentLink(
                bookingDTO,
                paymentMethod,
                jwt
        ).getBody();

        return ResponseEntity.ok(res);
    }


    @GetMapping("/customer")
    public ResponseEntity<Set<BookingDTO>> getBookingsByCustomer(
            @RequestHeader("Authorization") String jwt

    ) throws Exception {
        UserDTO user = userFeignClient.getUserProfile(jwt).getBody();
        if(user == null || user.getId() == null){
            throw new Exception("User not found from jwt...");
        }
        List<Booking> bookings = bookingService.getBookingsByCustomerId(user.getId());

        return ResponseEntity.ok(getBookingDTOs(bookings));
    }


    @GetMapping("/salon")
    public ResponseEntity<Set<BookingDTO>> getBookingsBySalon(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        SalonDTO salon = salonFeignClient.getSalonByOwnerId(jwt).getBody();
        List<Booking> bookings = bookingService.getBookingBySalonId(salon.getId());

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
            @PathVariable LocalDate date
    ) throws Exception {

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
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        SalonDTO salon = salonFeignClient.getSalonByOwnerId(jwt).getBody();
        SalonReport salonReport = bookingService.getSalonReport(salon.getId());

        return  ResponseEntity.ok(salonReport);
    }




    private Set<BookingDTO> getBookingDTOs(List<Booking> bookingSet){
        return bookingSet.stream().map(booking ->  {
            return BookingMapper.toDTO(booking);
        }).collect(Collectors.toSet());
    }



}
