package com.hnn.supermarket.dao;

import com.hnn.supermarket.SupermarketApplication;
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
public class CategoryTest {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private PricingPolicyRepository pricingPolicyRepository;

    @Test
    public void testAddItem() {
        String categoryCode = "CAT_TEST";

        PricingPolicy pricingPolicy = pricingPolicyRepository.save(new PricingPolicy("PP_TEST", "test", "test", LocalDate.now()));
        Item item = new Item("ITEM_TEST", "test", new BigDecimal("1"));
        item.setPricingPolicy(pricingPolicy);
        item = itemRepository.save(item);
        Category category = categoryRepository.save(new Category(categoryCode, "test"));
        category.addItem(item);
        item = itemRepository.save(item);
        category = categoryRepository.save(category);

        Category foundCategory = categoryRepository.getCategoryByCode(categoryCode);
        Set<Item> items = foundCategory.getItems();
        assertNotNull(items);
        assertEquals("Should have 1 item", 1, items.size());
        assertTrue("Should contain the test item", items.contains(item));
    }
}
