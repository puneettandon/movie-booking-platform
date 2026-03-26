package com.booking.platform.controller;

import com.booking.platform.dto.BookingResponseDto;
import com.booking.platform.dto.CreateBookingRequestDto;
import com.booking.platform.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @Operation(summary = "Create a movie ticket booking")
    @PostMapping
    public BookingResponseDto createBooking(@Valid @RequestBody CreateBookingRequestDto request) {
        return bookingService.createBooking(request);
    }
}