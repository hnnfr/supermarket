package com.hnn.supermarket.pricing;

import com.hnn.supermarket.dao.PricingPolicy;
import com.hnn.supermarket.dao.PricingPolicyRepository;

public class Dec2019Buy2Get1FreePriceCalculator extends AbstractBuy2Get1FreePriceCalculator {

    public Dec2019Buy2Get1FreePriceCalculator(PricingPolicyRepository pricingPolicyRepository) {
        this.pricingPolicyRepository = pricingPolicyRepository;
    }

    @Override
    public String getPricingPolicyCode() {
        return PricingPolicy.PP_DEC_2019_BUY_2_GET_1_FREE;
    }
}
