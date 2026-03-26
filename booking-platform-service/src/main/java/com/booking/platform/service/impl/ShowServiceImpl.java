package com.booking.platform.service.impl;

import com.booking.platform.dto.SeatLayoutResponseDto;
import com.booking.platform.dto.ShowResponseDto;
import com.booking.platform.entity.Show;
import com.booking.platform.entity.ShowSeatInventory;
import com.booking.platform.enums.SeatAvailabilityStatus;
import com.booking.platform.enums.ShowStatus;
import com.booking.platform.repository.ShowRepository;
import com.booking.platform.repository.ShowSeatInventoryRepository;
import com.booking.platform.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowServiceImpl implements ShowService {

    private final ShowRepository showRepository;
    private final ShowSeatInventoryRepository seatInventoryRepository;

    @Override
    public List<ShowResponseDto> searchShows(Long movieId, String city, LocalDate date) {

        List<Show> shows = showRepository
                .findByMovieIdAndScreenTheatreCityNameIgnoreCaseAndShowDateAndStatus(
                        movieId,
                        city,
                        date,
                        ShowStatus.ACTIVE
                );

        return shows.stream().map(show -> {

            long availableSeats = seatInventoryRepository
                    .findByShowIdAndStatus(show.getId(), SeatAvailabilityStatus.AVAILABLE)
                    .size();

            return ShowResponseDto.builder()
                    .showId(show.getId())
                    .theatreName(show.getScreen().getTheatre().getName())
                    .screenName(show.getScreen().getName())
                    .startTime(show.getStartTime())
                    .endTime(show.getEndTime())
                    .language(show.getLanguage())
                    .basePrice(show.getBasePrice())
                    .availableSeats(availableSeats)
                    .build();

        }).toList();
    }

    @Override
    public List<SeatLayoutResponseDto> getSeatLayout(Long showId) {

        List<ShowSeatInventory> seatInventoryList = seatInventoryRepository.findByShowId(showId);

        return seatInventoryList.stream()
                .map(seatInventory -> SeatLayoutResponseDto.builder()
                        .seatInventoryId(seatInventory.getId())
                        .seatNumber(seatInventory.getSeat().getSeatNumber())
                        .seatRow(seatInventory.getSeat().getSeatRow())
                        .seatType(seatInventory.getSeat().getSeatType())
                        .status(seatInventory.getStatus())
                        .price(seatInventory.getPrice())
                        .build())
                .toList();
    }
}