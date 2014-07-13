/**
 * 
 */
package com.bartley.calculator.expression.service.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.regex.Matcher;











import static com.bartley.calculator.expression.service.util.CalculatorConstants.*;

import com.bartley.calculator.expression.exception.CalculatorException;
import com.bartley.calculator.expression.service.OperationService;
import com.bartley.calculator.expression.strategy.binary.concrete.PowerOperation;
import com.bartley.calculator.expression.strategy.unary.concrete.ACosOperation;
import com.bartley.calculator.expression.strategy.unary.concrete.ASineOperation;
import com.bartley.calculator.expression.strategy.unary.concrete.ATanOperation;
import com.bartley.calculator.expression.strategy.unary.concrete.CosOperation;
import com.bartley.calculator.expression.strategy.unary.concrete.ExponentialM1Operation;
import com.bartley.calculator.expression.strategy.unary.concrete.ExponentialOperation;
import com.bartley.calculator.expression.strategy.unary.concrete.Log10Operation;
import com.bartley.calculator.expression.strategy.unary.concrete.Log1pOperation;
import com.bartley.calculator.expression.strategy.unary.concrete.LogOperation;
import com.bartley.calculator.expression.strategy.unary.concrete.ProcessBracketsOperation;
import com.bartley.calculator.expression.strategy.unary.concrete.SineOperation;
import com.bartley.calculator.expression.strategy.unary.concrete.SquareRootOperation;
import com.bartley.calculator.expression.strategy.unary.concrete.TanOperation;
import com.bartley.calculator.expression.worker.BinaryOperationWorker;
import com.bartley.calculator.expression.worker.OperationWorker;
import com.bartley.calculator.expression.worker.UnaryOperationWorker;

/**
 * @author Tony
 *
 */
public class CalculatorUtils {
	
	/**
	 * USED TO PROCESS COMBINATIONS OF NON-NEGATING BASE OPERRATIONS
	 * 
	 * @param equation
	 * @return
	 */
	public static WorkerMatcherCarrier<OperationWorker<Double>> processUnaryExceptNegate(String expression){
		// Process all the one number contexts except for negate which does not satisfy the pattern
		Matcher unaryContextMatcher = unaryContextPattern.matcher(expression);
		List<OperationWorker<Double>> unaryWorkerExceptNegateList = null;
		// Process all the matches found
		unaryWorkerExceptNegateList = getUnaryExceptNegateMatches(unaryContextMatcher);
		// Put  the operations and the matcher in a map so later the operations can be replaced by the values
		WorkerMatcherCarrier<OperationWorker<Double>> carrier = new WorkerMatcherCarrier<OperationWorker<Double>>();
		carrier.setMatcher(unaryContextMatcher.pattern().matcher(expression));
		carrier.setWorkers(unaryWorkerExceptNegateList);
		return carrier;
	}
	
	
	/**
	 * USED TO PROCESS  THE BRACKET UNARY OPERRATION, WHICH IS A COMPOSITIONAL OPERATION
	 * 
	 * @param expression
	 * @return
	 */
	public static String processBrackets1(final String expression){
		String returnString = "";
		boolean finished = false;
		while(!finished){
			int firstStartBracket = expression.indexOf(START_BRACKET);
			int nextEndBracket = locateEndBracketIndex(firstStartBracket + 1,expression);
			if(firstStartBracket < 0 && nextEndBracket >= 0 ){
				throw new CalculatorException(MISSING_START_BRACKET);
			}else{
				if( nextEndBracket < 0 &&  firstStartBracket >= 0){
					throw new CalculatorException(MISSING_END_BRACKET);
				}else{
					if(nextEndBracket < firstStartBracket){
						throw new CalculatorException(MISSING_START_BRACKET);
					}else{
						if(firstStartBracket < 0 && nextEndBracket < 0){
							// No more brackets so return expression
							returnString = "" + processBaseOperations(expression);
							finished = true;
						}else{
							//extract the first part
							String evalFirstPart = expression.substring(0, firstStartBracket);
							int matchingEndBracket = locateEndBracketIndex(firstStartBracket,expression);
						
							// Process the bracketed part
							String processNextPart = expression.substring(firstStartBracket, matchingEndBracket + 1);
							processNextPart = extractSiblingBrackets(processNextPart, null);
							String evalLastPart = expression.substring(matchingEndBracket + 1);
							
							returnString = evalFirstPart + processBrackets(processNextPart) +
									evalLastPart;
							finished = true;
						}
					}
				}
			}
		}
		return returnString;
	}
	
