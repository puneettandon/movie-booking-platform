package com.booking.platform.service.impl;

import com.booking.platform.service.PricingService;
import com.booking.platform.service.pricing.PricingRequest;
import com.booking.platform.service.pricing.PricingResult;
import com.booking.platform.service.pricing.PricingRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PricingServiceImpl implements PricingService {

    private final List<PricingRule> pricingRules;

    @Override
    public PricingResult calculate(PricingRequest request) {

        BigDecimal totalAmount = request.getSeatPrices().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalDiscount = pricingRules.stream()
                .map(rule -> rule.apply(request))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal finalAmount = totalAmount.subtract(totalDiscount);

        if (finalAmount.compareTo(BigDecimal.ZERO) < 0) {
            finalAmount = BigDecimal.ZERO;
        }

        return PricingResult.builder()
                .totalAmount(totalAmount)
                .discountAmount(totalDiscount)
                .finalAmount(finalAmount)
                .build();
    }
}