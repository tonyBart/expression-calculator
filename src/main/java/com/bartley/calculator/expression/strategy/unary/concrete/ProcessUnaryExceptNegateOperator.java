/**
 * 
 */
package com.bartley.calculator.expression.strategy.unary.concrete;

import static com.bartley.calculator.expression.service.util.CalculatorConstants.binaryContextPattern;
import static com.bartley.calculator.expression.service.util.CalculatorUtils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import com.bartley.calculator.expression.service.util.WorkerMatcherCarrier;
import com.bartley.calculator.expression.strategy.unary.UnaryOperatorStrategy;
import com.bartley.calculator.expression.worker.OperationWorker;

/**
 * @author Tony
 *
 */
public class ProcessUnaryExceptNegateOperator extends UnaryOperatorStrategy<String,WorkerMatcherCarrier<OperationWorker<Double>>> {

	@Override
	public WorkerMatcherCarrier<OperationWorker<Double>> doOperation(String expression) {
		List<OperationWorker<Double>> binaryWorkerList = new ArrayList<OperationWorker<Double>>();
		Matcher binaryContextMatcher = binaryContextPattern.matcher(expression);
		
		// If any matches are found create a list of the operations
		binaryWorkerList = getBinaryMatches(binaryContextMatcher);
	
		// Put  the operations and the matcher in a map so later the operations can be replaced by the values
		WorkerMatcherCarrier<OperationWorker<Double>> carrier = new WorkerMatcherCarrier<OperationWorker<Double>>();
		carrier.setMatcher(binaryContextMatcher);
		carrier.setWorkers(binaryWorkerList);
		return carrier;
	}

}
