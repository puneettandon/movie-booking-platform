package com.booking.platform.dto;

import com.booking.platform.enums.SeatAvailabilityStatus;
import com.booking.platform.enums.SeatType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class SeatLayoutResponseDto {

    private Long seatInventoryId;
    private String seatNumber;
    private String seatRow;
    private SeatType seatType;
    private SeatAvailabilityStatus status;
    private BigDecimal price;
}