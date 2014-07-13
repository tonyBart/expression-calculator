/**
 * 
 */
package com.bartley.calculator.expression.strategy.binary.concrete;

import com.bartley.calculator.expression.strategy.binary.BinaryOperatorStrategy;

/**
 * @author Tony
 *
 */
public class PowerOperation extends  BinaryOperatorStrategy<Double,Double,Double> {

	public Double doOperation(final Double num1,final Double intNum) {
		return Math.pow(num1, intNum);
	}

}
