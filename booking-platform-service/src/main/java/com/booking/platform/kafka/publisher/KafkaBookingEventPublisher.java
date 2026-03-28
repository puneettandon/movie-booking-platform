package com.booking.platform.kafka.publisher;

import com.booking.platform.kafka.KafkaTopics;
import com.booking.platform.model.BookingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaBookingEventPublisher implements BookingEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(KafkaBookingEventPublisher.class);

    private final KafkaTemplate<String, BookingEvent> kafkaTemplate;

    public KafkaBookingEventPublisher(KafkaTemplate<String, BookingEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publishBookingEvent(BookingEvent event) {
        kafkaTemplate.send(KafkaTopics.BOOKING_EVENTS, event.getBookingId(), event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to publish booking event type={} bookingId={}",
                                event.getEventType(), event.getBookingId(), ex);
                    } else {
                        log.info("Published booking event type={} bookingId={} topic={} partition={} offset={}",
                                event.getEventType(),
                                event.getBookingId(),
                                result.getRecordMetadata().topic(),
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    }
                });
    }
}