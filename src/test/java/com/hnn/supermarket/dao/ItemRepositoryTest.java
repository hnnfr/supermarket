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

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SupermarketApplication.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:create_tables.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:cleanup.sql")
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private PricingPolicyRepository pricingPolicyRepository;

    @Test
    public void testGetItemByCode() {
        PricingPolicy pricingPolicy = pricingPolicyRepository.save(new PricingPolicy("PP_TEST", "test", "test", LocalDate.now()));
        Item item = new Item("ITEM_TEST", "test", BigDecimal.valueOf(1));
        item.setPricingPolicy(pricingPolicy);
        item = itemRepository.save(item);
        Item foundItem = itemRepository.getItemByCode(item.getCode());

        assertNotNull(foundItem);
        assertEquals(item.getCode(), foundItem.getCode());
    }

    @Test
    public void testGetItemByCode_CodeNotExist() {
        Item foundItem = itemRepository.getItemByCode("codeNotExist");

        assertNull("Should return null when code not exist", foundItem);
    }

    @Test
    public void testGetItemByCode_CodeNull() {
        Item foundItem = itemRepository.getItemByCode(null);

        assertNull("Should return null when code null", foundItem);
    }}
