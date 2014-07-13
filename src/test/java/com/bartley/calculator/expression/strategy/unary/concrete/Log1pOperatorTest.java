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
public class Log1pOperatorTest {

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
	 * Test method for {@link com.bartley.calculator.expression.strategy.unary.concrete.Log1pOperation#doOperation(double)}.
	 */
	@Test
	public void testDoOperation() {
		double result = Double.parseDouble((new DecimalFormat("#0.000000000")).format(Math.log1p(0.491361694)));
		assertTrue("The value returned should be: 0.399689591 but was found to be: " + result,result == 0.399689591);
	}

}
