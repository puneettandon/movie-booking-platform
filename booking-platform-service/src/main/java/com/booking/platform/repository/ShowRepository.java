package com.booking.platform.repository;

import com.booking.platform.entity.Show;
import com.booking.platform.enums.ShowStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Long> {

    List<Show> findByMovieIdAndScreenTheatreCityNameIgnoreCaseAndShowDateAndStatus(
            Long movieId,
            String cityName,
            LocalDate showDate,
            ShowStatus status
    );
}