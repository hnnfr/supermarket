package com.hnn.supermarket.pricing;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.hnn.supermarket.dao.PricingPolicy;
import com.hnn.supermarket.dao.PricingPolicyRepository;

public class Noel2019ReductionPriceCalculator implements IPriceCalculator {

    private PricingPolicyRepository pricingPolicyRepository;

    public Noel2019ReductionPriceCalculator(PricingPolicyRepository pricingPolicyRepository) {
        this.pricingPolicyRepository = pricingPolicyRepository;
    }

    @Override
    public BigDecimal calculatePrice(BigDecimal basicPrice, int volume) {
        // First, check for the validity of the PricingPolicy
        PricingPolicy pricingPolicy = this.pricingPolicyRepository.getPricingPolicyByCode(getPricingPolicyCode());
        if ( (pricingPolicy.getAppliedStartDate() != null && LocalDate.now().isAfter(pricingPolicy.getAppliedStartDate()))
                && (pricingPolicy.getAppliedEndDate() != null && LocalDate.now().isBefore(pricingPolicy.getAppliedEndDate())) ) {
            // Apply the pricingPolicy here
            // Reduction of 20% (TODO: should log / audit here)
            BigDecimal newPrice = new BigDecimal("0.8").multiply(basicPrice).multiply(BigDecimal.valueOf(volume));
            return newPrice;
        }
        // TODO: should log / audit here
        return basicPrice.multiply(BigDecimal.valueOf(volume));
    }

    @Override
    public String getPricingPolicyCode() {
        return PricingPolicy.PP_2019_NOEL_REDUCTION;
    }
}
