package com.devsaif.salon.service.service.impl;

import com.devsaif.salon.service.model.Salon;
import com.devsaif.salon.service.paload.dto.SalonDto;
import com.devsaif.salon.service.paload.dto.UserDto;
import com.devsaif.salon.service.repository.SalonRepository;
import com.devsaif.salon.service.service.SalonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalonServiceImpl implements SalonService {

    private final SalonRepository salonRepo;


    @Override
    public Salon createSalon(SalonDto req, UserDto user) {
        Salon salon = new Salon();
        salon.setName(req.getName());
        salon.setAddress(req.getAddress());
        salon.setCity(req.getCity());
        salon.setPhoneNumber(req.getPhoneNumber());
        salon.setEmail(req.getEmail());
        salon.setImages(req.getImages());
        salon.setOwnerId(user.getId());
        salon.setOpeningTime(req.getOpeningTime());
        salon.setClosingTime(req.getClosingTime());
        salon.setPhoneNumber(req.getPhoneNumber());

        return salonRepo.save(salon);
    }

    @Override
    public Salon updateSalon(SalonDto salon, UserDto user, Long salonId) throws Exception {
        Salon existingSalon = salonRepo.findById(salonId).orElse(null);
        if(!salon.getOwnerId().equals(user.getId())){
            throw new Exception("you don't have the permission to update this salon");
        }
        if(existingSalon != null) {
            existingSalon.setName(salon.getName());
            existingSalon.setAddress(salon.getAddress());
            existingSalon.setOwnerId(user.getId());
            existingSalon.setCity(salon.getCity());
            existingSalon.setPhoneNumber(salon.getPhoneNumber());
            existingSalon.setEmail(salon.getEmail());
            existingSalon.setImages(salon.getImages());
            existingSalon.setOpeningTime(salon.getOpeningTime());
            existingSalon.setClosingTime(salon.getClosingTime());
            existingSalon.setPhoneNumber(salon.getPhoneNumber());

            return salonRepo.save(existingSalon);
        }
        throw new Exception("salon not found!");
    }

    @Override
    public List<Salon> getSalons() {
        return salonRepo.findAll();
    }

    @Override
    public Salon getSalonById(Long id) throws Exception {
        Salon salon = salonRepo.findById(id).orElse(null);
        if(salon == null) {
            throw new Exception("salon not found!");
        }
        return salon;
    }

    @Override
    public void DeleteAll() {
        salonRepo.deleteAll();
    }

    @Override
    public void DeleteById(Long id) {
        salonRepo.deleteById(id);
    }

    @Override
    public Salon getSalonByOwnerId(Long ownerId) {
        return salonRepo.findByOwnerId(ownerId);
    }

    @Override
    public List<Salon> searchSalonByCityName(String cityName) {
        return salonRepo.searchSalons(cityName);
    }
}
