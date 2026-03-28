package com.booking.platform.kafka.publisher;


import com.booking.platform.model.BookingEvent;

public interface BookingEventPublisher {
    void publishBookingEvent(BookingEvent event);
}