	/**
	 * USED TO PROCESS  THE BRACKET UNARY OPERRATION, WHICH IS A COMPOSITIONAL OPERATION
	 * 
	 * @param expression
	 * @return
	 */
	public static String processBrackets(final String expression){
		// Check if no brackets exist
		if(expression.indexOf(START_BRACKET) == -1 || (expression.indexOf(END_BRACKET) == -1 )){
			if(expression.indexOf(START_BRACKET) > -1 && expression.indexOf(END_BRACKET) == -1  || 
					expression.indexOf(START_BRACKET) == -1 && expression.indexOf(END_BRACKET) > -1 ){
				throw new CalculatorException(MISSING_BRACKET);
			}
			// No Brackets 
			return expression;
		}
		Map<Integer,Integer> matchingBracketsMap = getSiblingBrackets(expression);
		checkForExtraBrackets(matchingBracketsMap,expression);
		String processNextPart = processSiblings(expression, matchingBracketsMap);
		
		String bracketsFreeExpression = "" + processBaseOperations(processNextPart);
		return bracketsFreeExpression;
	}


	public static String processSiblings(final String expression,
			Map<Integer, Integer> matchingBracketsMap) {
		// Process the bracketed sibling parts
		String processNextPart = expression;
		
		List<UnaryOperationWorker<String,String>> operationsList = new ArrayList<UnaryOperationWorker<String,String>>();
		List<Integer> startingIndexList = new ArrayList<Integer>(matchingBracketsMap.keySet());
		
		for(Integer start :startingIndexList){
			operationsList.add(new UnaryOperationWorker<String,String>(expression.substring(start + 1, matchingBracketsMap.get(start)), new ProcessBracketsOperation()));
		}
		List<String> bracketSolutions = null;
		try {
			if(startingIndexList.size() > 1){
				OperationService<String> operationService = new OperationService<String>(matchingBracketsMap.size(),0);
				bracketSolutions = operationService.processOperations(operationsList);
			}else{
				bracketSolutions = new ArrayList<String>();
				bracketSolutions.add(operationsList.get(0).call());
			}
			
			int index = 0;
			for(Integer starter :startingIndexList ){
				processNextPart = processNextPart.replace(expression.substring(starter, matchingBracketsMap.get(starter) + 1), "" + processBaseOperations(bracketSolutions.get(index)));
				index += 1;
			}
		} catch (Exception e) {
			throw new CalculatorException(e.getMessage());
		}
		return processNextPart;
	}


	public static String extractSiblingBrackets(final String expression, Map<Integer, Integer> siblingBracketLocationsMap) {
		String evalLastPart = "";
		String duplicateExpression = expression;
		
			List<Integer >siblingBracketStartLocations = new ArrayList<Integer>(siblingBracketLocationsMap.keySet());
			
		for(Integer starter : siblingBracketStartLocations){
			String nextPart = expression.substring(starter, siblingBracketLocationsMap.get(starter) + 1);
			duplicateExpression = duplicateExpression.replace(nextPart, processBrackets(nextPart));
		}
		
		evalLastPart =  "" + processBaseOperations(duplicateExpression);
		
		return evalLastPart;
	}
	
	
	public static void checkForExtraBrackets(
			Map<Integer, Integer> siblingBracketLocationsMap,
			String expression) {
		String message = "";
		int endBracketCount =0;
		int startBracketCount =0;
		int fromIndex = expression.indexOf(START_BRACKET);
		boolean isOk = true;
		try {
			List<Integer> startIndexList = new ArrayList<Integer>();
			while(fromIndex > -1){
				startBracketCount++;
				startIndexList.add(fromIndex);
				fromIndex =  expression.indexOf(START_BRACKET, fromIndex + 1); 
			}
			int toIndex =0;
			int startBracketIndex = 0;
			while((toIndex = expression.indexOf(END_BRACKET, toIndex)) > -1){
				endBracketCount++;
				isOk = startIndexList.size() > endBracketCount  && startIndexList.get(startBracketIndex++) < toIndex;
					toIndex++; 
			}
			
			isOk = endBracketCount == startBracketCount;
	
		}catch(Exception ex){
			message = ex.getMessage();
		}
		
		if(!isOk){
			throw new CalculatorException(MISSING_BRACKET + message);
		}
	}


