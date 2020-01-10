package com.hnn.supermarket.pricing;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import com.hnn.supermarket.SupermarketApplication;
import com.hnn.supermarket.dao.PricingPolicyRepository;
import org.junit.Test;

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
public class Nov2019Buy2Get1FreePriceCalculatorTest {

	@Autowired
	private PricingPolicyRepository pricingPolicyRepository;

	@Test
	public void testCalculatePrice() {
		// Date of validity is over for this PricingPolicy
		IPriceCalculator calculator = PriceCalculatorFactory.getPriceCalculator(PricingPolicy.PP_NOV_2019_BUY_2_GET_1_FREE, pricingPolicyRepository);
		BigDecimal basicPrice = new BigDecimal("3.5");
		int volume = 3; 
		BigDecimal expected = new BigDecimal("10.5"); 
		BigDecimal price = calculator.calculatePrice(basicPrice, volume);
		double epsilon = 0.00001;
		assertEquals("expected price should equal to basicPrice multiplies by volume", expected.doubleValue(), price.doubleValue(), epsilon);
	}
}
