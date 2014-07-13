package com.bartley.calculator.expression.context;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bartley.calculator.expression.context.UnaryOperationContext;
import com.bartley.calculator.expression.strategy.unary.UnaryOperatorStrategy;
import com.bartley.calculator.expression.strategy.unary.concrete.SineOperation;

public class UnaryOperationContextTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testExecuteStrategy() {
		UnaryOperatorStrategy<Double,Double> strategy = new SineOperation();
		UnaryOperationContext<Double,Double> unaryOperationContext = new UnaryOperationContext<Double,Double>(strategy);
		double result = unaryOperationContext.executeStrategy(0.0);
		assertTrue("The sin of 90 degrees should be 0.0", result == 0.0);
		
	}

}
