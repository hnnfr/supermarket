package com.hnn.supermarket.dao;

import com.hnn.supermarket.SupermarketApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SupermarketApplication.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:create_tables.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:cleanup.sql")
public class PricingHistoryRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private PricingPolicyRepository pricingPolicyRepository;
    @Autowired
    private PricingHistoryRepository pricingHistoryRepository;

    private PricingPolicy pricingPolicy;
    private Item item;

    @Before
    public void initialize() {
        pricingPolicy = pricingPolicyRepository.save(new PricingPolicy("PP_TEST", "pp test", "", LocalDate.now()));
        item = new Item("ITEM_TEST", "test", new BigDecimal("1"));
        item.setPricingPolicy(pricingPolicy);
        item = itemRepository.save(item);
    }

    @Test
    public void testSaveAndGet() {
        PricingHistory pricingHistory = pricingHistoryRepository.save(new PricingHistory(item, pricingPolicy));

        PricingHistory foundPricingHistory = pricingHistoryRepository.findById(pricingHistory.getId()).orElse(null);
        assertNotNull(foundPricingHistory);
        assertEquals("Should be the pricingHistory of the item", item.getCode(), foundPricingHistory.getItem().getCode());
    }

    @Test
    public void testAddPricingHistoryForItem() {
        PricingHistory pricingHistory1 = new PricingHistory();
        pricingHistory1.setItem(item);
        pricingHistory1.setPricingPolicy(pricingPolicy);
        pricingHistory1.setCreationDate(LocalDate.of(2019, 11, 1));
        pricingHistoryRepository.save(pricingHistory1);

        PricingPolicy pricingPolicy2 = pricingPolicyRepository.save(new PricingPolicy("PP_TEST_2", "pp test", "", LocalDate.now()));
        PricingHistory pricingHistory2 = new PricingHistory();
        pricingHistory2.setItem(item);
        pricingHistory2.setPricingPolicy(pricingPolicy2);
        pricingHistory2.setCreationDate(LocalDate.of(2020, 1, 1));
        pricingHistoryRepository.save(pricingHistory2);

        Item foundItem = itemRepository.getItemWithPricingHistories(item.getCode());
        Set<PricingHistory> pricingHistories = foundItem.getPricingHistories();
        assertNotNull(pricingHistories);
        assertEquals("Item should have 2 pricingHistories", 2, pricingHistories.size());
        assertTrue(pricingHistories.contains(pricingHistory1));
        assertTrue(pricingHistories.contains(pricingHistory2));
    }
}
