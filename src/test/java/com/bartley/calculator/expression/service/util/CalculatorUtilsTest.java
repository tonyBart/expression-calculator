/**
 * 
 */
package com.bartley.calculator.expression.service.util;

import static org.junit.Assert.*;
import static com.bartley.calculator.expression.service.util.CalculatorUtils.*;

import java.util.List;
import java.util.regex.Matcher;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.bartley.calculator.expression.exception.CalculatorException;
import com.bartley.calculator.expression.service.util.CalculatorConstants;
import com.bartley.calculator.expression.service.util.WorkerMatcherCarrier;
import com.bartley.calculator.expression.worker.BinaryOperationWorker;
import com.bartley.calculator.expression.worker.OperationWorker;
import com.bartley.calculator.expression.worker.UnaryOperationWorker;

/**
 * @author Tony
 *
 */
public class CalculatorUtilsTest {

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
	 * Test method for {@link com.bartley.calculator.expression.service.util.CalculatorUtils#processUnaryExceptNegate(java.lang.String)}.
	 */
	@Test
	public void testProcessUnaryExceptNegate() {
		WorkerMatcherCarrier<OperationWorker<Double>> returnCarrier = null;
		Matcher expectedOneNumberContextMatcher = CalculatorConstants.unaryContextPattern.matcher("sin(90.0) + 19.9 + cos(0.0)");
		String expression = "sin(-90.0) + 19.9 --24 + cos(0.0)";
		returnCarrier = processUnaryExceptNegate(expression);
		Matcher returnedMatcher = returnCarrier.getMatcher();
		List<? extends OperationWorker<Double>> workersList =   returnCarrier.getWorkers();
		UnaryOperationWorker<Double,Double>  unaryWorker1 = (UnaryOperationWorker<Double,Double>)workersList.get(0);
		UnaryOperationWorker<Double,Double>  unaryWorker2 = (UnaryOperationWorker<Double,Double>)workersList.get(1);
		assertTrue("The matcher returned should be a UnaryContextMatcher",expectedOneNumberContextMatcher.getClass().toString().equalsIgnoreCase(returnedMatcher.getClass().toString()));
		assertEquals("There should be two unaryContextOperations ",workersList.size(),2 );
		assertTrue("The number for the first negation operation should be -90.0", unaryWorker1.getFirstItem() == -90.0);
		assertTrue("The number for the cos operation should be 0.0", unaryWorker2.getFirstItem() == 0.0);
	}

	/**
	 * Test method for {@link com.bartley.calculator.expression.service.util.CalculatorUtils#getOperands(java.lang.String)}.
	 */
	@Test
	public void testGetOperands() {
		List<String> operandsList = getOperands("9.8 + 3.4*72/3");
		assertTrue("The operand for index: 0 should be '+'", operandsList.get(0).equalsIgnoreCase("+"));
		assertTrue("The operand for index: 1 should be '*'", operandsList.get(1).equalsIgnoreCase("*"));
		assertTrue("The operand for index: 2 should be '/'", operandsList.get(2).equalsIgnoreCase("/"));
	}

	/**
	 * Test method for {@link com.bartley.calculator.expression.service.util.CalculatorUtils#getUnaryExceptNegateMatches(java.util.regex.Matcher)}.
	 */
	@Test
	public void testGetUnaryExceptNegateMatches() {
		/// Normal operation
		Matcher oneNumberContextMatcher = CalculatorConstants.unaryContextPattern.matcher("sin(90.0) + 19.9 + cos(0.0)");
		List<OperationWorker<Double>> workersList = getUnaryExceptNegateMatches(oneNumberContextMatcher);
		UnaryOperationWorker<Double,Double>  unaryWorker1 = (UnaryOperationWorker<Double,Double>)workersList.get(0);
		UnaryOperationWorker<Double,Double>  unaryWorker2 = (UnaryOperationWorker<Double,Double>)workersList.get(1);
		assertTrue("There should be two unaryContextOperations ",workersList.size() == 2 );
		assertTrue("The number for the sine operation should be 90.0", unaryWorker1.getFirstItem() == 90.0);
		assertTrue("The number for the cos operation should be 0.0", unaryWorker2.getFirstItem() == 0.0);
		
		/// Normal operation with spaces added in operators
		oneNumberContextMatcher = CalculatorConstants.unaryContextPattern.matcher("sin ( 90.0) + 19.9 +cos(0.0)");
		workersList = getUnaryExceptNegateMatches(oneNumberContextMatcher);
		assertEquals("There should be two unaryContextOperations ",workersList.size(),2 );
		assertTrue("The number for the sine operation should be 90.0", unaryWorker1.getFirstItem() == 90.0);
		assertTrue("The number for the cos operation should be 0.0", unaryWorker2.getFirstItem() == 0.0);
			
		workersList = null;
		// Error case as unknown operation
		try{
			oneNumberContextMatcher = CalculatorConstants.unaryContextPattern.matcher("sin(90.0) + 19.9 + pos(0.0)");
			workersList = getUnaryExceptNegateMatches(oneNumberContextMatcher);
		}catch(CalculatorException calEx){
			assertTrue("Unknown operator, so should be an CalculatorException with message: 'Unrecognised UnaryOperator: POS; \n actual message is: " + calEx.getMessage(), calEx.getMessage().equalsIgnoreCase("Unrecognised UnaryOperator: POS"));
		}
		assertTrue("Unknown operator, so should be an CalculatorException with message: 'Unknown UnaryOperator: POW' ", workersList == null);
	}

	/**
	 * Test method for {@link com.bartley.calculator.expression.service.util.CalculatorUtils#getConstants(java.lang.String)}.
	 */
	@Test
	public void testGetConstants() {
		String binaryOpsString = "9.8 + 3.4*72/3";
		List<Double> constantsList  = getConstants(binaryOpsString); 
		assertTrue("The value for index: 0 should be '9.8'", constantsList.get(0) ==  9.8);
		assertTrue("The value for index: 1 should be '3.4'", constantsList.get(1) == 3.4);
		assertTrue("The value for index: 2 should be '72.0'", constantsList.get(2) == 72.0);
		assertTrue("The value for index: 3 should be '3.0'", constantsList.get(3) == 3.0);
	}
	
	@Test
	public void testProcessBaseOperations(){
		double result = processBaseOperations("2.0*6.0 + 14.0/7.0 -3.0");
		assertTrue("The result should be: 11.0", result == 11.0);
		//
		result = processBaseOperations("3.0+2.0*6.0 + 14.0/7.0 -3.0");
		assertTrue("The result should be: 14.0", result == 14.0);
		//
		result = processBaseOperations("3.0-2.0*6.0 + 14.0/7.0 -3.0");
		assertTrue("The result should be: -10.0", result == -10.0);
		
	}
	
	@Test
	public void testGetBinaryMatches(){
		Matcher binaryContextMatcher = CalculatorConstants.binaryContextPattern.matcher("32.5 + pow(2.0,2.0) + 64 + pow(3.0,3.0)");
		List<OperationWorker<Double>> binaryOperationWorkerList = getBinaryMatches(binaryContextMatcher);
		assertTrue("There should be 2 pow BinaryOperationWorker's",binaryOperationWorkerList.size() == 2);
		BinaryOperationWorker<Double,Double,Double>  binaryWorker1 = (BinaryOperationWorker<Double,Double,Double>)binaryOperationWorkerList.get(0);
		BinaryOperationWorker<Double,Double,Double>  binaryWorker2 = (BinaryOperationWorker<Double,Double,Double>)binaryOperationWorkerList.get(1);
		assertTrue("The value's of the first power operator should be 2.0 & 2.0",binaryWorker1.getFirstItem() == 2.0 && binaryWorker1.getSecondItem() == 2.0);
		assertTrue("The value of the second power operator should be 3.0 & 3.0",binaryWorker2.getFirstItem() == 3.0 &&
				binaryWorker2.getSecondItem() == 3.0);
	}
	
	@Test
	public void testProcessBinary(){
		WorkerMatcherCarrier<OperationWorker<Double>> carrier  = processBinary("32.5 + pow(2.0,2.0) + 64 + pow(3.0,3.0)");
		List<? extends OperationWorker<Double>> binaryOperationWorkerList = carrier.getWorkers();
		BinaryOperationWorker<Double,Double,Double>  binaryWorker1 = (BinaryOperationWorker<Double,Double,Double>)binaryOperationWorkerList.get(0);
		BinaryOperationWorker<Double,Double,Double>  binaryWorker2 = (BinaryOperationWorker<Double,Double,Double>)binaryOperationWorkerList.get(1);
		assertTrue("There should be 2 pow BinaryOperationWorker's",binaryOperationWorkerList.size() == 2);
		assertTrue("The value's of the first power operator should be 2.0 & 2.0",binaryWorker1.getFirstItem() == 2.0 &&
				binaryWorker1.getSecondItem() == 2.0);
		assertTrue("The value of the second power operator should be 3.0 & 3.0",binaryWorker2.getFirstItem() == 3.0 &&
				binaryWorker2.getSecondItem() == 3.0);
	}
	
	@Test
	public void testProcess2Brackets(){
		String expectedResult = "8.0";
		String resultString = "" + processBrackets("(4.5 + 3.5)"); 
		assertTrue("", expectedResult.equals(resultString));
	}
	
	@Test
	public void testProcess3Brackets(){
		String expectedResult = "119.0";
		String resultString = "" + processBrackets("13.5*(4.5 + 3.5)+ (33.0/3.0)");
		assertTrue("", expectedResult.equals(resultString));
	}
	
	
	@Test
	public void testMissingStartBrackets(){
		boolean exceptionThrown = false;
		try{
			processBrackets("13.5*(4.5 + 3.5)+ (33.0/3.0))");
		
		}catch(CalculatorException calEx){
			assertTrue("", CalculatorConstants.MISSING_BRACKET.equals(calEx.getMessage()));
			exceptionThrown = true;
		}
		assertTrue("Exception should have been thrown missing start bracket",exceptionThrown);
	}
	
	@Test
	public void testMissingEndBrackets(){
		boolean exceptionThrown = false;
		try{
			processBrackets("(13.5*(4.5 + 3.5)+ (33.0/3.0)");
		
		}catch(CalculatorException calEx){
			assertTrue("", CalculatorConstants.MISSING_BRACKET.equals(calEx.getMessage()));
			exceptionThrown = true;
		}
		assertTrue("Exception should have been thrown missing end bracket",exceptionThrown);
		exceptionThrown = false;
		try{
			processBrackets("(13.5*(4.5 + 3.5+ (33.0/3.0))");
		
		}catch(CalculatorException calEx){
			assertTrue("", CalculatorConstants.MISSING_BRACKET.equals(calEx.getMessage()));
			exceptionThrown = true;
		}
		assertTrue("Exception should have been thrown missing end bracket",exceptionThrown);
	}
	
}
