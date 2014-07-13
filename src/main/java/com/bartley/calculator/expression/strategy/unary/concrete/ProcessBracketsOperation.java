package com.bartley.calculator.expression.strategy.unary.concrete;

import java.util.Map;
import java.lang.String;

import com.bartley.calculator.expression.exception.CalculatorException;
import com.bartley.calculator.expression.service.util.CalculatorConstants;
import com.bartley.calculator.expression.strategy.unary.UnaryOperatorStrategy;

import static com.bartley.calculator.expression.service.util.CalculatorUtils.*;

public class ProcessBracketsOperation extends UnaryOperatorStrategy<String,String> {

	
	
	@Override
	public String doOperation(String expression) {
		// Check if no brackets exist
				if(expression.indexOf(CalculatorConstants.START_BRACKET) == -1 || (expression.indexOf(CalculatorConstants.END_BRACKET) == -1 )){
					if(expression.indexOf(CalculatorConstants.START_BRACKET) > -1 && expression.indexOf(CalculatorConstants.END_BRACKET) == -1  || 
							expression.indexOf(CalculatorConstants.START_BRACKET) == -1 && expression.indexOf(CalculatorConstants.END_BRACKET) > -1 ){
						throw new CalculatorException(CalculatorConstants.MISSING_BRACKET);
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
	


	
}
