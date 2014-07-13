/**
 * 
 */
package com.bartley.calculator.expression.strategy.unary.concrete;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


/**
 * @author Tony
 *
 */
public class ACosOperationTest {

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
	 * Test method for {@link com.bartley.calculator.expression.strategy.unary.concrete.ACosOperation#doOperation(double)}.
	 */
	@Ignore
	@Test
	public void testDoOperation() {
		double result = Math.acos(0.347468272);
		assertTrue("The value returned should be: 30.2 but was found to be: " + result,result == 30.2);
	}

}
