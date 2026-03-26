package com.booking.platform.service;

import com.booking.platform.service.pricing.PricingRequest;
import com.booking.platform.service.pricing.PricingResult;

public interface PricingService {

    PricingResult calculate(PricingRequest request);
}