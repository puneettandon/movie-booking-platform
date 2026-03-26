package com.booking.platform.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalTime;

@Data
@Builder
public class ShowResponseDto {

    private Long showId;
    private String theatreName;
    private String screenName;
    private LocalTime startTime;
    private LocalTime endTime;
    private String language;
    private BigDecimal basePrice;
    private Long availableSeats;
}