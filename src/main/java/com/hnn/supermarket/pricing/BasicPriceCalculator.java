package com.hnn.supermarket.pricing;

import java.math.BigDecimal;

import com.hnn.supermarket.dao.PricingPolicy;

public class BasicPriceCalculator implements IPriceCalculator {

    @Override
    public BigDecimal calculatePrice(BigDecimal basicPrice, int volume) {
        // TODO: should log / audit here
        return basicPrice.multiply(BigDecimal.valueOf(volume));
    }

    @Override
    public String getPricingPolicyCode() {
        return PricingPolicy.PP_BASIC;
    }
}
