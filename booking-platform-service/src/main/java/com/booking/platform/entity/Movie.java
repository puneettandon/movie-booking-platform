package com.booking.platform.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "movies")
@Getter
@Setter
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String language;

    private String genre;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    private String certificate;

    @Column(name = "release_date")
    private LocalDate releaseDate;
}