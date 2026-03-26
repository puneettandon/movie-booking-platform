package com.booking.platform.service;

import com.booking.platform.dto.SeatLayoutResponseDto;
import com.booking.platform.dto.ShowResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface ShowService {

    List<ShowResponseDto> searchShows(Long movieId, String city, LocalDate date);

    List<SeatLayoutResponseDto> getSeatLayout(Long showId);
}