/**
 * 
 */
package com.bartley.calculator.expression.worker;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;


/**
 * @author Tony
 * @param <T>
 *
 */
public interface OperationWorker<T> extends Callable<T>{
	
	public String getStrategy();
	
	public String getBaseStrategy();

	public void setCountDownLatch(CountDownLatch latchDone);

}
