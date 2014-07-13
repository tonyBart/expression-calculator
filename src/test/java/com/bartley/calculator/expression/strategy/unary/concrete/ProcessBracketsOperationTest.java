/**
 * 
 */
package com.bartley.calculator.expression.strategy.unary.concrete;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bartley.calculator.expression.exception.CalculatorException;
import com.bartley.calculator.expression.service.util.CalculatorConstants;
import com.bartley.calculator.expression.strategy.unary.concrete.ProcessBracketsOperation;

/**
 * @author Tony
 *
 */
public class ProcessBracketsOperationTest {

	private ProcessBracketsOperation processBrackets = new ProcessBracketsOperation();
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
	 *  * Test method for {@link com.bartley.calculator.expression.strategy.unary.concrete.ProcessBracketsOperation#doOperation(java.lang.String)}.
	 */
	@Test
	public void testDoOperation2Brackets(){
		String expectedResult = "8.0";
		String resultString = "" + processBrackets.doOperation("(4.5 + 3.5)"); 
		assertTrue("", expectedResult.equals(resultString));
	}
	
	@Test
	public void testDoOperation3Brackets(){
		String expectedResult = "119.0";
		String resultString = "" + processBrackets.doOperation("13.5*(4.5 + 3.5)+ (33.0/3.0)");
		assertTrue("", expectedResult.equals(resultString));
	}
	
	
	@Test
	public void testMissingStartBrackets(){
		boolean exceptionThrown = false;
		String message = "Index: 1, Size: 1";
		try{
			processBrackets.doOperation("13.5*4.5 + 3.5)+ (33.0/3.0)");
		
		}catch(CalculatorException calEx){
			assertTrue("Error message should have read: " + CalculatorConstants.MISSING_BRACKET+ message + "/n but read: " + calEx.getMessage(), (CalculatorConstants.MISSING_BRACKET).equals(calEx.getMessage()));
			exceptionThrown = true;
		}
		assertTrue("Exception should have been thrown missing start bracket",exceptionThrown);
	}
	
	@Test
	public void testMissingEndBrackets(){
		boolean exceptionThrown = false;
		String message = "";
		try{
			processBrackets.doOperation("(13.5*(4.5 + 3.5)+ (33.0/3.0)");
		
		}catch(CalculatorException calEx){
			assertTrue("Error message should have read: " + CalculatorConstants.MISSING_BRACKET+ message + "/n but read: " + calEx.getMessage(), (CalculatorConstants.MISSING_BRACKET + message).equals(calEx.getMessage()));
			exceptionThrown = true;
		}
		assertTrue("Exception should have been thrown missing end bracket",exceptionThrown);
		exceptionThrown = false;
		message = "";
		try{
			processBrackets.doOperation("13.5*(4.5 + 3.5+ (33.0/3.0");
		
		}catch(CalculatorException calEx){
			assertTrue("Error message should have read: " + CalculatorConstants.MISSING_BRACKET+ message + "/n but read: " + calEx.getMessage(), (CalculatorConstants.MISSING_BRACKET + message).equals(calEx.getMessage()));
			exceptionThrown = true;
		}
		assertTrue("Exception should have been thrown missing end bracket",exceptionThrown);
	}

}
