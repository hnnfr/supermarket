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
    @Autowired
    private PricingHistoryRepository pricingHistoryRepository; 

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
    	Item item = this.itemRepository.getItemWithPricingHistories(itemCode);
    	if (item != null) {
    		if (!item.getPricingPolicy().getCode().equals(policyCode)) {
    			// TODO: transaction here
    			historisePricingPolicyForItem(item);
    			PricingPolicy policy = this.pricingPolicyRepository.getPricingPolicyWithItems(policyCode);
    			item.setPricingPolicy(policy);
    			item = itemRepository.save(item);
    			policy = pricingPolicyRepository.save(policy);
			} else {
				// TODO: try to apply the same PricingPolicy on item, audit 
			}
		} else {
			// TODO: audit here - item not found
		}
    }
    
    /**
     * This method deletes the actual PricingPolicy for the given item (represented by itemCode).
     * That means the item will be assigned with basic PricingPolicy (if it is not already the case).  
     *  
     * @param itemCode
     */
    public void dropPricingPolicyOnItem(String itemCode) {
    	Item item = this.itemRepository.getItemByCode(itemCode);
    	if (item != null) {
    		if (!item.getPricingPolicy().getCode().equals(PricingPolicy.PP_BASIC)) {
    			// TODO: transaction here
    			historisePricingPolicyForItem(item);
    			PricingPolicy basicPricingPolicy = this.pricingPolicyRepository.getPricingPolicyByCode(PricingPolicy.PP_BASIC); 
				item.setPricingPolicy(basicPricingPolicy);
				itemRepository.save(item); 
			} else {
				// TODO: audit here - item already had the basic PricingPolicy
			}
		} else {
			// TODO: audit here - item not found
		}
	}
    
    private void historisePricingPolicyForItem(Item item) {
		PricingHistory pricingHistory = new PricingHistory(item, item.getPricingPolicy()); 
		this.pricingHistoryRepository.save(pricingHistory); 
	}
}
