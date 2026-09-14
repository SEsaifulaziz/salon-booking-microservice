package com.devsaif.salon.service.mapper;

import com.devsaif.salon.service.model.Salon;
import com.devsaif.salon.service.paload.dto.SalonDto;

public class SalonMapper {

    public static SalonDto mapToDto(Salon salon) {
        SalonDto salonDto = new SalonDto();
        salonDto.setId(salon.getId());

        salonDto.setName(salon.getName());
        salonDto.setEmail(salon.getEmail());
        salonDto.setAddress(salon.getAddress());
        salonDto.setCity(salon.getCity());
        salonDto.setImages(salon.getImages());
        salonDto.setClosingTime(salon.getClosingTime());
        salonDto.setOpeningTime(salon.getOpeningTime());
        salonDto.setOwnerId(salon.getOwnerId());
        salonDto.setPhoneNumber(salon.getPhoneNumber());
        return salonDto;
    }
}
