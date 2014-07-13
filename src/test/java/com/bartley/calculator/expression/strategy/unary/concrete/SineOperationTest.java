/**
 * 
 */
package com.bartley.calculator.expression.strategy.unary.concrete;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Tony
 *
 */
public class SineOperationTest {

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
	 * Test method for {@link com.bartley.calculator.expression.strategy.unary.concrete.SineOperation#doOperation(double)}.
	 */
	@Test
	public void testDoOperation() {
		double result = Math.sin(0);
		assertTrue("The value returned should be: 0.0 but was found to be: " + result,result == 0.0);
	}

}
