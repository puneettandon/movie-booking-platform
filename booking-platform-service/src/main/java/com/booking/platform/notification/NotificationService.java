package com.booking.platform.notification;

import com.booking.platform.model.BookingEvent;

public interface NotificationService {

    void sendBookingConfirmation(BookingEvent event);

    void sendBookingCancellation(BookingEvent event);

    void sendBookingFailed(BookingEvent event);
}