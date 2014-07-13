/**
 * 
 */
package com.bartley.calculator.expression.service.util;

import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author Tony
 *
 */
public class ExpressionSecuredUpdater extends Updater {
	/**
	 * 
	 */
	private volatile StringBuffer expression;
	private volatile ConcurrentSkipListMap<String, String> swapMap;
	
	public ExpressionSecuredUpdater(String expression, ConcurrentSkipListMap<String, String> swapMap){
		this.expression = new StringBuffer(expression);
		this.swapMap = swapMap;
	}

	/**
	 * @return the expression
	 */
	@Override
	public String getExpression() {
		return expression.toString();
	}

	/*
	 * Updates every occurrence of the operationName with the string value.
	 * opToValsMap
	 * Creates an Happens after 
	 * 
	 * @param expression the expression to set
	 * @return returns true if any operators replaced, false otherwise
	 */
	@Override
	public String updateExpression(String operationName, String value) {
		int startIndex = this.expression.indexOf(operationName);
		String returnExpression = null;
		if( startIndex > -1){
			synchronized(expression){
				// Check index to see if it still exists and if not find the first endIndex 
				startIndex = this.expression.indexOf(operationName);
				if( startIndex > -1){
					System.out.println("About to swap: " + operationName + " with: " + value);
					int endIndex = startIndex + operationName.length();
					// Take it out of the swap map
					swapMap.remove(operationName);
					// Replace every one
					while(startIndex > -1){
						expression = this.expression.replace(startIndex,endIndex ,value);
						// Check if any more exists
						startIndex = this.expression.indexOf(operationName);
						endIndex = startIndex + operationName.length();
						System.out.println("Updated expression - new expression: " + expression.toString());
					}
					returnExpression = expression.toString();
					System.out.println("Swaped: " + operationName + " with: " + value);
				}
			}
		}
		return returnExpression;
	}
}
