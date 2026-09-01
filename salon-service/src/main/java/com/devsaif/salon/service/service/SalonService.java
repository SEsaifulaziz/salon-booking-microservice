package com.devsaif.salon.service.service;

import com.devsaif.salon.service.model.Salon;
import com.devsaif.salon.service.paload.dto.SalonDto;
import com.devsaif.salon.service.paload.dto.UserDto;

import java.util.List;

public interface SalonService {

    Salon createSalon(SalonDto salon, UserDto user);

    Salon updateSalon(SalonDto salon, UserDto user, Long salonId) throws Exception;

    List<Salon> getSalons();

    Salon getSalonById(Long id) throws Exception;

    void DeleteAll();

    void DeleteById(Long id);

    Salon getSalonByOwnerId(Long ownerId) throws  Exception;

    List<Salon> searchSalonByCityName(String cityName);


}
