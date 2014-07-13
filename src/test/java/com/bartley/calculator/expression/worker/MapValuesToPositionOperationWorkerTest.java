/**
 * 
 */
package com.bartley.calculator.expression.worker;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import com.bartley.calculator.expression.service.util.ExpressionSecuredUpdater;
import com.bartley.calculator.expression.service.util.CalculatorConstants.DIRECTION;
import com.bartley.calculator.expression.strategy.triple.concrete.MapValuesToPositionInStringOperation;

/**
 * @author Tony
 *
 */
public class MapValuesToPositionOperationWorkerTest {

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
	 * Test method for {@link com.bartley.calculator.expression.strategy.triple.concrete.MapValuesToPositionInStringOperation#doOperation(java.util.List, java.lang.String, boolean)}.
	 */
	@Ignore
	@Test
	public void testDoOperation() {
		MapValuesToPositionInStringOperation<String> mapOperand1 = new MapValuesToPositionInStringOperation<String>();
		ExpressionSecuredUpdater expressionSecuredUpdater = Mockito.mock(ExpressionSecuredUpdater.class);
		String operatorToBeReplaced1 = "cos(0.0)";
		List<String> unaryValueList = new ArrayList<String>();
		List<String> binaryValueList = new ArrayList<String>();
		unaryValueList.add("1.0");
		unaryValueList.add("0.0");
		binaryValueList.add("9.0");
		binaryValueList.add("16.0");
		String returnValue1 ="1.0 + pow(3.0,3.0) + 17.7 + 1.0 + 1.0";
		when(expressionSecuredUpdater.updateExpression(operatorToBeReplaced1, "1.0")).thenReturn("1.0 + pow(3.0,3.0) + 17.7 + 1.0 + 1.0");
		when(expressionSecuredUpdater.getExpression()).thenReturn(returnValue1);
		
		ConcurrentSkipListMap<String, String> swapMap = new ConcurrentSkipListMap<String, String>();
		swapMap.put("cos(0.0)", "1.0");
		swapMap.put("pow(3.0,3.0)", "27.0");
		mapOperand1.doOperation(swapMap, expressionSecuredUpdater,DIRECTION.ASCENDING);
		String newExpression = expressionSecuredUpdater.getExpression();
		assertTrue("The expected return string is: " + returnValue1 + ",but the returned string was:" + newExpression,newExpression == returnValue1);
	}
}
