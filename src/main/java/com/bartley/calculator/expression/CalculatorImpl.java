/**
 * 
 */
package com.bartley.calculator.expression;

import static com.bartley.calculator.expression.service.util.CalculatorConstants.*;
import static com.bartley.calculator.expression.service.util.CalculatorUtils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;

import com.bartley.calculator.expression.exception.CalculatorException;
import com.bartley.calculator.expression.service.OperationService;
import com.bartley.calculator.expression.service.util.ExpressionSecuredUpdater;
import com.bartley.calculator.expression.service.util.Updater;
import com.bartley.calculator.expression.service.util.WorkerMatcherCarrier;
import com.bartley.calculator.expression.strategy.triple.TripleOperatorStrategy;
import com.bartley.calculator.expression.strategy.triple.concrete.MapValuesToPositionInStringOperation;
import com.bartley.calculator.expression.worker.MapValuesToPositionOperationWorker;
import com.bartley.calculator.expression.worker.OperationWorker;


/**
 * @author Tony
 *
 */
public class CalculatorImpl implements Calculator {

	/**
	 * 
	 */
	public CalculatorImpl() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.bartley.calculator.expression.Calculator#calculate(java.lang.String)
	 */
	@Override
	public Double calculate(String expression) {
		OperationService<Double> operationService = new OperationService<Double>(5, 0);
		WorkerMatcherCarrier<OperationWorker<Double>> unaryCarrier = processUnaryExceptNegate(expression);
		int amountUnaryWorkers = unaryCarrier.getWorkers().size();
		WorkerMatcherCarrier<OperationWorker<Double>> binaryCarrier = processBinary(expression);
		List<OperationWorker<Double>> workerList = new ArrayList<OperationWorker<Double>>();
		workerList.addAll(unaryCarrier.getWorkers());
		workerList.addAll(binaryCarrier.getWorkers());
		double finalResult = 0.0;
		try {	
			// Process Unary & Binary operations concurrently
			List<Double> resultsList  = operationService.processOperations(workerList);
			Matcher unaryMatcher  = unaryCarrier.getMatcher().pattern().matcher(expression);
			Matcher binaryMatcher  = binaryCarrier.getMatcher().pattern().matcher(expression);
			String finalExpression  = updateExpressionWithValues(amountUnaryWorkers,resultsList,expression,unaryMatcher, binaryMatcher);
			
			// Only base operations left, so process them
			finalResult = processBaseOperations(processBrackets(finalExpression));
			
		} catch (ExecutionException e) {
			throw new CalculatorException(e.getMessage());
		}
		return finalResult;
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
	private String  updateExpressionWithValues(int amountUnaryOperations,
		List<Double> resultsList, String expression,  Matcher unaryMatcher, Matcher binaryMatcher) throws ExecutionException {
		TripleOperatorStrategy<ConcurrentSkipListMap<String,String>,Updater,DIRECTION,String> sameStrategy1 = new MapValuesToPositionInStringOperation<String>();
		TripleOperatorStrategy<ConcurrentSkipListMap<String,String>,Updater,DIRECTION,String> sameStrategy2 = new MapValuesToPositionInStringOperation<String>();
		OperationService<String> operationSevice = new OperationService<String>(2,0);
		List<MapValuesToPositionOperationWorker<String>> workersList = new ArrayList<MapValuesToPositionOperationWorker<String>>();
		// Change the results into a List<String>
		List<String> stringResultsList = new ArrayList<String>();
		for(Double result: resultsList){
			stringResultsList.add("" + result);
		}
		// Do the unary pattern first
		ConcurrentSkipListMap<String,String> opToValsMap = mapOperationsTovalues(stringResultsList.subList(0, amountUnaryOperations),unaryMatcher);
		// Now the binary pattern
		opToValsMap.putAll(mapOperationsTovalues(stringResultsList.subList(amountUnaryOperations, resultsList.size()),binaryMatcher));
		// Create an secured updater so workers can safely update the expression
		Updater expressionSecuredUpdater = new ExpressionSecuredUpdater(expression, opToValsMap);
		workersList.add(new MapValuesToPositionOperationWorker<String>(sameStrategy1,opToValsMap, expressionSecuredUpdater));
		// Reverse direction
		workersList.add(new MapValuesToPositionOperationWorker<String>(sameStrategy2,opToValsMap, expressionSecuredUpdater));
		MapValuesToPositionOperationWorker<String> temp = (MapValuesToPositionOperationWorker<String>)workersList.get(1);
		temp.setReplacementtDirection(DIRECTION.DESCENDING);
		try {
			operationSevice.processOperations(workersList);
		} catch (Exception e) {
			throw new CalculatorException(e.getMessage());
		}
		return expressionSecuredUpdater.getExpression();
		
	}

	

}
