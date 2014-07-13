/**
 * 
 */
package com.bartley.calculator.expression;

import static com.bartley.calculator.expression.service.util.CalculatorUtils.processBrackets;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bartley.calculator.expression.Calculator;
import com.bartley.calculator.expression.CalculatorImpl;
import com.bartley.calculator.expression.exception.CalculatorException;
import com.bartley.calculator.expression.service.util.CalculatorConstants;

/**
 * @author Tony
 *
 */
public class CalculatorImplTest {

	private Calculator calculator = new CalculatorImpl();
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.bartley.calculator.expression.CalculatorImpl#calculate(java.lang.String)}.
	 */
	@Test
	public void testCalculate() {
		double result = calculator.calculate("sin(90.0) + 19.9 + cos(0.0)");
		assertTrue("The returned value should be 21.8", result == 21.793996663600556);
	}
	
	@Test
	public void testCalculate2() {
		double result = calculator.calculate("32.5 * cos(0.0) + pow(2.0,2.0) - cos(90.0) + 64 + pow(3.0,3.0) + 24*3/12");
		assertTrue("The returned value should be 133.9, but is: " + result, result == 133.94807361612916);
	}
	
	@Test
	public void testCalculate3(){
		double result = calculator.calculate("(4.5 + 3.5)");
		assertTrue("The returned value should be 8.0", result == 8.0);
	}
		
	@Test 
	public void testMissingBrackets1(){
		boolean exceptionThrown = false;
		try{
			calculator.calculate("13.5*(4.5 + 3.5)+ (33.0/3.0))");
		
		}catch(CalculatorException calEx){
			assertTrue("", CalculatorConstants.MISSING_BRACKET.equals(calEx.getMessage()));
			exceptionThrown = true;
		}
		assertTrue("Exception should have been thrown missing start bracket",exceptionThrown);
	}
	
	@Test 
	public void testMissingBrackets2(){
		boolean exceptionThrown = false;
		try{
			calculator.calculate("(13.5*(4.5 + 3.5)+ (33.0/3.0)");
		
		}catch(CalculatorException calEx){
			assertTrue("", CalculatorConstants.MISSING_BRACKET.equals(calEx.getMessage()));
			exceptionThrown = true;
		}
		assertTrue("Exception should have been thrown missing end bracket",exceptionThrown);
		exceptionThrown = false;
		try{
			calculator.calculate("(13.5*(4.5 + 3.5+ (33.0/3.0))");
		
		}catch(CalculatorException calEx){
			assertTrue("", CalculatorConstants.MISSING_BRACKET.equals(calEx.getMessage()));
			exceptionThrown = true;
		}
		assertTrue("Exception should have been thrown missing end bracket",exceptionThrown);
	}
	
}
