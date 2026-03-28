package com.booking.platform.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingEvent {

    private BookingEventType eventType;

    private String bookingId;
    private String userId;
    private String userEmail;

    private String movieName;
    private String theatreName;
    private String screenName;

    private LocalTime showTime;
    private List<String> seats;

    private BigDecimal totalAmount;
    private LocalDateTime bookedAt;

}