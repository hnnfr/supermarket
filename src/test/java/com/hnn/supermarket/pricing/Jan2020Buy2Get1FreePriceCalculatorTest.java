package com.hnn.supermarket.pricing;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

@RunWith(Parameterized.class)
@SpringBootTest(classes = SupermarketApplication.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:create_tables.sql")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:insert_tables.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:cleanup.sql")
public class Jan2020Buy2Get1FreePriceCalculatorTest {

	@ClassRule
	public static final SpringClassRule scr = new SpringClassRule();

	@Rule
	public final SpringMethodRule smr = new SpringMethodRule();

	@Autowired
	private PricingPolicyRepository pricingPolicyRepository;

	private BigDecimal expected; 
	private BigDecimal basicPrice; 
	private int volume; 
	
	@Parameters
	public static Collection<BigDecimal[]> getTestParameters() {
		return Arrays.asList(new BigDecimal[][] {
			{new BigDecimal("3.0"), new BigDecimal("1.5"), new BigDecimal("3")}, 	// volume=3, get 1 item free
			{new BigDecimal("3.0"), new BigDecimal("1.5"), new BigDecimal("2")},	// volume=2, no item free 
			{new BigDecimal("4.5"), new BigDecimal("1.5"), new BigDecimal("4")},	// volume=4, get 1 item free
			{new BigDecimal("6.0"), new BigDecimal("1.5"), new BigDecimal("6")},	// volume=6, get 2 item free
			{new BigDecimal("9.0"), new BigDecimal("1.5"), new BigDecimal("8")},	// volume=8, get 2 item free
			{new BigDecimal("9.0"), new BigDecimal("1.5"), new BigDecimal("9")}	// volume=9, get 3 item free
		}); 
	}
	
	public Jan2020Buy2Get1FreePriceCalculatorTest(BigDecimal expected, BigDecimal basicPrice, BigDecimal volume) {
		this.expected = expected;
		this.basicPrice = basicPrice;
		this.volume = volume.intValue();
	}

	@Test
	public void testCalculatePrice() {
		IPriceCalculator calculator = PriceCalculatorFactory.getPriceCalculator(PricingPolicy.PP_JAN_2020_BUY_2_GET_1_FREE, pricingPolicyRepository);
		BigDecimal price = calculator.calculatePrice(basicPrice, volume);
		double epsilon = 0.00001;
		assertEquals(expected.doubleValue(), price.doubleValue(), epsilon);
	}
}
