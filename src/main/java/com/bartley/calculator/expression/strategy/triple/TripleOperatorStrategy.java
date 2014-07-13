/**
 * 
 */
package com.bartley.calculator.expression.strategy.triple;

import com.bartley.calculator.expression.strategy.Strategy;



/**
 * @author Tony
 *
 */
public abstract class TripleOperatorStrategy<T,V,U,W> implements Strategy {

private String baseStrategy = "TripleStrategy";
	
	abstract public  W doOperation(T item1,V item2, U item3);
	
	/* (non-Javadoc)
	 * @see com.bartley.calculator.expression.strategy.Strategy#getStrategy()
	 */
	@Override
	public String getBaseStrategy() {
		return this.baseStrategy;
	}
	
}
