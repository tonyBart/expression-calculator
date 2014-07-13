package com.bartley.calculator.expression.strategy;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.bartley.calculator.expression.strategy.binary.concrete.PowerOperatorTest;
import com.bartley.calculator.expression.strategy.triple.concrete.MapValuesToPositionInStringOperatorTest;
import com.bartley.calculator.expression.strategy.unary.concrete.ACosOperationTest;
import com.bartley.calculator.expression.strategy.unary.concrete.ASinOperatorTest;
import com.bartley.calculator.expression.strategy.unary.concrete.ATanOperationTest;
import com.bartley.calculator.expression.strategy.unary.concrete.CosOperationTest;
import com.bartley.calculator.expression.strategy.unary.concrete.ExponentialM1OperatorTest;
import com.bartley.calculator.expression.strategy.unary.concrete.ExponentialOperatorTest;
import com.bartley.calculator.expression.strategy.unary.concrete.Log10OperatorTest;
import com.bartley.calculator.expression.strategy.unary.concrete.Log1pOperatorTest;
import com.bartley.calculator.expression.strategy.unary.concrete.LogOperatorTest;
import com.bartley.calculator.expression.strategy.unary.concrete.SineOperationTest;
import com.bartley.calculator.expression.strategy.unary.concrete.SquareRootOperatorTest;
import com.bartley.calculator.expression.strategy.unary.concrete.TanOperationTest;

@RunWith(Suite.class)
@SuiteClasses({ ACosOperationTest.class, CosOperationTest.class,
		ExponentialM1OperatorTest.class, ExponentialOperatorTest.class,
		Log10OperatorTest.class, Log1pOperatorTest.class,
		LogOperatorTest.class, MapValuesToPositionInStringOperatorTest.class,
		ASinOperatorTest.class, SineOperationTest.class,
		PowerOperatorTest.class, SquareRootOperatorTest.class,
		ATanOperationTest.class, TanOperationTest.class })
public class AllStrategyTests {

}
