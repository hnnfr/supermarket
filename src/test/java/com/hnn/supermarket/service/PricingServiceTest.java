package com.hnn.supermarket.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

import com.hnn.supermarket.SupermarketApplication;
import com.hnn.supermarket.dao.ItemRepository;
import com.hnn.supermarket.dao.PricingPolicyRepository;
import org.junit.Test;

import com.hnn.supermarket.dao.Item;
import com.hnn.supermarket.dao.PricingPolicy;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SupermarketApplication.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:create_tables.sql")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:insert_tables.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:cleanup.sql")
public class PricingServiceTest {
	
	@Autowired
	private PricingService service;

	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private PricingPolicyRepository pricingPolicyRepository;

	@Test
	public void testGetItemsForPricingPolicy() {
		PricingPolicy policy = pricingPolicyRepository.getPricingPolicyByCode(PricingPolicy.PP_GROUP_3_FOR_1_EURO);
		List<Item> items = service.getItemsForPricingPolicy(policy); 
		assertNotNull("Should not return null", items);
		// Print all them out
		items.stream().forEach(System.out::println);
	}

	@Test
	public void testGetItemsForPricingPolicy_NullParameter() {
		PricingPolicy policy = null;
		List<Item> items = service.getItemsForPricingPolicy(policy); 
		assertNotNull("Should not return null", items);
		assertEquals("Should return empty list", 0, items.size());
	}
	
	@Test
	public void testGetPriceForItem() {
		Item item = itemRepository.getItemByCode("ORANGE");
		int volume = 3; 
		BigDecimal price = service.getPriceForItem(item, volume);
		assertNotNull(price);
		System.out.println("PricingPolicy for item: " + item.getPricingPolicy());
		System.out.println("price = " + price.doubleValue());
	}
	
	@Test
	public void testApplyPricingPolicyOnItem() {
		String pricingPolicyCode = PricingPolicy.PP_2019_NOEL_REDUCTION; 
		String itemCode = "ORANGE"; 
		service.applyPricingPolicyOnItem(pricingPolicyCode, itemCode);
		// Now check that the item has the new PricingPolicy
		Item item = itemRepository.getItemByCode(itemCode);
		assertNotNull(item);
		assertEquals("Item should have new PricingPolicy", pricingPolicyCode, item.getPricingPolicy().getCode());
	}
}
