package com.bartley.calculator.expression;

import static org.junit.Assert.assertTrue;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.bartley.calculator.expression.Calculator;
import com.bartley.calculator.expression.CalculatorImpl;
import com.bartley.calculator.expression.exception.CalculatorException;

public class CalculatorSteps {
	Calculator calculator = new CalculatorImpl();
	double returnValue;
	String errorMessage;
	@Given("An instance of the CalculatorImpl have been correctly configured")
	public void correctlyConfigSys(){
		calculator = new CalculatorImpl();
	}
	
	@When("Person inputs $stringExpression which is evaluated")
	public void personInputsStringExpression(String expression){
		try{
			returnValue = calculator.calculate(expression);
		}catch(CalculatorException calEx){
			errorMessage = calEx.getMessage();
		}
	}
	
	@Then("Then the response should be &response")
	public void theResponseShouldBe(double response) {
		assertTrue(" The expected value is incorrect ",response == returnValue);
	}
	
	@Then("Then the response should be &errorResponse")
	public void theResponseShouldBe(String errorResponse) {
		assertTrue(" The expected error messaage is incorrect ",this.errorMessage.contains("Odd number of brackets,please check your equation; cause: "));
	}
	
}
