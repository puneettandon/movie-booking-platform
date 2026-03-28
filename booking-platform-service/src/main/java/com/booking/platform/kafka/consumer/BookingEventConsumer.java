package com.booking.platform.kafka.consumer;


import com.booking.platform.kafka.KafkaTopics;
import com.booking.platform.model.BookingEvent;
import com.booking.platform.notification.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class BookingEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(BookingEventConsumer.class);

    private final NotificationService notificationService;

    public BookingEventConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = KafkaTopics.BOOKING_EVENTS, groupId = "notification-group")
    public void consume(BookingEvent event) {
        log.info("Received booking event type={} bookingId={}", event.getEventType(), event.getBookingId());

        if (event.getEventType() == null) {
            log.warn("Received booking event with null eventType for bookingId={}", event.getBookingId());
            return;
        }

        switch (event.getEventType()) {
            case BOOKING_CONFIRMED -> notificationService.sendBookingConfirmation(event);
            case BOOKING_CANCELLED -> notificationService.sendBookingCancellation(event);
            case BOOKING_FAILED ->    notificationService.sendBookingFailed(event);
            default -> log.info("No notification action configured for eventType={} bookingId={}",
                    event.getEventType(), event.getBookingId());
        }
    }
}