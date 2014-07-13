package com.bartley.calculator.expression;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.bartley.calculator.expression.CalculatorJbehave;
import com.bartley.calculator.expression.context.AllOperationContextTests;
import com.bartley.calculator.expression.service.OperationServiceTest;
import com.bartley.calculator.expression.service.util.AllUtilsTests;
import com.bartley.calculator.expression.strategy.AllStrategyTests;
import com.bartley.calculator.expression.worker.AllWorkerTests;

@RunWith(Suite.class)
@SuiteClasses({ CalculatorImplTest.class,AllUtilsTests.class, AllWorkerTests.class, OperationServiceTest.class, 
	AllOperationContextTests.class,AllStrategyTests.class,CalculatorJbehave.class})
public class AllTests {

}
