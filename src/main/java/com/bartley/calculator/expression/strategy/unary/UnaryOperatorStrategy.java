package com.bartley.calculator.expression.strategy.unary;

import com.bartley.calculator.expression.strategy.Strategy;

public abstract class UnaryOperatorStrategy<T,U> implements Strategy {
	
	private final String baseStrategy = "UnaryStrategy";
	
	public abstract U doOperation(T num);

	/**
	 * @return the strategy
	 */
	@Override
	public String getBaseStrategy() {
		return baseStrategy;
	}
}
