/**
 * 
 */
package com.bartley.calculator.expression.strategy.unary.concrete;

import com.bartley.calculator.expression.strategy.unary.UnaryOperatorStrategy;

/**
 * @author Tony
 *
 */
public class CosOperation extends  UnaryOperatorStrategy<Double,Double> {

	@Override
	public Double doOperation(final Double num) {
		return Math.cos(num);
	}

}
