/**
 * 
 */
package com.bartley.calculator.expression.service.util;

/**
 * @author Tony
 *
 */
public abstract class Updater {
	/**
	 * 
	 */
	private StringBuffer expression;
	
	public  String getExpression(){
		return this.expression.toString();
	}
	
	public abstract String updateExpression(String itemToBeReplaced, String value);
}