	public static Map<Integer,Integer> getSiblingBrackets(
			String expression) {
		int startIndex = expression.indexOf(START_BRACKET) ;
		int endIndex = locateEndBracketIndex(startIndex,expression);
		Map<Integer,Integer> siblingBracketLocationsMap = new HashMap<Integer,Integer>();
		boolean end = false;
		while(!end){
			if(startIndex == -1 ){
				end = true;
			} else{
				siblingBracketLocationsMap.put(startIndex, endIndex);
			}
			if(!end){
				startIndex = expression.indexOf(START_BRACKET,endIndex + 1) ;
				endIndex = locateEndBracketIndex(startIndex,expression);
			}
		}
		
		return siblingBracketLocationsMap;
	}



	public static int locateEndBracketIndex(int startIndex, final String expression) {
		int nextStartBracket = expression.indexOf(START_BRACKET, startIndex + 1);
		int nextEndBracket = expression.indexOf(END_BRACKET,startIndex);
		int startBracketCount = 0;
		int endBracketCount = 0;
		int index = -1;
		boolean found = false;
		if(startIndex == -1){
			return index;
		}else{
			startBracketCount++;
		}
		while(!found && nextEndBracket > -1){
			endBracketCount++;
			if(startBracketCount == endBracketCount){
				found = true;
				return nextEndBracket;
			}else{
				nextStartBracket = expression.indexOf(START_BRACKET, nextStartBracket + 1);
				nextEndBracket = expression.lastIndexOf(END_BRACKET,nextEndBracket + 1);
				if(nextStartBracket != -1){
					startBracketCount++;
				}
			}
			
		}
		return index;
	}


