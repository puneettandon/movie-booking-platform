package com.booking.platform.service.pricing;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalTime;

@Component
public class AfternoonShowDiscountRule implements PricingRule {

    private static final LocalTime AFTERNOON_START = LocalTime.of(12, 0);
    private static final LocalTime AFTERNOON_END = LocalTime.of(17, 0);

    @Override
    public BigDecimal apply(PricingRequest request) {

        LocalTime showStartTime = request.getShowStartTime();

        if (showStartTime == null ||
                showStartTime.isBefore(AFTERNOON_START) ||
                showStartTime.isAfter(AFTERNOON_END)) {
            return BigDecimal.ZERO;
        }

        BigDecimal total = request.getSeatPrices().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return total.multiply(BigDecimal.valueOf(0.20));
    }

    @Override
    public String getRuleName() {
        return "AFTERNOON_SHOW_DISCOUNT";
    }
}