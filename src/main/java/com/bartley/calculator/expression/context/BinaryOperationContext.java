/**
 * 
 */
package com.bartley.calculator.expression.context;

import com.bartley.calculator.expression.strategy.binary.BinaryOperatorStrategy;

/**
 * @author Tony
 *
 */
public class BinaryOperationContext<T,V,U> {
	   private BinaryOperatorStrategy<T,V,U> strategy;

	   public BinaryOperationContext(BinaryOperatorStrategy<T,V,U> strategy){
	      this.strategy = strategy;
	   }

	   public U executeStrategy(T item1, V item2){
	      return strategy.doOperation(item1,item2);
	   }
	}
