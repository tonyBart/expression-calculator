package com.bartley.calculator.expression.strategy.triple.concrete;

import java.util.NavigableSet;
import java.util.concurrent.ConcurrentSkipListMap;

import com.bartley.calculator.expression.service.util.CalculatorConstants;
import com.bartley.calculator.expression.service.util.Updater;
import com.bartley.calculator.expression.strategy.triple.TripleOperatorStrategy;

public class MapValuesToPositionInStringOperation<W> extends TripleOperatorStrategy<ConcurrentSkipListMap<String, String>,Updater,CalculatorConstants.DIRECTION,W> {
	
	private final String strategy = "MapValuesToPositionInStringOperation";
	
	@Override
	public W doOperation(final ConcurrentSkipListMap<String, String> swapMap,
			final Updater expressionSecuredUpdater, CalculatorConstants.DIRECTION direction) {
		W returnVal = null;
		NavigableSet<String> operationsList = swapMap.keySet();
		if(direction == CalculatorConstants.DIRECTION.DESCENDING){
			operationsList.descendingSet();
		}
		while(!operationsList.isEmpty()){
			String nextOp  = operationsList.first();
				expressionSecuredUpdater.updateExpression(nextOp,"" + swapMap.get(nextOp));
				operationsList = swapMap.keySet();
			}
		return returnVal;
	}

	/**
	 * @return the strategy
	 */
	public String getStrategy() {
		return strategy;
	}

}
