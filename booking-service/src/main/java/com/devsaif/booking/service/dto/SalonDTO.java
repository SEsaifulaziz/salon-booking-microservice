package com.devsaif.service.offering.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SalonDTO {

    private Long id;

    private String name;

    private Long ownerId;

    private List<String> images;

    private String address;

    private String phoneNumber;

    private String email;

    private String city;

    private LocalDateTime openingTime;

    private LocalDateTime ClosingTime;

}
