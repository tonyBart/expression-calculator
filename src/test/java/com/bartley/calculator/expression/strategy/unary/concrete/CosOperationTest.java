package com.bartley.calculator.expression.strategy.unary.concrete;

import static org.junit.Assert.*;

import java.text.DecimalFormat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CosOperationTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDoOperation() {
		double result = Double.parseDouble((new DecimalFormat("#0.000000000")).format(Math.cos(30.2)));
		assertTrue("The value returned should be: 0.347468272 but was found to be: " + result,result == 0.347468272);
	}

}
