package com.devsaif.notifications.payload.dto;

import lombok.Data;

import java.time.LocalTime;
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

    private LocalTime openingTime;

    private LocalTime ClosingTime;

}
