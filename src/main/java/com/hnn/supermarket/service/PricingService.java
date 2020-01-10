package com.hnn.supermarket.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.hnn.supermarket.dao.*;
import com.hnn.supermarket.pricing.IPriceCalculator;
import com.hnn.supermarket.pricing.PriceCalculatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PricingService {

    @Autowired
    private PricingPolicyRepository pricingPolicyRepository;
    @Autowired
    private ItemRepository itemRepository;

    public BigDecimal getPriceForItem(Item item, int volume) {
        return getRecalculatedPrice(item.getPricingPolicy(), item.getPrice(), volume);
    }

    private BigDecimal getRecalculatedPrice(PricingPolicy pricingPolicy, BigDecimal basicPrice, int volume) {
        IPriceCalculator priceCalculator = PriceCalculatorFactory.getPriceCalculator(pricingPolicy.getCode(), pricingPolicyRepository);
        return priceCalculator.calculatePrice(basicPrice, volume);
    }

    public List<Item> getItemsWithoutPricingPolicy() {
        List<Item> allItems = itemRepository.findAll();
        return allItems.stream().filter(i -> !i.hasPricingPolicy()).collect(Collectors.toList());
    }

    /**
     * @param policy
     * @return empty list if the policy parameter is null, <br/> 
     * 			otherwise return list of items that has this policy
     */
    public List<Item> getItemsForPricingPolicy(PricingPolicy policy) {
    	if (policy == null) {
			return new ArrayList<Item>(); 
		}
    	List<Item> allItems = itemRepository.findAll();
    	return allItems.stream().filter(i -> i.getPricingPolicy().getCode().equals(policy.getCode())).collect(Collectors.toList());
	}
    
    public void applyPricingPolicyOnItem(String policyCode, String itemCode) {
    	Item item = this.itemRepository.getItemByCode(itemCode);
    	if (item != null) {
			PricingPolicy policy = this.pricingPolicyRepository.getPricingPolicyByCode(policyCode);
			item.setPricingPolicy(policy);
			item = itemRepository.save(item);
		} else {
			// TODO: audit here - item not found
		}
    }
}
