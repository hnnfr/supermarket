package com.hnn.supermarket.pricing;

import com.hnn.supermarket.SupermarketApplication;
import com.hnn.supermarket.dao.PricingPolicyRepository;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.hnn.supermarket.dao.PricingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
@SpringBootTest(classes = SupermarketApplication.class)
public class PriceCalculatorFactoryTest {

	@ClassRule
	public static final SpringClassRule scr = new SpringClassRule();

	@Rule
	public final SpringMethodRule smr = new SpringMethodRule();

	@Autowired
	private PricingPolicyRepository pricingPolicyRepository;

	@SuppressWarnings("rawtypes")
	private Class expected;
	private String pricingCode; 
	
	@Parameters
	public static Collection<Object[]> getTestParameters() {
		return Arrays.asList(new Object[][] {
			{BasicPriceCalculator.class, PricingPolicy.PP_BASIC},
			{Dec2019Buy2Get1FreePriceCalculator.class, PricingPolicy.PP_DEC_2019_BUY_2_GET_1_FREE}, 
			{Group3For1EuroPriceCalculator.class, PricingPolicy.PP_GROUP_3_FOR_1_EURO}, 
			{Jan2020Buy2Get1FreePriceCalculator.class, PricingPolicy.PP_JAN_2020_BUY_2_GET_1_FREE}, 
			{Noel2019ReductionPriceCalculator.class, PricingPolicy.PP_2019_NOEL_REDUCTION}, 
			{Nov2019Buy2Get1FreePriceCalculator.class, PricingPolicy.PP_NOV_2019_BUY_2_GET_1_FREE} 
		});
	}
	
	public PriceCalculatorFactoryTest(@SuppressWarnings("rawtypes") Class expected, String pricingCode) {
		this.expected = expected; 
		this.pricingCode = pricingCode;
	}

	@Test
	public void testGetPriceCalculator() {
		IPriceCalculator calculator = PriceCalculatorFactory.getPriceCalculator(this.pricingCode, pricingPolicyRepository);
		assertEquals(this.expected, calculator.getClass());
	}
}
