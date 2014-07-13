/**
 * 
 */
package com.bartley.calculator.expression.strategy.binary;

import com.bartley.calculator.expression.strategy.Strategy;

/**
 * @author Tony
 *
 */
public abstract class BinaryOperatorStrategy<T,V,U> implements Strategy{
	private final String baseStrategy = "BinaryStrategy";
	
	abstract public U doOperation(T item1, V item2);
	
	@Override
	public String getBaseStrategy() {
		return baseStrategy;
	}
}
