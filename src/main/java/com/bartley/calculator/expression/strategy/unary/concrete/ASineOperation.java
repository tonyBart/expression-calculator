/**
 * 
 */
package com.bartley.calculator.expression.strategy.unary.concrete;

import com.bartley.calculator.expression.strategy.unary.UnaryOperatorStrategy;

/**
 * @author Tony
 *
 */
public class ASineOperation extends  UnaryOperatorStrategy<Double,Double> {
	public Double doOperation(final Double num) {
		return Math.sinh(num);
	}
}
