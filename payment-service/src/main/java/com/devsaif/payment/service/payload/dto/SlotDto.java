package com.devsaif.booking.service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SlotDto {

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
