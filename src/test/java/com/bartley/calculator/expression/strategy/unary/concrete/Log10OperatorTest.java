/**
 * 
 */
package com.bartley.calculator.expression.strategy.unary.concrete;

import static org.junit.Assert.*;

import java.text.DecimalFormat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Tony
 *
 */
public class Log10OperatorTest {

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
	 * Test method for {@link com.bartley.calculator.expression.strategy.unary.concrete.Log10Operation#doOperation(double)}.
	 */
	@Test
	public void testDoOperation() 
	{
		double result = Double.parseDouble((new DecimalFormat("#0.000000000")).format(Math.log10(57.0)));
		assertTrue("The value returned should be: 1.755874856 but was found to be: " + result,result == 1.755874856);
	}

}
