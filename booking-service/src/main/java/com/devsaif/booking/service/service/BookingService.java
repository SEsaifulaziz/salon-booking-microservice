package com.devsaif.booking.service.service;


import com.devsaif.booking.service.domain.BookingStatus;
import com.devsaif.booking.service.dto.BookingRequest;
import com.devsaif.booking.service.dto.SalonDTO;
import com.devsaif.booking.service.dto.ServiceDTO;
import com.devsaif.booking.service.dto.UserDTO;
import com.devsaif.booking.service.model.Booking;
import com.devsaif.booking.service.model.SalonReport;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface BookingService {

    Booking createBooking(BookingRequest booking, UserDTO userDTO,
                          SalonDTO salonDTO,
                          Set<ServiceDTO> services) throws Exception;

    List<Booking> getBookingsByCustomerId(Long customerId);
    List<Booking> getBookingBySalonId(Long salonId);
    Booking getBookingById(Long id);
    Booking updateBooking(Long id, BookingStatus bookingStatus);
    List<Booking> getBookingsByDate(LocalDate date, Long salonId);
    SalonReport getSalonReport(Long salonId);
}