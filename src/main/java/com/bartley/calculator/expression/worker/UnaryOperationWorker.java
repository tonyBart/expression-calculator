/**
 * 
 */
package com.bartley.calculator.expression.worker;




import java.util.concurrent.CountDownLatch;

import com.bartley.calculator.expression.strategy.unary.UnaryOperatorStrategy;

/**
 * @author Tony
 *
 */
public class UnaryOperationWorker<T,V> implements OperationWorker<V>{
	/**
	 * 
	 */
	private final UnaryOperatorStrategy<T,V> strategy;
	private final T firstItem;
	private CountDownLatch countDownLatch;
	private final String baseStrategy = "UnaryStrategy";
	
	
	public UnaryOperationWorker(T firstItem,UnaryOperatorStrategy<T,V> strategy){
		this.firstItem = firstItem;
		this.strategy = strategy;
	}
	

	@Override
	public V call() {
	
		System.out.println("Thread: " + this.hashCode() + " " + strategy.getBaseStrategy() + ": About to process: " + firstItem);
		V returnValue =  strategy.doOperation(firstItem);
		System.out.println("Thread: " + this.hashCode() + " " + strategy.getBaseStrategy() + ": Processed: " + firstItem );
		if(countDownLatch != null){
			System.out.println(strategy.getBaseStrategy() + "About to decrement countDownLatch");
			countDownLatch.countDown();
			System.out.println("Thread: " + this.hashCode() + " " + strategy.getBaseStrategy() + "Decremented countDownLatch");
		}
		return returnValue;
	}

	
	@Override
	public String getStrategy() {
		return strategy.getBaseStrategy();
	}
	
	public T getFirstItem(){
		return firstItem;
	}



	/**
	 * @param countDownLatch the countDownLatch to set
	 */
	@Override
	public void setCountDownLatch(CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
	}


	@Override
	public String getBaseStrategy() {
		return this.baseStrategy;
	}



}
