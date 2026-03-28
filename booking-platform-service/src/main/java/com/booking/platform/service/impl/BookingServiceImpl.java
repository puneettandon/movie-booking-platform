package com.booking.platform.service.impl;

import com.booking.platform.dto.BookingResponseDto;
import com.booking.platform.dto.CreateBookingRequestDto;
import com.booking.platform.entity.Booking;
import com.booking.platform.entity.BookingSeat;
import com.booking.platform.entity.Show;
import com.booking.platform.entity.ShowSeatInventory;
import com.booking.platform.enums.BookingStatus;
import com.booking.platform.enums.SeatAvailabilityStatus;
import com.booking.platform.exception.BadRequestException;
import com.booking.platform.exception.ResourceNotFoundException;
import com.booking.platform.kafka.publisher.BookingEventPublisher;
import com.booking.platform.model.BookingEvent;
import com.booking.platform.model.BookingEventType;
import com.booking.platform.repository.BookingRepository;
import com.booking.platform.repository.BookingSeatRepository;
import com.booking.platform.repository.ShowRepository;
import com.booking.platform.repository.ShowSeatInventoryRepository;
import com.booking.platform.service.BookingService;
import com.booking.platform.service.PricingService;
import com.booking.platform.service.pricing.PricingRequest;
import com.booking.platform.service.pricing.PricingResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final ShowRepository showRepository;
    private final ShowSeatInventoryRepository seatInventoryRepository;
    private final BookingRepository bookingRepository;
    private final BookingSeatRepository bookingSeatRepository;
    private final PricingService pricingService;
    private final BookingEventPublisher bookingEventPublisher;

    @Override
    @Transactional
    public BookingResponseDto createBooking(CreateBookingRequestDto request) {

        Show show = showRepository.findById(request.getShowId())
                .orElseThrow(() -> new ResourceNotFoundException("Show not found"));

        List<ShowSeatInventory> selectedSeats = seatInventoryRepository
                .findByShowIdAndSeatNumbersForUpdate(request.getShowId(), request.getSeatNumbers());

        if (selectedSeats.size() != request.getSeatNumbers().size()) {
            throw new BadRequestException("Some selected seats are invalid");
        }

        boolean anyUnavailable = selectedSeats.stream()
                .anyMatch(seat -> seat.getStatus() != SeatAvailabilityStatus.AVAILABLE);

        if (anyUnavailable) {
            throw new BadRequestException("Some selected seats are not available");
        }

        List<BigDecimal> seatPrices = selectedSeats.stream()
                .map(ShowSeatInventory::getPrice)
                .toList();

        PricingRequest pricingRequest = PricingRequest.builder()
                .seatPrices(seatPrices)
                .showStartTime(show.getStartTime())
                .build();

        PricingResult pricingResult = pricingService.calculate(pricingRequest);

        BigDecimal totalAmount = pricingResult.getTotalAmount();
        BigDecimal discountAmount = pricingResult.getDiscountAmount();
        BigDecimal finalAmount = pricingResult.getFinalAmount();

        Booking booking = new Booking();
        booking.setBookingReference("BKG-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        booking.setUserId(request.getUserId());
        booking.setShow(show);
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setTotalAmount(totalAmount);
        booking.setDiscountAmount(discountAmount);
        booking.setFinalAmount(finalAmount);
        booking.setCreatedAt(LocalDateTime.now());

        Booking savedBooking = bookingRepository.save(booking);

        for (ShowSeatInventory seatInventory : selectedSeats) {
            seatInventory.setStatus(SeatAvailabilityStatus.BOOKED);
            seatInventoryRepository.save(seatInventory);

            BookingSeat bookingSeat = new BookingSeat();
            bookingSeat.setBooking(savedBooking);
            bookingSeat.setShowSeatInventory(seatInventory);
            bookingSeat.setSeatPrice(seatInventory.getPrice());

            bookingSeatRepository.save(bookingSeat);
        }

        bookingEventPublisher.publishBookingEvent(
                buildBookingEvent(savedBooking, BookingEventType.BOOKING_CONFIRMED, request.getSeatNumbers())
        );

        return BookingResponseDto.builder()
                .bookingReference(savedBooking.getBookingReference())
                .status(savedBooking.getStatus())
                .showId(savedBooking.getShow().getId())
                .bookedSeats(request.getSeatNumbers())
                .totalAmount(totalAmount)
                .discountAmount(discountAmount)
                .finalAmount(finalAmount)
                .build();
    }

    private BookingEvent buildBookingEvent(Booking booking, BookingEventType eventType,
                                           List<String> seatNumbers) {
        return new BookingEvent(
                eventType,
                booking.getId().toString(),
                booking.getUserId().toString(),
                booking.getBookingReference(),
                booking.getShow().getMovie().getTitle(),
                booking.getShow().getScreen().getTheatre().getName(),
                booking.getShow().getScreen().getName(),
                booking.getShow().getStartTime(),
                seatNumbers,
                booking.getTotalAmount(),
                java.time.LocalDateTime.now()
        );
    }
}