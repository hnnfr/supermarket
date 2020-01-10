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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

@RunWith(Parameterized.class)
@SpringBootTest(classes = SupermarketApplication.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:create_tables.sql")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:insert_tables.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:cleanup.sql")
public class Group3For1EuroPriceCalculatorTest {

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
			{new BigDecimal("1.0"), new BigDecimal("0.5"), new BigDecimal("3")}, 	// volume=3, price 1 euro
			{new BigDecimal("1.0"), new BigDecimal("0.5"), new BigDecimal("2")},	// volume=2, price 1 euro 
			{new BigDecimal("1.5"), new BigDecimal("0.5"), new BigDecimal("4")},	// volume=4, price 1.5 euros
			{new BigDecimal("2.0"), new BigDecimal("0.5"), new BigDecimal("6")},	// volume=6, price 2 euros
			{new BigDecimal("3.0"), new BigDecimal("0.5"), new BigDecimal("8")},	// volume=8, price 3 euros
			{new BigDecimal("3.0"), new BigDecimal("0.5"), new BigDecimal("9")}		// volume=9, price 3 euros
		}); 
	}

	public Group3For1EuroPriceCalculatorTest(BigDecimal expected, BigDecimal basicPrice, BigDecimal volume) {
		super();
		this.expected = expected;
		this.basicPrice = basicPrice;
		this.volume = volume.intValue();
	}

	@Test
	public void testCalculatePrice() {
		IPriceCalculator calculator = PriceCalculatorFactory.getPriceCalculator(PricingPolicy.PP_GROUP_3_FOR_1_EURO, pricingPolicyRepository);
		BigDecimal price = calculator.calculatePrice(basicPrice, volume);
		double epsilon = 0.00001;
		assertEquals(expected.doubleValue(), price.doubleValue(), epsilon);
	}
}
