package com.hnn.supermarket.dao;

import com.hnn.supermarket.SupermarketApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SupermarketApplication.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:create_tables.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:cleanup.sql")
public class PricingPolicyRepositoryTest {

    @Autowired
    private PricingPolicyRepository pricingPolicyRepository;

    @Test
    public void testGetPricingPolicyByCode() {
        PricingPolicy pricingPolicy = pricingPolicyRepository.save(new PricingPolicy("PP_TEST", "test", "test", LocalDate.now()));
        PricingPolicy foundPricingPolicy = pricingPolicyRepository.getPricingPolicyByCode(pricingPolicy.getCode());

        assertNotNull(foundPricingPolicy);
        assertEquals(pricingPolicy.getCode(), foundPricingPolicy.getCode());
    }

    @Test
    public void testGetPricingPolicyByCode_CodeNotExist() {
        PricingPolicy foundPricingPolicy = pricingPolicyRepository.getPricingPolicyByCode("codeNotExist");

        assertNull("Should return null when codeNotExist", foundPricingPolicy);
    }

    @Test
    public void testGetPricingPolicyByCode_CodeNull() {
        PricingPolicy foundPricingPolicy = pricingPolicyRepository.getPricingPolicyByCode(null);

        assertNull("Should return null when code null", foundPricingPolicy);
    }
}
