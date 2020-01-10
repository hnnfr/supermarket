package com.hnn.supermarket.pricing;

import com.hnn.supermarket.dao.PricingPolicy;
import com.hnn.supermarket.dao.PricingPolicyRepository;

public class PriceCalculatorFactory {
    public static IPriceCalculator getPriceCalculator(String policyCode, PricingPolicyRepository pricingPolicyRepository) {
        switch (policyCode) {
            case PricingPolicy.PP_2019_NOEL_REDUCTION:
                return new Noel2019ReductionPriceCalculator(pricingPolicyRepository);
            case PricingPolicy.PP_NOV_2019_BUY_2_GET_1_FREE:
                return new Nov2019Buy2Get1FreePriceCalculator(pricingPolicyRepository);
            case PricingPolicy.PP_DEC_2019_BUY_2_GET_1_FREE:
                return new Dec2019Buy2Get1FreePriceCalculator(pricingPolicyRepository);
            case PricingPolicy.PP_JAN_2020_BUY_2_GET_1_FREE:
                return new Jan2020Buy2Get1FreePriceCalculator(pricingPolicyRepository);
            case PricingPolicy.PP_GROUP_3_FOR_1_EURO:
                return new Group3For1EuroPriceCalculator(pricingPolicyRepository);
            default:
                return new BasicPriceCalculator();
        }
    }
}
