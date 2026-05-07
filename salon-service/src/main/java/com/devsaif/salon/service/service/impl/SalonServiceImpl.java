package com.devsaif.salon.service.service.impl;

import com.devsaif.salon.service.model.Salon;
import com.devsaif.salon.service.paload.dto.SalonDto;
import com.devsaif.salon.service.paload.dto.UserDto;
import com.devsaif.salon.service.service.SalonService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalonServiceImpl implements SalonService {

    @Override
    public Salon createSalon(SalonDto salon, UserDto user) {
        return null;
    }

    @Override
    public Salon updateSalon(SalonDto salon, UserDto user, Long salonId) {
        return null;
    }

    @Override
    public List<Salon> getSalons() {
        return List.of();
    }

    @Override
    public Salon getSalonById(Long id) {
        return null;
    }

    @Override
    public Salon DeleteAll() {
        return null;
    }

    @Override
    public Salon DeleteById(Long id) {
        return null;
    }

    @Override
    public Salon getSalonByOwnerId(Long ownerId) {
        return null;
    }

    @Override
    public List<Salon> searchSalonByCityName(String cityName) {
        return List.of();
    }
}
