package com.hnn.supermarket.pricing;

import com.hnn.supermarket.dao.PricingPolicy;
import com.hnn.supermarket.dao.PricingPolicyRepository;

public class Jan2020Buy2Get1FreePriceCalculator extends AbstractBuy2Get1FreePriceCalculator {

    public Jan2020Buy2Get1FreePriceCalculator(PricingPolicyRepository pricingPolicyRepository) {
        this.pricingPolicyRepository = pricingPolicyRepository;
    }

    @Override
    public String getPricingPolicyCode() {
        return PricingPolicy.PP_JAN_2020_BUY_2_GET_1_FREE;
    }
}
