/**
 * 
 */
package com.bartley.calculator.expression.strategy.unary.concrete;

import com.bartley.calculator.expression.strategy.unary.UnaryOperatorStrategy;

/**
 * @author Tony
 *
 */
public class ACosOperation extends  UnaryOperatorStrategy<Double,Double> {

	@Override
	public Double doOperation(Double num) {
		return Math.acos(num);
	}

}
