package com.booking.platform.notification;

import com.booking.platform.model.BookingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @Override
    public void sendBookingConfirmation(BookingEvent event) {
        log.info("Sending BOOKING CONFIRMATION to userEmail={} for bookingId={}, movie={}, theatre={}, seats={}",
                event.getUserEmail(),
                event.getBookingId(),
                event.getMovieName(),
                event.getTheatreName(),
                event.getSeats());
    }

    @Override
    public void sendBookingCancellation(BookingEvent event) {
        log.info("Sending BOOKING CANCELLATION to userEmail={} for bookingId={}",
                event.getUserEmail(),
                event.getBookingId());
    }

    @Override
    public void sendBookingFailed(BookingEvent event) {
        log.info("Sending BOOKING FAILED to userEmail={} for bookingId={}",
                event.getUserEmail(),
                event.getBookingId());
    }
}