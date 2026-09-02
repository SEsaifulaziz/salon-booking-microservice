package com.devsaif.booking.service.mapper;

import com.devsaif.booking.service.dto.BookingDTO;
import com.devsaif.booking.service.model.Booking;

public class BookingMapper {

    public static BookingDTO toDTO(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();

        bookingDTO.setId(booking.getId());
        bookingDTO.setCustomerId(booking.getCustomerId());
        bookingDTO.setStatus(booking.getStatus());
        bookingDTO.setStartTime(booking.getStartTime());
        bookingDTO.setEndTime(booking.getEndTime());
        bookingDTO.setServiceIds(booking.getServiceIds());
        bookingDTO.setSalonId(booking.getSalonId());
        bookingDTO.setTotalPrice(booking.getTotalPrice());
        return bookingDTO;

    }
}
