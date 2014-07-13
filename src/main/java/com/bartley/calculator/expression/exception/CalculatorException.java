/**
 * 
 */
package com.bartley.calculator.expression.exception;

/**
 * @author Tony
 *
 */
public class CalculatorException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3259834565612526119L;

	/**
	 * 
	 */
	public CalculatorException() {
		
	}

	/**
	 * @param message
	 */
	public CalculatorException(String message) {
		super(message);
		
	}

	/**
	 * @param cause
	 */
	public CalculatorException(Throwable cause) {
		super(cause);
		
	}

	/**
	 * @param message
	 * @param cause
	 */
	public CalculatorException(String message, Throwable cause) {
		super(message, cause);
		
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public CalculatorException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super();
		
	}

}