	/**
	 * USED TO PROCESS  BINARY OPERRATIONS
	 * 
	 * @param expression
	 * @return
	 */
	public static WorkerMatcherCarrier<OperationWorker<Double>> processBinary(String expression){
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
	
	
	/**
	 * ADD NEW BINARY OPERATORS HERE
	 * 
	 * @param unaryContextMatcher
	 * @param workerNegateMap
	 * @throws CalculatorException: signals unknown binary operation 
	 */
	public static List<OperationWorker<Double>> getBinaryMatches(Matcher binaryContextMatcher) {
		List<OperationWorker<Double>> binaryWorkerList = new ArrayList<OperationWorker<Double>>();
		
		while(binaryContextMatcher.find()){
			String operationName = binaryContextMatcher.group(2);
			double num1 = Double.parseDouble(binaryContextMatcher.group(3));
			double num2 = Double.parseDouble(binaryContextMatcher.group(4));
			if(!BINARY_SET.contains(operationName.toUpperCase())){
				throw new CalculatorException(UNRECOGNIZED_BINARY_OPERATOR + operationName); 
			}
			if(operationName.equalsIgnoreCase(BINARY_OPERATORS.POW.toString())){
				binaryWorkerList.add(new BinaryOperationWorker<Double,Double,Double>(num1,num2,new PowerOperation()));
			}else{
				// Unrecognised BinaryOperator, so throw Exception
				throw new CalculatorException(UNRECOGNIZED_BINARY_OPERATOR + operationName + FORGOT_TO_ADD_THE_OPERATION); 
			}
		}

		return binaryWorkerList;
	}
	
	/**
	 * USED TO PROCESS COMBINATIONS OF NEGATING BASE OPERRATIONS: '+' & '-' 
	 * @param expression
	 * @return
	 */
	private static String processDoublePlusAndMinus(String expression){
		Matcher matcher = minusPlusPattern.matcher(expression);
		String processedString = matcher.replaceAll(MINUS);
		matcher = plusMinusPattern.matcher(processedString);
		processedString = matcher.replaceAll(MINUS);
		matcher = doubleMinusPattern.matcher(processedString);
		processedString = matcher.replaceAll(PLUS);
		matcher = doublePlusPattern.matcher(processedString);
		processedString = matcher.replaceAll(PLUS);
		return processedString;
		
	}
		


	/**
	 * ADD NEW UNARY OPERATORS HERE
	 * 
	 * @param unaryContextMatcher
	 * @param workerMap
	 * @throws CalculatorException: signals unknown unary operation 
	 */
	static List<OperationWorker<Double>> getUnaryExceptNegateMatches(Matcher unaryContextMatcher) {
		List<OperationWorker<Double>> unaryWorkerList = new ArrayList<OperationWorker<Double>>();
		
		while(unaryContextMatcher.find()){
			String operationName = unaryContextMatcher.group(2);
			// Obtain the value which is going to be operated on
			double num = Double.parseDouble(unaryContextMatcher.group(3));
			if(!UNARY_SET.contains(operationName.toUpperCase())){
				throw new CalculatorException(UNRECOGNIZED_UNARY_OPERATOR + operationName);
			}
			if(operationName.equalsIgnoreCase(UNARY_OPERATORS.COS.toString())){
					unaryWorkerList.add( new UnaryOperationWorker<Double,Double>(num,new CosOperation()));
			}else{
				if(operationName.equalsIgnoreCase(UNARY_OPERATORS.ACOS.toString())){
					unaryWorkerList.add( new UnaryOperationWorker<Double,Double>(num,new ACosOperation()));
				}else{
					if(operationName.equalsIgnoreCase(UNARY_OPERATORS.SIN.toString())){
						unaryWorkerList.add( new UnaryOperationWorker<Double,Double>(num,new SineOperation()));
					}else{
						if(operationName.equalsIgnoreCase(UNARY_OPERATORS.ASIN.toString())){
							unaryWorkerList.add( new UnaryOperationWorker<Double,Double>(num,new ASineOperation()));
						}else{
							if(operationName.equalsIgnoreCase(UNARY_OPERATORS.TAN.toString())){
								unaryWorkerList.add( new UnaryOperationWorker<Double,Double>(num,new TanOperation()));
							}else{
								if(operationName.equalsIgnoreCase(UNARY_OPERATORS.ATAN.toString())){
									unaryWorkerList.add( new UnaryOperationWorker<Double,Double>(num,new ATanOperation()));
								}else{
									if(operationName.equalsIgnoreCase(UNARY_OPERATORS.EXP.toString())){
										unaryWorkerList.add( new UnaryOperationWorker<Double,Double>(num,new ExponentialOperation()));
									}else{
										if(operationName.equalsIgnoreCase(UNARY_OPERATORS.EXPM1.toString())){
											unaryWorkerList.add( new UnaryOperationWorker<Double,Double>(num,new ExponentialM1Operation()));
										}else{
											if(operationName.equalsIgnoreCase(UNARY_OPERATORS.LOG.toString())){
												unaryWorkerList.add( new UnaryOperationWorker<Double,Double>(num,new LogOperation()));
											}else{
												if(operationName.equalsIgnoreCase(UNARY_OPERATORS.LOG10.toString())){
													unaryWorkerList.add( new UnaryOperationWorker<Double,Double>(num,new Log10Operation()));
												}else{
													if(operationName.equalsIgnoreCase(UNARY_OPERATORS.LOG1P.toString())){
														unaryWorkerList.add( new UnaryOperationWorker<Double,Double>(num,new Log1pOperation()));
													}else{
														if(operationName.equalsIgnoreCase(UNARY_OPERATORS.SQRT.toString())){
															unaryWorkerList.add( new UnaryOperationWorker<Double,Double>(num,new SquareRootOperation()));
														}else{
															throw new CalculatorException(UNRECOGNIZED_UNARY_OPERATOR + operationName + FORGOT_TO_ADD_THE_OPERATION);
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
			
		}
		return unaryWorkerList;
	}
	
	/**
	 * 
	 * @param binaryOpsString
	 * @return
	 */
		public static  List<Double> getConstants(
				String binaryOpsString) {
			Matcher numberMatcher = numberPattern.matcher(binaryOpsString);
			List<Double> valuesList = new ArrayList<Double>();
			while(numberMatcher.find()){
				valuesList.add(Double.parseDouble(numberMatcher.group()));	
			}
			return valuesList;
		}
		
		/**
		 * 
		 * @param binaryOpsString
		 * @return
		 */
		public static List<String> getOperands(String binaryOpsString){
			Matcher baseOperatorsMatcher = baseOperatorPattern.matcher(binaryOpsString);
			List<String> operatorList = new ArrayList<String>();
			while(baseOperatorsMatcher.find()){
				operatorList.add(baseOperatorsMatcher.group());	
			}
			return operatorList;
		}

		/**
		 * 
		 * @param finalExpression
		 * @return
		 * @throws CalculatorException: signals unknown base operation
		 */
		public static double processBaseOperations(String finalExpression) {
			String processedString = processDoublePlusAndMinus(finalExpression);
			List<Double> valuesList =  getConstants(processedString);
			List<String> operandsList = getOperands(processedString);
			while(valuesList.size() != 1){
				if(( operandsList.get(0).equalsIgnoreCase(PLUS)) && (operandsList.size() == 1 ||operandsList.get(1).equalsIgnoreCase(MINUS) || operandsList.get(1).equalsIgnoreCase(PLUS)  )){
					double result = valuesList.get(0) + valuesList.get(1);
					operandsList.remove(0);
					valuesList.remove(1);
					valuesList.remove(0);
					valuesList.add(0,result);
				}else{
					if(operandsList.get(0).equalsIgnoreCase(MINUS) && (operandsList.size() == 1 || operandsList.get(1).equalsIgnoreCase(PLUS) || operandsList.get(1).equalsIgnoreCase(MINUS))){
						double result = valuesList.get(0) - valuesList.get(1);
						operandsList.remove(0);
						valuesList.remove(1);
						valuesList.remove(0);
						valuesList.add(0,result);
					}else{
						if((operandsList.get(0).equalsIgnoreCase(PLUS) || operandsList.get(0).equalsIgnoreCase(MINUS) ) && operandsList.get(1).equalsIgnoreCase(DIV)){
							double result = valuesList.get(1) / valuesList.get(2);
							operandsList.remove(1);
							valuesList.remove(2);
							valuesList.remove(1);
							valuesList.add(1,result);
						}else{
							if( (operandsList.get(0).equalsIgnoreCase(PLUS) || operandsList.get(0).equalsIgnoreCase(MINUS) ) && operandsList.get(1).equalsIgnoreCase(MULT) ){
								double result = valuesList.get(1) * valuesList.get(2);
								operandsList.remove(1);
								valuesList.remove(2);
								valuesList.remove(1);
								valuesList.add(1,result);
							}else{
								if(operandsList.get(0).equalsIgnoreCase(DIV)){
									double result = valuesList.get(0) / valuesList.get(1);
									operandsList.remove(0);
									valuesList.remove(1);
									valuesList.remove(0);
									valuesList.add(0,result);
								}else{
									if(operandsList.get(0).equalsIgnoreCase(MULT)){
										double result = valuesList.get(0) * valuesList.get(1);
										operandsList.remove(0);
										valuesList.remove(1);
										valuesList.remove(0);
										valuesList.add(0,result);
									}else{
										// uNKNOWN OPERATOR, THROW EXCEPTION
										throw new CalculatorException(UNKNOWN_BASE_OPERATION + operandsList.get(0)); 
									}
								}
							}
						}
					}
				}
			}
			return valuesList.get(0);
		}
		
		public static ConcurrentSkipListMap<String, String> mapOperationsTovalues(List<String> valueList, Matcher matcher){
			ConcurrentSkipListMap<String,String> returnMap = new ConcurrentSkipListMap<String,String>();
			int index = 0;
			while(matcher.find()){
				String operation = matcher.group();
				returnMap.put(operation, valueList.get(index++));
				
			}
			return returnMap;
		}
	}
