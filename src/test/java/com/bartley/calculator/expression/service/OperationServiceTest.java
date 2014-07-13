/**
 * 
 */
package com.bartley.calculator.expression.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bartley.calculator.expression.service.OperationService;
import com.bartley.calculator.expression.strategy.unary.concrete.CosOperation;
import com.bartley.calculator.expression.strategy.unary.concrete.SineOperation;
import com.bartley.calculator.expression.worker.UnaryOperationWorker;

/**
 * @author Tony
 *
 */
public class OperationServiceTest {

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
	 * Test method for {@link com.bartley.calculator.expression.service.UnaryOperationService#processOperations(java.util.List)}.
	 */
	@Test
	public void testProcessOperations() {
		OperationService<Double>  operationService = new  OperationService<Double>(2,20);
		List<UnaryOperationWorker<Double,Double>> unaryOperationsList = new ArrayList<UnaryOperationWorker<Double,Double>>();
		
		UnaryOperationWorker<Double,Double> cosworker = new UnaryOperationWorker<Double,Double>(0.0, new CosOperation());
		UnaryOperationWorker<Double,Double> sineworker = new UnaryOperationWorker<Double,Double>(0.0, new SineOperation());
		unaryOperationsList.add(sineworker);
		unaryOperationsList.add(cosworker);
		
		List<Double> expectedDoublesList = new ArrayList<Double>();
		expectedDoublesList.add(0.0);
		expectedDoublesList.add(1.0);
		List<Double> resultList = null;
		try {
			resultList = operationService.processOperations(unaryOperationsList);
		} catch (ExecutionException e) {
			
			e.printStackTrace();
			assertTrue("Exited with the following error: " + e.getMessage(),false);
		}
		
		assertTrue("The number of UnaryOperationWorker's should be 2", resultList.size() == 2);
		int index = 0;
		for(double value :expectedDoublesList){
			assertTrue("Operation index number: " + index + " of result list expected " + value + "but was: " +  resultList.get(index),value == resultList.get(index++));
		}
	}

}
