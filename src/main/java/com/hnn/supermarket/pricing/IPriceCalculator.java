package com.hnn.supermarket.pricing;

import java.math.BigDecimal;

public interface IPriceCalculator {
    BigDecimal calculatePrice(BigDecimal basicPrice, int volume);
    String getPricingPolicyCode();
}
