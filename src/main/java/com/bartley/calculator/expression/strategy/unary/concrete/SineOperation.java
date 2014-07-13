package com.bartley.calculator.expression.strategy.unary.concrete;

import com.bartley.calculator.expression.strategy.unary.UnaryOperatorStrategy;

public class SineOperation extends  UnaryOperatorStrategy<Double,Double> {

	public Double doOperation(final Double num) {
		return Math.sin(num);
	}

}
