/**
 * 
 */
package com.bartley.calculator.expression.strategy.unary.concrete;

import com.bartley.calculator.expression.strategy.unary.UnaryOperatorStrategy;

/**
 * @author Tony
 *
 */
public class LogOperation extends  UnaryOperatorStrategy<Double,Double> {

	/* (non-Javadoc)
	 * @see com.bartley.calculator.expression.strategy.UnaryOperatorStrategy#doOperation(double)
	 */
	public Double doOperation(final Double num) {
		return StrictMath.log(num);
	}

}
