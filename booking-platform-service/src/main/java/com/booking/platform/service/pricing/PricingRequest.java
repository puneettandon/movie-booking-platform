package com.booking.platform.service.pricing;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
public class PricingRequest {

    private List<BigDecimal> seatPrices;
    private LocalTime showStartTime;
}