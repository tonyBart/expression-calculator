/**
 * 
 */
package com.bartley.calculator.expression.worker;

import java.util.concurrent.CountDownLatch;

import com.bartley.calculator.expression.strategy.binary.BinaryOperatorStrategy;

/**
 * @author Tony
 *
 */
public class BinaryOperationWorker<T,V,U> implements OperationWorker<U>{

	private T firstItem;
	private V secondItem;
	private BinaryOperatorStrategy<T,V,U> strategy;
	private CountDownLatch countDownLatch;
	private final String baseStrategy  = "BinaryStrategy";
	
	
	public BinaryOperationWorker(T firstItem, V secondItem,BinaryOperatorStrategy<T,V,U> strategy){
		this.firstItem = firstItem;
		this.secondItem = secondItem;
		this.strategy = strategy;
		
	}
	
	
	@Override
	public U call() {
		System.out.println(strategy.getBaseStrategy() + ": About to process: " + firstItem + " and " + secondItem );
		U returnValue = strategy.doOperation(firstItem, secondItem);
		System.out.println(strategy.getBaseStrategy() + ": Processed: " + firstItem + " and " + secondItem );
		if(countDownLatch != null){
			System.out.println(strategy.getBaseStrategy() + "About to decrement countDownLatch");
			countDownLatch.countDown();
			System.out.println(strategy.getBaseStrategy() + "Decremented countDownLatch");
		}
		return returnValue;

	}
	
	
	@Override
	public String getStrategy(){
		return this.strategy.getBaseStrategy();
	}
	
	public V getSecondItem(){
		return this.secondItem;
	}
	
	/**
	 * @return the firstItem
	 */
	public T getFirstItem() {
		return firstItem;
	}


	/**
	 * @param countDownLatch the countDownLatch to set
	 */
	public void setCountDownLatch(CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
	}


	@Override
	public String getBaseStrategy() {
		return this.baseStrategy;
	}


}
