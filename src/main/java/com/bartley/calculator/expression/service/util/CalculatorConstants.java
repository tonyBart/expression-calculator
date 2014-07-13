/**
 * 
 */
package com.bartley.calculator.expression.service.util;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author Tony
 *
 */
public class CalculatorConstants {

	// NOTE: NEW UNARY OPERATORS MUST BE ADDED TO THIS ENUM
	public static enum UNARY_OPERATORS{
		COS,ACOS,SIN,ASIN,TAN,ATAN,EXP,
		EXPM1,LOG10,LOG,LOG1P, SQRT	}

	// NOTE: NEW BINARY OPERATORS MUST BE ADDED TO THIS ENUM
	public static enum BINARY_OPERATORS{
		POW
	}

	public  static enum DIRECTION{ DESCENDING,ASCENDING}

	static final Set<String> UNARY_SET = new HashSet<String>();
	static final Set<String> BINARY_SET = new HashSet<String>();
	public  static final String PLUS = "+";
	public  static final String DIV = "/";
	public  static final String MINUS = "-";
	public  static final String MULT = "*";
	// Pattern for number
	static final String numberString = "(\\d+\\.?\\d*)";
	public static final Pattern numberPattern = Pattern.compile(numberString);
	// pattern for unary context
	static final String unaryContextString = "(([a-zA-Z]+)\\s*\\(\\s*([-\\+]?\\s*\\d+\\.?\\d*)\\s*\\))";
	public static final Pattern unaryContextPattern = Pattern.compile(unaryContextString);
	// Pattern for the base operations: /,*,+ and -
	static final String baseOperatorString = "([//+//*////-])";
	public static final Pattern baseOperatorPattern = Pattern.compile(baseOperatorString);
	// Pattern for bracket
	static final String bracketOperatorString = "";  //"([\\+\\*/\\)\\n\\-]|\\s*(\\()+" + "[(\\d+\\.?\\d*)]" + "[\\n\\d](?(?=)\\)+))";
	public static final Pattern bracketOperatorPattern = Pattern.compile(bracketOperatorString);
	// Pattern for binary context
	static final String binaryContextString =   "(([a-zA-Z]+)\\s*\\(\\s*([-\\+]?\\s*\\d+\\.?\\d*),([-\\+]?\\s*\\d+\\.?\\d*)\\))";
	public static final Pattern binaryContextPattern = Pattern.compile(binaryContextString);
	// Patterns for: ++,--,+-  & -+ 
	static final String doubleMinusString ="-\\s*-";
	public static final Pattern doubleMinusPattern = Pattern.compile(doubleMinusString);
	static final String doublePlusString ="\\+\\s*\\+";
	public static final Pattern doublePlusPattern = Pattern.compile(doublePlusString);
	static final String plusMinusString ="\\+\\s*-";
	public static final Pattern plusMinusPattern = Pattern.compile(plusMinusString);
	static final String minusPlusString ="-\\s*\\+";
	public static final Pattern minusPlusPattern = Pattern.compile(minusPlusString);
	public static final String START_BRACKET ="(";
	public static final String END_BRACKET =")";
	public static final String MISSING_BRACKET = "Odd number of brackets,please check your equation; cause: ";
	public static final String MISSING_END_BRACKET = "Missing end bracket: ')'";
	public static final String MISSING_START_BRACKET = "Missing start bracket: '('";
	static final String UNRECOGNIZED_BINARY_OPERATOR = "Unrecognised BinaryOperator: ";
	static final String UNRECOGNIZED_UNARY_OPERATOR  = "Unrecognised UnaryOperator: ";
	static final String FORGOT_TO_ADD_THE_OPERATION = " You forgot to add the operation";
	static final String UNKNOWN_BASE_OPERATION = "Unknown BaseOperation: ";
	static{
		CalculatorConstants.UNARY_OPERATORS[] tempArray = CalculatorConstants.UNARY_OPERATORS.values();
		for(int i=0; i < tempArray.length; i++){
			CalculatorConstants.UNARY_SET.add(tempArray[i].toString());
		}
		
		CalculatorConstants.BINARY_OPERATORS[] tempArray2 = CalculatorConstants.BINARY_OPERATORS.values();
		for(int i=0; i < tempArray2.length; i++){
			CalculatorConstants.BINARY_SET.add(tempArray2[i].toString());
		}
		
	}

}
