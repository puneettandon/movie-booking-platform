package com.booking.platform.controller;

import com.booking.platform.dto.SeatLayoutResponseDto;
import com.booking.platform.dto.ShowResponseDto;
import com.booking.platform.service.ShowService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/shows")
@RequiredArgsConstructor
public class ShowController {

    private final ShowService showService;

    @Operation(summary = "Search shows by movie, city and date")
    @GetMapping("/search")
    public List<ShowResponseDto> searchShows(
            @RequestParam Long movieId,
            @RequestParam String city,
            @RequestParam String date
    ) {
        return showService.searchShows(
                movieId,
                city,
                LocalDate.parse(date)
        );
    }

    @Operation(summary = "Get seat layout for a specific show")
    @GetMapping("/{showId}/seats")
    public List<SeatLayoutResponseDto> getSeatLayout(@PathVariable Long showId) {
        return showService.getSeatLayout(showId);
    }
}