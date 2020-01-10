package com.hnn.supermarket.pricing;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.hnn.supermarket.dao.PricingPolicy;
import com.hnn.supermarket.dao.PricingPolicyRepository;

public abstract class AbstractBuy2Get1FreePriceCalculator implements IPriceCalculator {
	
	protected PricingPolicyRepository pricingPolicyRepository;
	
    @Override
    public BigDecimal calculatePrice(BigDecimal basicPrice, int volume) {
    	if (volume >= 3) {
            // First, check for the validity of the PricingPolicy
            PricingPolicy pricingPolicy = this.pricingPolicyRepository.getPricingPolicyByCode(getPricingPolicyCode());
            if ( (pricingPolicy.getAppliedStartDate() != null && LocalDate.now().isAfter(pricingPolicy.getAppliedStartDate()))
                    && (pricingPolicy.getAppliedEndDate() != null && LocalDate.now().isBefore(pricingPolicy.getAppliedEndDate())) ) {
                // Apply the pricingPolicy here
                // Buy 2 get 1 free (TODO: should log / audit here)
                BigDecimal newPrice = basicPrice.multiply(BigDecimal.valueOf(volume));
                newPrice = newPrice.subtract(basicPrice.multiply(BigDecimal.valueOf(volume/3)));
                return newPrice;
            }
		} 
        // TODO: should log / audit here
        return basicPrice.multiply(BigDecimal.valueOf(volume));
    }
}
