package com.booking.platform.service.pricing;

import java.math.BigDecimal;

public interface PricingRule {

    BigDecimal apply(PricingRequest request);

    String getRuleName();
}