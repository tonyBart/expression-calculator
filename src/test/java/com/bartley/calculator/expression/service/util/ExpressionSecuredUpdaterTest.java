/**
 * 
 */
package com.bartley.calculator.expression.service.util;

import static com.bartley.calculator.expression.service.util.CalculatorUtils.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.regex.Matcher;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bartley.calculator.expression.service.OperationService;
import com.bartley.calculator.expression.service.util.CalculatorConstants;
import com.bartley.calculator.expression.service.util.ExpressionSecuredUpdater;
import com.bartley.calculator.expression.service.util.Updater;
import com.bartley.calculator.expression.strategy.triple.TripleOperatorStrategy;
import com.bartley.calculator.expression.strategy.triple.concrete.MapValuesToPositionInStringOperation;
import com.bartley.calculator.expression.worker.MapValuesToPositionOperationWorker;


/**
 * @author Tony
 *
 */
public class ExpressionSecuredUpdaterTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.bartley.calculator.expression.service.util.ExpressionSecuredUpdater#getExpression()}.
	 */
	@Test
	public void testGetExpression() {
		ExpressionSecuredUpdater expressionSecuredUpdater = new ExpressionSecuredUpdater("sin(25)", null);
		assertTrue("The returned expression should be 'sin(25)' but was: " + expressionSecuredUpdater.getExpression(),
				expressionSecuredUpdater.getExpression().equalsIgnoreCase("sin(25)"));
		
	}

	/**
	 * Test method for {@link com.bartley.calculator.expression.service.util.ExpressionSecuredUpdater#updateExpression(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testUpdateExpression() {
		List<String> binaryValList = new ArrayList<String>();
		binaryValList.add("4.0");
		binaryValList.add("27.0");
		List<String> unaryValList = new ArrayList<String>();
		unaryValList.add("0.0");
		unaryValList.add("0.0");
		String expression = "32.5 + pow(2.0,2.0) + 64 + pow(3.0,3.0) + cos(90.0) + sin(0.0)";
		Matcher binaryMatcher = CalculatorConstants.binaryContextPattern.matcher(expression);
		Matcher unaryMatcher = CalculatorConstants.unaryContextPattern.matcher(expression);
		ConcurrentSkipListMap<String,String> opToValsMap = mapOperationsTovalues(unaryValList,unaryMatcher);
		// Now the binary pattern
		opToValsMap.putAll( mapOperationsTovalues(binaryValList,binaryMatcher));
		ExpressionSecuredUpdater expressionSecuredUpdater = new ExpressionSecuredUpdater(expression, opToValsMap);
		expressionSecuredUpdater.updateExpression("pow(2.0,2.0)","4.0");
		expressionSecuredUpdater.updateExpression("pow(3.0,3.0)","27.0");
		expressionSecuredUpdater.updateExpression("cos(90.0)","0.0");
		expressionSecuredUpdater.updateExpression("sin(0.0)","0.0");
		assertTrue("The returned string should be: 32.5 + 4.0 + 64.0 + 27.0 + 0.0 + 0.0, but was: " + expressionSecuredUpdater.getExpression(),expressionSecuredUpdater.getExpression().equalsIgnoreCase("32.5 + 4.0 + 64 + 27.0 + 0.0 + 0.0"));
	}
	
	@Test
	public void testConcurrentUpdateExpression(){

		TripleOperatorStrategy<ConcurrentSkipListMap<String,String>,Updater,CalculatorConstants.DIRECTION,String> sameStrategy1 = new MapValuesToPositionInStringOperation<String>();
		TripleOperatorStrategy<ConcurrentSkipListMap<String,String>,Updater,CalculatorConstants.DIRECTION,String> sameStrategy2 = new MapValuesToPositionInStringOperation<String>();
		OperationService<String> operationService = new OperationService<String>(2, 0);
		List<MapValuesToPositionOperationWorker<String>> workersList = new ArrayList<MapValuesToPositionOperationWorker<String>>();
		List<Double> binaryValList = new ArrayList<Double>();
		binaryValList.add(4.0);
		binaryValList.add(27.0);
		List<Double> unaryValList = new ArrayList<Double>();
		unaryValList.add(0.0);
		unaryValList.add(0.0);
		String expression = "32.5 + pow(2.0,2.0) + 64 + pow(3.0,3.0) + cos(90.0) + sin(0.0)";
		Matcher binaryMatcher = CalculatorConstants.binaryContextPattern.matcher(expression);
		Matcher unaryMatcher = CalculatorConstants.unaryContextPattern.matcher(expression);
		// Change the results into a List<String>
		List<String> binaryStringResultsList = new ArrayList<String>();
		for(Double result: binaryValList){
			binaryStringResultsList.add("" + result);
		}
		
		List<String> unaryStringResultsList = new ArrayList<String>();
		for(Double result1: unaryValList){
			unaryStringResultsList.add("" + result1);
		}
		// Do the unary pattern first
		ConcurrentSkipListMap<String,String> opToValsMap = mapOperationsTovalues(unaryStringResultsList,unaryMatcher);
		// Now the binary pattern
		opToValsMap.putAll( mapOperationsTovalues(binaryStringResultsList,binaryMatcher));
		ExpressionSecuredUpdater expressionSecuredUpdater = new ExpressionSecuredUpdater(expression, opToValsMap);
		// Add workers to list
		workersList.add(new MapValuesToPositionOperationWorker<String>(sameStrategy1,opToValsMap, expressionSecuredUpdater));
		workersList.add(new MapValuesToPositionOperationWorker<String>(sameStrategy2,opToValsMap, expressionSecuredUpdater));
		
		try {
			operationService.processOperations(workersList);
		} catch (Exception e) {
			System.out.println("" + e.getMessage());
		}
		
		assertTrue("The returned string should be: 32.5 + 4.0 + 64.0 + 27.0 + 0.0 + 0.0, but was: " + expressionSecuredUpdater.getExpression(),expressionSecuredUpdater.getExpression().equalsIgnoreCase("32.5 + 4.0 + 64 + 27.0 + 0.0 + 0.0"));
	}

}
