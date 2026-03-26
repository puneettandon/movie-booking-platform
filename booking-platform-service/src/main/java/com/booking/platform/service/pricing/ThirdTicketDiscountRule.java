package com.booking.platform.service.pricing;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Component
public class ThirdTicketDiscountRule implements PricingRule {

    @Override
    public BigDecimal apply(PricingRequest request) {

        List<BigDecimal> seatPrices = request.getSeatPrices();

        if (seatPrices == null || seatPrices.size() < 3) {
            return BigDecimal.ZERO;
        }

        List<BigDecimal> sortedPrices = seatPrices.stream()
                .sorted(Comparator.naturalOrder())
                .toList();

        BigDecimal thirdTicketPrice = sortedPrices.get(2);

        return thirdTicketPrice.multiply(BigDecimal.valueOf(0.5));
    }

    @Override
    public String getRuleName() {
        return "THIRD_TICKET_DISCOUNT";
    }
}