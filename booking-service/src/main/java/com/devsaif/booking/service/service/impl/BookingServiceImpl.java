package com.devsaif.booking.service.service.impl;

import com.devsaif.booking.service.domain.BookingStatus;
import com.devsaif.booking.service.dto.BookingRequest;
import com.devsaif.booking.service.dto.SalonDTO;
import com.devsaif.booking.service.dto.ServiceDTO;
import com.devsaif.booking.service.dto.UserDTO;
import com.devsaif.booking.service.model.Booking;
import com.devsaif.booking.service.model.SalonReport;
import com.devsaif.booking.service.repository.BookingRepository;
import com.devsaif.booking.service.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.jaxb.internal.stax.LocalSchemaLocator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepo;

    @Override
    public Booking createBooking(BookingRequest booking,
                                 UserDTO userDTO,
                                 SalonDTO salonDTO,
                                 Set<ServiceDTO> services) throws Exception {

        int totalDuration = services.stream().
                mapToInt(ServiceDTO::getDuration)
                .sum();

        LocalDateTime bookingStartTime = booking.getStartTime();
        LocalDateTime bookingEndTime = bookingStartTime.plusDays(totalDuration);

        Boolean isSlotAvailable = isTimeSlotAvailable(salonDTO, bookingStartTime, bookingEndTime);

        int totalPrice = services.stream().mapToInt(ServiceDTO::getPrice).sum();

        Set<Long> idList = services.stream().map(ServiceDTO::getId).collect(Collectors.toSet());

        Booking newBookings = new Booking();

        newBookings.setCustomerId(userDTO.getId());
        newBookings.setSalonId(salonDTO.getId());
        newBookings.setServiceIds(idList);
        newBookings.setStatus(BookingStatus.PENDING);
        newBookings.setStartTime(bookingStartTime);
        newBookings.setEndTime(bookingEndTime);
        newBookings.setTotalPrice(totalPrice);

        return bookingRepo.save(newBookings);

    }

    public Boolean isTimeSlotAvailable(SalonDTO salonDTO,
                                       LocalDateTime bookingStartTime,
                                       LocalDateTime bookingEndTime) throws Exception {

        List<Booking> existingBookings = getBookingBySalonId(salonDTO.getId());

        LocalDateTime salonOpeningTime = salonDTO.getOpeningTime().atDate(bookingStartTime.toLocalDate());
        LocalDateTime salonClosingTime = salonDTO.getClosingTime().atDate(bookingEndTime.toLocalDate());

        if(bookingStartTime.isBefore(salonOpeningTime)
                || bookingEndTime.isAfter(salonClosingTime)){
            throw new Exception("Booking time be within salon's working hours");
        }

        for(Booking existingBooking : existingBookings){
            LocalDateTime existingBookingStartTime = existingBooking.getStartTime();
            LocalDateTime existingBookingEndTime = existingBooking.getEndTime();

            if(existingBookingStartTime.isBefore(existingBookingEndTime)
                    && bookingEndTime.isAfter(existingBookingStartTime)){
                throw new Exception("slot not available, choose different time");
            }

            if(bookingStartTime.equals(existingBookingStartTime)
                    || bookingEndTime.equals(existingBookingEndTime)){
                throw new Exception("slot not available, choose different time");
            }
        }

        return true;
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
