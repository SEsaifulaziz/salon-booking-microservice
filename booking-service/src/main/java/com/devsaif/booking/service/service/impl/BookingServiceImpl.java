package com.devsaif.booking.service.service.impl;

import com.devsaif.booking.service.domain.BookingStatus;
import com.devsaif.booking.service.dto.BookingRequest;
import com.devsaif.booking.service.dto.SalonDTO;
import com.devsaif.booking.service.dto.ServiceDTO;
import com.devsaif.booking.service.dto.UserDTO;
import com.devsaif.booking.service.model.Booking;
import com.devsaif.booking.service.model.SalonReport;
import com.devsaif.booking.service.service.BookingService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class BookingServiceImpl implements BookingService {
    @Override
    public Booking createBooking(BookingRequest booking, UserDTO userDTO, SalonDTO salonDTO, Set<ServiceDTO> services) {
        return null;
    }

    @Override
    public List<Booking> getBookingsByCustomerId(Long customerId) {
        return List.of();
    }

    @Override
    public List<Booking> getBookingBySalonId(Long salonId) {
        return List.of();
    }

    @Override
    public Booking getBookingById(Long id) {
        return null;
    }

    @Override
    public Booking updateBooking(Long id, BookingStatus bookingStatus) {
        return null;
    }

    @Override
    public List<Booking> getBookingsByDate(LocalDate date, Long salonId) {
        return List.of();
    }

    @Override
    public SalonReport getSalonReport(Long salonId) {
        return null;
    }
}
