package com.booking.platform.dto;

import com.booking.platform.enums.BookingStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class BookingResponseDto {

    private String bookingReference;
    private BookingStatus status;
    private Long showId;
    private List<String> bookedSeats;
    private BigDecimal totalAmount;
    private BigDecimal discountAmount;
    private BigDecimal finalAmount;
}