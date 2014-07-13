package com.bartley.calculator.expression.strategy.unary.concrete;

import com.bartley.calculator.expression.strategy.unary.UnaryOperatorStrategy;

public class ExponentialM1Operation extends  UnaryOperatorStrategy<Double,Double> {

	@Override
	public Double doOperation(final Double num) {
		return Math.expm1(num);
	}

}
