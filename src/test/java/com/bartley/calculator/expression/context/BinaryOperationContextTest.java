package com.bartley.calculator.expression.context;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bartley.calculator.expression.context.BinaryOperationContext;
import com.bartley.calculator.expression.strategy.binary.BinaryOperatorStrategy;
import com.bartley.calculator.expression.strategy.binary.concrete.PowerOperation;

public class BinaryOperationContextTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testExecuteStrategy() {
		BinaryOperatorStrategy<Double,Double,Double> strategy = new PowerOperation();
		BinaryOperationContext<Double,Double,Double> binaryOperationContext = new BinaryOperationContext<Double,Double,Double>(strategy);
		double result = binaryOperationContext.executeStrategy(3.0, 3.0);
		assertTrue("The returned Value should be 27.0", result == 27.0);
	}

}
