package com.devsaif.category.service.dto;

import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
public class SalonDto {

    private Long id;

    private String name;

    private Long ownerId;

    private List<String> images;

    private String address;

    private String phoneNumber;

    private String email;

    private String city;

    private LocalTime openingTime;

    private LocalTime ClosingTime;

}
