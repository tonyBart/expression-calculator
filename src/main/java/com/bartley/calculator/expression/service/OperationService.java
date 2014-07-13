package com.bartley.calculator.expression.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

import com.bartley.calculator.expression.exception.CalculatorException;
import com.bartley.calculator.expression.service.util.ExpressionSecuredUpdater;
import com.bartley.calculator.expression.service.util.Updater;
import com.bartley.calculator.expression.strategy.triple.TripleOperatorStrategy;
import com.bartley.calculator.expression.strategy.triple.concrete.MapValuesToPositionInStringOperation;
import com.bartley.calculator.expression.worker.MapValuesToPositionOperationWorker;
import com.bartley.calculator.expression.worker.OperationWorker;

import static com.bartley.calculator.expression.service.util.CalculatorConstants.*;
import static com.bartley.calculator.expression.service.util.CalculatorUtils.*;


public class OperationService<T> {
	private final int MAX_AMOUNT_THREADS;
	private final int DELAY;
	
	public OperationService(int maxThreads, int delay){
		this.MAX_AMOUNT_THREADS = maxThreads;
		this.DELAY = delay;
	}
	
	/**
	 * 
	 * @param unaryOperationsList
	 * @return
	 * @throws ExecutionException
	 */
	public List<T> processOperations(List<? extends OperationWorker<T>> operationsList) throws ExecutionException{
		ThreadPoolExecutor consumerFactory = null;
    	List<Future<T>> futureslist = new ArrayList<Future<T>>();
    	List<T> returnList = new ArrayList<T>();
        try{
        	CountDownLatch latchDone = new CountDownLatch(operationsList.size());
         	consumerFactory = setWorkersWorking(operationsList, futureslist,
					latchDone);
        	try{
        		latchDone.await();
        	}catch(InterruptedException interExcep){
        	}
        	returnList = waitUntilDone(futureslist);
        }finally{
        	consumerFactory.shutdown();
        }
        return returnList;
	}

	private ThreadPoolExecutor setWorkersWorking(
			List<? extends OperationWorker<T>> operationsList,
			List<Future<T>> futureslist, CountDownLatch latchDone) {
		ThreadPoolExecutor consumerFactory;
		int amountOfThreads =0;
		if(operationsList.size() <= MAX_AMOUNT_THREADS){
			amountOfThreads = operationsList.size();
		}else{
			amountOfThreads = MAX_AMOUNT_THREADS;
		}
		BlockingQueue<Runnable> consumerThreadQueue = new LinkedBlockingQueue<Runnable>();
		consumerFactory = new ThreadPoolExecutor(amountOfThreads,MAX_AMOUNT_THREADS,DELAY, TimeUnit.MICROSECONDS, consumerThreadQueue);
      //latchStart.;
		for(OperationWorker<T> worker: operationsList){
			worker.setCountDownLatch(latchDone);
			futureslist.add((Future<T>) consumerFactory.submit(worker));
      
		}
		return consumerFactory;
	}
	
	/**
	 * 
	 * @param amountUnaryOperations
	 * @param resultsList
	 * @param expression
	 * @param unaryMatcher
	 * @param binaryMatcher
	 * @return
	 * @throws ExecutionException
	 */
	public String  updateExpressionWithValues(int requestedNumThreads,
		List<T> resultsList, String expression, List<Matcher> theMatchers, List<Integer> amountPerMatcher) throws ExecutionException {
		BlockingQueue<Runnable> consumerThreadQueue = new LinkedBlockingQueue<Runnable>();
    	ThreadPoolExecutor consumerFactory = null;
    	
    	// Create an secured updater so workers can safely update the expression
    	Updater expressionSecuredUpdater = new ExpressionSecuredUpdater(expression, null);
    				
    	try{
        	int amountOfThreads =0;
        	if(theMatchers.size() <= MAX_AMOUNT_THREADS){
        		amountOfThreads = requestedNumThreads;
        	}else{
        		amountOfThreads = MAX_AMOUNT_THREADS;
        	}
        	// Change the results into a List<String>
			List<String> stringResultsList = new ArrayList<String>();
			for(T result: resultsList){
				stringResultsList.add(result.toString());
			}
			consumerFactory = new ThreadPoolExecutor(amountOfThreads,MAX_AMOUNT_THREADS,DELAY, TimeUnit.MICROSECONDS, consumerThreadQueue);
			CountDownLatch countDownLatch = new CountDownLatch(amountOfThreads);
			int startIndex = 0;
			int matcherIndex = 0;
			for(Matcher matcher:theMatchers){
				ConcurrentSkipListMap<String,String> opToValsMap = mapOperationsTovalues(stringResultsList.subList(startIndex, 
						startIndex + amountPerMatcher.get(matcherIndex++)),matcher);
				TripleOperatorStrategy<ConcurrentSkipListMap<String,String>,Updater,DIRECTION,String> sameStrategy1 = new MapValuesToPositionInStringOperation<String>();
				OperationWorker<String> worker = new MapValuesToPositionOperationWorker<String>(sameStrategy1,opToValsMap, expressionSecuredUpdater); 
				worker.setCountDownLatch(countDownLatch);
				consumerFactory.submit(worker);
				startIndex += amountPerMatcher.get(matcherIndex++);
			}
		
			try {
				countDownLatch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}finally{
				consumerFactory.shutdown();
			}
    	}catch(Exception ex){
    		throw new CalculatorException(ex.getMessage());
    	}
		return expressionSecuredUpdater.getExpression();
		
	}

	
	
	/**
	 * 
	 * @param futuresList
	 * @return
	 * @throws ExecutionException
	 */
	private List<T> waitUntilDone(List<Future<T>> futuresList ) throws ExecutionException{
		System.out.println("Inside OperationService.waitUntilDone, HashCode: " + this.hashCode());
		// Process each result as all threads finished
		List<T> returnList = new ArrayList<T>();
		for(Future<T> future : futuresList){
			try{
				returnList.add(future.get());
			}catch (InterruptedException e) {
				// Do Nothing
			}	
		}
		return returnList;
		
	}
	
}
