package com.hnn.supermarket.pricing;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import com.hnn.supermarket.SupermarketApplication;
import com.hnn.supermarket.dao.PricingPolicyRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hnn.supermarket.dao.PricingPolicy;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SupermarketApplication.class)
public class BasicPriceCalculatorTest {

	@Autowired
	private PricingPolicyRepository pricingPolicyRepository;

	@Test
	public void testCalculatePrice() {
		IPriceCalculator calculator = PriceCalculatorFactory.getPriceCalculator(PricingPolicy.PP_BASIC, pricingPolicyRepository);
		BigDecimal basicPrice = new BigDecimal("3.5");
		int volume = 3; 
		BigDecimal expected = new BigDecimal("10.5"); 
		BigDecimal price = calculator.calculatePrice(basicPrice, volume);
		double epsilon = 0.00001;
		assertEquals("expected price should equal to basicPrice multiplies by volume", expected.doubleValue(), price.doubleValue(), epsilon);
	}
}
