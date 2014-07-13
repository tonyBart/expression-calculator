/**
 * 
 */
package com.bartley.calculator.expression.worker;

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;

import static com.bartley.calculator.expression.service.util.CalculatorConstants.*;

import com.bartley.calculator.expression.service.util.Updater;
import com.bartley.calculator.expression.strategy.triple.TripleOperatorStrategy;


/**
 * @author Tony
 *
 */
public class MapValuesToPositionOperationWorker<W>  implements OperationWorker<W> {

	private ConcurrentSkipListMap<String, String> swapMap;
	private TripleOperatorStrategy<ConcurrentSkipListMap<String,String>,Updater,DIRECTION,W> strategy;
	private CountDownLatch countDownLatch;
	private Updater expressionSecuredUpdater;
	private final String baseStrategy = "QuadStrategy";
	private DIRECTION listReplacementtDirection = DIRECTION.ASCENDING;
	
	
	public MapValuesToPositionOperationWorker(TripleOperatorStrategy<ConcurrentSkipListMap<String,String>,Updater,DIRECTION,W> strategy,
			ConcurrentSkipListMap<String, String> swapMap, Updater expressionSecuredUpdater){
		this.swapMap = swapMap;
		this.strategy = strategy;
		this.expressionSecuredUpdater = expressionSecuredUpdater;
	}
	
	
	@Override
	public W call() {
		W returnValue = null;
		strategy.doOperation(swapMap,expressionSecuredUpdater, listReplacementtDirection);
		if(countDownLatch != null){
			System.out.println("MapValuesToPositionOperationWorkerMapValuesToPositionOperationWorker thread: " + this.hashCode());
			countDownLatch.countDown();
		}
		return returnValue;
	}
	
	
	/**
	 * @param countDownLatch the countDownLatch to set
	 */
	@Override
	public void setCountDownLatch(CountDownLatch countDownLatch) {
		
		this.countDownLatch = countDownLatch;
	}


	@Override
	public String getStrategy() {
		return this.strategy.getBaseStrategy();
	}


	@Override
	public String getBaseStrategy() {
		return this.baseStrategy;
	}


	/**
	 * @return the listReplacementtDirection
	 */
	public DIRECTION getReplacementtDirection() {
		return listReplacementtDirection;
	}


	/**
	 * @param listReplacementtDirection the listReplacementtDirection to set
	 */
	public void setReplacementtDirection(DIRECTION listReplacementtDirection) {
		this.listReplacementtDirection = listReplacementtDirection;
	}


	
}
