package com.booking.platform.service;

import com.booking.platform.dto.BookingResponseDto;
import com.booking.platform.dto.CreateBookingRequestDto;

public interface BookingService {

    BookingResponseDto createBooking(CreateBookingRequestDto request);
}