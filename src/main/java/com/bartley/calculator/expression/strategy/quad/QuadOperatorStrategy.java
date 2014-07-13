/**
 * 
 */
package com.bartley.calculator.expression.strategy.quad;

import com.bartley.calculator.expression.strategy.Strategy;

/**
 * @author Tony
 *
 */
public abstract class QuadOperatorStrategy<T,U,V,W> implements Strategy {

	private String baseStrategy = "QuadStrategy";
	
	abstract public  void doOperation(T item1,T item2,T item3,T item4);
	
	/* (non-Javadoc)
	 * @see com.bartley.calculator.expression.strategy.Strategy#getStrategy()
	 */
	@Override
	public String getBaseStrategy() {
		return this.baseStrategy;
	}
	

}
