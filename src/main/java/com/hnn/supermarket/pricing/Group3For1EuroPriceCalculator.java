package com.hnn.supermarket.pricing;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.hnn.supermarket.dao.PricingPolicy;
import com.hnn.supermarket.dao.PricingPolicyRepository;

public class Group3For1EuroPriceCalculator implements IPriceCalculator {
	
    private PricingPolicyRepository pricingPolicyRepository;

    public Group3For1EuroPriceCalculator(PricingPolicyRepository pricingPolicyRepository) {
        this.pricingPolicyRepository = pricingPolicyRepository;
    }

    @Override
    public BigDecimal calculatePrice(BigDecimal basicPrice, int volume) {
        // First, check for the validity of the PricingPolicy
        PricingPolicy pricingPolicy = this.pricingPolicyRepository.getPricingPolicyByCode(getPricingPolicyCode());
        if ( (pricingPolicy.getAppliedStartDate() != null && LocalDate.now().isAfter(pricingPolicy.getAppliedStartDate()))
                && (pricingPolicy.getAppliedEndDate() != null && LocalDate.now().isBefore(pricingPolicy.getAppliedEndDate())) ) {
            // Apply the pricingPolicy here
            // 3 items cost 1 euro (TODO: should log / audit here)
            BigDecimal newPrice = BigDecimal.ZERO;
            if (volume >= 3) {
                newPrice = newPrice.add(BigDecimal.ONE.multiply(BigDecimal.valueOf(volume/3)));
            }
            newPrice = newPrice.add(basicPrice.multiply(BigDecimal.valueOf(volume%3)));
            return newPrice;
        }
        // TODO: should log / audit here
        return basicPrice.multiply(BigDecimal.valueOf(volume));
    }

    @Override
    public String getPricingPolicyCode() {
        return PricingPolicy.PP_GROUP_3_FOR_1_EURO;
    }
}
