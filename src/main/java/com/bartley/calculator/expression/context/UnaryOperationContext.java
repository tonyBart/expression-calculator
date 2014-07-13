/**
 * 
 */
package com.bartley.calculator.expression.context;

import com.bartley.calculator.expression.strategy.unary.UnaryOperatorStrategy;

/**
 * @author Tony
 *
 */
public class UnaryOperationContext <T,U>{
	private UnaryOperatorStrategy<T,U> strategy;

	   public UnaryOperationContext(UnaryOperatorStrategy<T,U> strategy){
	      this.strategy = strategy;
	   }

	   public U executeStrategy(T item1){
	      return strategy.doOperation(item1);
	   }
}
