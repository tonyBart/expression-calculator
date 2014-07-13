Scenario:  A Person wishes to evluate  "32.5 + pow(2.0,2.0) + 64 + pow(3.0,3.0)" 
Given("An instance of the CalculatorImpl have been correctly configured")
When Person inputs "32.5 + pow(2.0,2.0) + 64 + pow(3.0,3.0)" which is evaluated
Then the response should be 127.5
 
Scenario:  A Person wishes to evluate  "32.5 * cos(90) + pow(2.0,2.0) - cos(0.0) + 64 + pow(3.0,3.0) + 24*3/12"
 
Given("An instance of the CalculatorImpl have been correctly configured")
When Person inputs "32.5 * cos(0.0) + pow(2.0,2.0) - cos(90.0) + 64 + pow(3.0,3.0) + 24*3/12" which is evaluated
Then the response should be 133.5

Scenario:  A Person wishes to check that if he gives an expression with a missing bracket, an error message is returned"
 
Given("An instance of the CalculatorImpl have been correctly configured")
When Person inputs "32.5 * (cos(0.0) + pow(2.0,2.0)) -( cos(90.0) + 64) + pow(3.0,3.0) + 24*3/12)" which is evaluated
Then the response should be "Odd number of brackets,please check your equation; cause: "
 