package edu.ucsb.cs56.projects.utilities.calculator;
import java.util.ArrayList;
import java.lang.NumberFormatException;
import java.util.concurrent.Callable;
import java.text.DecimalFormat;
import java.util.Stack;

/**
* This class represents the portion of the calculator that does all of the
* computations, and sends it to the screen.
* @author Sam Dowell
*/
class Calculator {
	private String entry;
	private int parenCount;
	private boolean displayingResult;
	private JLabelMessageDestination display;
	private JLabelMessageDestination resultDisplay;
	private double result;
	static final double negative = -1.0;
	static final char subtraction = '-';
	static final char multiply = '*';
	static final char decimal = '.';
	static final char openParenthesis = '(';
	static final char closedParenthesis = ')';
    
	/**
	* Constructor
	* @param display The JLabelMessageDestination to send the operations
	* and results to
	*/
	public Calculator(JLabelMessageDestination display, JLabelMessageDestination resultDisplay) {
		displayingResult = false;
		this.display = display;
		this.resultDisplay = resultDisplay;
		resultDisplay.append("Hello!");
		refresh();
		entry = "";
		result = 0.0;
	}
    
	/**
	* Call this method with a String to have the calculator do some operation
	* (i.e. appending a digit to the current number, or appending an operator
	* to the expression)
	*/
	public void append(String s) {
		char pushedButton = s.charAt(0);
		if (displayingResult) {
			clear();
			displayingResult = false;
		}
	
		// Checks to see if we have more closed parentheses than open
		if (s.equals(")")) {
			if (parenCount < 1) {
				return;
			}
		}
	
		// If the calculator is blank
		if (entry == "") {
			if ((isOperator(s) && !(s.equals("-"))) || s.equals(")")) {
				return;
			}
			else if (Character.isDigit(pushedButton) || s.equals("(") || s.equals("-") || s.equals(".")) {
				entry += s;
				if (s.equals("(")) {
					parenCount++;
				}
			}
		}
	
		// If we're on an operator
		else if (isOperator(entry.substring(entry.length() - 1))) {
			if (Character.isDigit(pushedButton)) {
				entry += s;
			}
			else if (s.equals(".")) {
				entry += s;
			}
			else if (s.equals("(")) {
				entry += s;
				parenCount++;
			}
			else if (s.equals("-") && (!isOperator(entry.substring(entry.length() - 2, entry.length() - 1)))) {
				entry += s;
			}
			else {
				return;
			}
		}
	
		// If we're on a number
		else if (Character.isDigit(entry.charAt(entry.length() - 1))) {
			if (Character.isDigit(pushedButton) || isOperator(s)) {
				entry += s;
			}
			else if (s.equals("(")) {
				entry += s;
				parenCount++;
			}
			else if (s.equals(".")) {
				if (hasDecimal(entry) == false) {
					entry += s;
				}
				else {
					return;
				}
			}
			else if (s.equals(")")) {
				entry += s;
				parenCount--;
			}
		}
	
		// If we're on a decimal place
		else if (entry.charAt(entry.length() - 1) == decimal) {
			if (Character.isDigit(pushedButton)) {
				entry += s;
			}
			else if (entry.length() <= 1) {
				return;
			}
			else if (Character.isDigit(entry.charAt(entry.length() -2 ))) {	
				if (isOperator(s)) {
					entry += s;
				}
				else if (s.equals(")")) {
					entry += s;
					parenCount--;
				}
	    		}
		}
	
	
		// If we're on an open parenthesis
		else if (entry.charAt(entry.length() - 1) == openParenthesis) {
			if (Character.isDigit(pushedButton)) {
				entry += s;
			}
			if (s.equals("(")) {
				entry += s;
				parenCount++;
			}
			else if (s.equals(")")) {
				entry += s;
				parenCount--;
			}
			else if (s.equals("-")) {
				entry += s;
			}
			else if (s.equals(".")) {
				entry += s;
			}
		}
	
		// If we're on a closed parenthesis
		else if (entry.charAt(entry.length() - 1) == closedParenthesis) {
			if (isOperator(s)) {
				entry += s;
			}
			if (s.equals(")")) {
				entry += s;
				parenCount--;
			}
			if (Character.isDigit(pushedButton)) {
				entry += s;
			}
			if (s.equals("(")) {
				entry += s;
				parenCount++;
			}
		}
		refresh();
	}

	private boolean hasDecimal(String val) {
		for (int i = val.length() - 1; i >= 0; i--) {
			if (val.charAt(i) == '.') {
				return true;
			}
			else if (isOperator(Character.toString(val.charAt(i)))) {
				return false;
			}
		}
		return false;
	}


	/**
	* Checks if a character is an operator.
	*/
	private boolean isOperator(String s) {
		if (s.equals("*") || s.equals("/") || s.equals("+") || s.equals("-") || s.equals("^")) {
			return true;
		}
		else {
			return false;
		}
	}
    
    
	/**
	* Refresh the display to update it to the current state of the expression
	*/
	public void refresh() {
		display.append(entry);
	}
    
	/**
	* Clear out the expression and refresh the display
	*/
	public void clear() {
		entry = "";
		parenCount = 0;
		resultDisplay.append("Cleared");
		refresh();
	}
    
	/**
	* Delete the rightmost character in the expression. 
	* (Called by using backspace or clicking the Delete button)
	*/
	public void delete() {
		if (entry.equals("")) {
			clear();
			return;
		} 
		else if ((entry.substring(entry.length() - 1)).equals(")")) {
			parenCount++;
		} 
		else if ((entry.substring(entry.length() - 1)).equals("(")) {
			parenCount--;
		}
		entry = entry.substring(0, entry.length() - 1);
		refresh();
	}
    
	/**
	* Operate on the current expression and display the result
	*/
	public void operate() {
		if (entry.length() == 0) {
			return;
		}
		if (!isOperator(entry.substring(entry.length() - 1))) {
			result = evaluate(entry);
			displayResult(result);
		}
	}

	/**
	* Evaluates a String arithmetic expression and returns result
	* Uses the "Shunting Yard Algorithm"
	*/
	public double evaluate(String expression) {
		Stack<Double> values = new Stack<Double>();
		Stack<Character> ops = new Stack<Character>();
		for (int i = 0; i < expression.length(); i++) {
			char curr = expression.charAt(i);
			StringBuffer sbuf = new StringBuffer();

			//If we're on the first index and if there is a negative
			if (i == 0 && curr == subtraction && expression.length() > 1) {
				if (isParenthesisAfterNeg(expression, curr, i)) {
					distributeNeg(values, ops);
				}
				else {
					sbuf.append(curr);
					i++;
					curr = expression.charAt(i);
					if (isNumberOrDecimal(curr)) {
						i = addNumberGetIndex(expression, curr, i, sbuf, values);
					}
				}
			}

			//If we're on a number
			else if (isNumberOrDecimal(curr)) {
				i = addNumberGetIndex(expression, curr, i, sbuf, values);
			}

			//If we're on an open parenthesis	
			else if (curr == openParenthesis) {
				checkForValueBeforeParenthesis(expression, curr, i, ops);
				if (expression.charAt(i + 1) == subtraction && expression.charAt(i + 2) == openParenthesis) {
					ops.push(curr);
					distributeNeg(values, ops);
					i++;
				}
				else {
					ops.push(curr);
					i = checkNegValueAfterOpenParenthesis(expression, curr, i, sbuf, values);
				}
			}

			//If we're on an operator
			else if (isOperator(expression.substring(i, i + 1))) {
				while (!ops.empty() && hasPrecedence(curr, ops.peek())) {
					values.push(applyOp(ops.pop(), values.pop(), values.pop()));
				}
				if (isParenthesisAfterNeg(expression, curr, i)) {
					negBeforeOpenParenthesis(expression, curr, i, values, ops);
				}
				else {
					ops.push(curr);
					i++;
					curr = expression.charAt(i);
					if (curr == subtraction) {
						i = negAfterOperator(expression, curr, i, sbuf, values, ops);
					}
					else {
						i--;
					}
				}
			}

			//If we're on a closed parenthesis
			else if (curr == closedParenthesis) {
				operateParentheses(values, ops);
				checkAfterClosedParenthesis(expression, curr, i, ops);
			}
		}
		return emptyStacks(values, ops);
	}

	/**
	* Checks if the current character is negative and if the next character is a parenthesis 
	*/
	public boolean isParenthesisAfterNeg(String expression, char curr, int i) {
		if (curr == '-' && expression.charAt(i + 1) == '(') {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	* Checks if the current character in expression is a number or decimal
	*/
	public boolean isNumberOrDecimal(char curr) {
		if ((curr >= '0' && curr <= '9') || curr == '.') {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	* Multiplies a -1.0 onto the stacks
	*/
	public void distributeNeg(Stack<Double> v, Stack<Character> o) {
		v.push(negative);
		o.push(multiply);
	}

	/**
	* Adds the full value onto the values stack and returns the new index after
	*/
	public int addNumberGetIndex(String expression, char curr, int i, StringBuffer sbuf, Stack<Double> values) {
		while (i < expression.length() && isNumberOrDecimal(curr)) {
			sbuf.append(curr);
			i++;
			if (i < expression.length()) {
				curr = expression.charAt(i);
			}
		}
		if (i < expression.length()) {
			i--;
		}
		values.push(Double.parseDouble(sbuf.toString()));
		return i;
	}
	
	/**
	* Empties all the stacks and returns the last value from the values stack
	*/
	public double emptyStacks(Stack<Double> values, Stack<Character> ops) {
		while (!ops.empty()) {
			values.push(applyOp(ops.pop(), values.pop(), values.pop()));
		}
		return values.pop();
	}

	/**
	* Operates everything inside of the parentheses
	*/
	public void operateParentheses(Stack<Double> values, Stack<Character> ops) {
		while (ops.peek() != '(') {
			values.push(applyOp(ops.pop(), values.pop(), values.pop()));
		}
		ops.pop();
	}

	/**
	* Pushes a multiplication operator if there is a value next to an open parenthesis
	*/
	public void checkForValueBeforeParenthesis(String expression, char curr, int i, Stack<Character> ops) {
		if (i != 0) {
			i--;
			curr = expression.charAt(i);
			if (curr >= '0' && curr <= '9') {
				ops.push(multiply);
			}
		}
	}

	/**
	* Checks if there is a negative value right after an open parenthesis and returns the new index after
	*/
	public int checkNegValueAfterOpenParenthesis(String expression, char curr, int i, StringBuffer sbuf, Stack<Double> values) {
		i++;
		curr = expression.charAt(i);
		if (curr == '-') {
			sbuf.append(curr);
			i++;
			curr = expression.charAt(i);
			if (isNumberOrDecimal(curr)) {
				i = addNumberGetIndex(expression, curr, i, sbuf, values);
			}
		}
		else {
			i--;
		}
		return i;
	}

	/**
	* Inserts multiplication if there is a number or open parenthesis after closed parenthesis
	*/
	public void checkAfterClosedParenthesis(String expression, char curr, int i, Stack<Character> ops) {
		if (i < expression.length() - 1) {
			i++;
			curr = expression.charAt(i);
			if (isNumberOrDecimal(curr)) {
				ops.push(multiply);
			}
			if (curr == '(') {
				ops.push(multiply);
			}
			i--;
		}
	}

	/**
	* Determines whether to treat a negative as an operator or to multiply it
	*/
	public void negBeforeOpenParenthesis(String expression, char curr, int i, Stack<Double> values, Stack<Character> ops) {
		if ((i > 0) && (expression.charAt(i - 1) >= '0' && expression.charAt(i - 1) <= '9')) {
			ops.push(curr);
		}
		else {
			distributeNeg(values, ops);
		}
	}

	/**
	* Determines whether to multiply the negative or treat it like a negative value and returns the new index after
	*/
	public int negAfterOperator(String expression, char curr, int i, StringBuffer sbuf, Stack<Double> values, Stack<Character> ops) {
		if (expression.charAt(i + 1) == '(') {
			distributeNeg(values, ops);
		}
		else {
			sbuf.append(curr);
			i++;
			curr = expression.charAt(i);
			if (isNumberOrDecimal(curr)) {
				i = addNumberGetIndex(expression, curr, i, sbuf, values);
			}
		}
		return i;
	}

	/**
	* Determines if Operator 1 has precedence over Operator 2
	*/
	public boolean hasPrecedence(char op1, char op2) {
		if (op2 == '(' || op2 == ')') {
			return false;
		}
		if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
			return false;
        	}
		else {
			return true;
		}
	}

	/**
	* Returns the result of an operator applied to two operands
	*/
	public double applyOp(char op, double b, double a) {
		switch (op){
		case '+':
			return a + b;
		case '-':
			return a - b;
		case '*':
			return a * b;
		case '^':
			return Math.pow(a, b);
		case '/':
			if (b == 0) {
				throw new UnsupportedOperationException("Cannot divide by zero");
			}
			return a / b;
		}
		return 0;
	}

	/**
	* Used to evaluate an expression in the form array of strings
	*/
	public String getAnswer() {
		String answer = Double.toString(result);
		return answer;
	}
    
	/**
	* Displays result by replacing left String
	* @param Double result to be displayed
	*/
	private void displayResult(double result) {
		displayingResult = true;
		DecimalFormat decimalformat = new DecimalFormat("0.00000000E0");
		result = Double.valueOf(decimalformat.format(result));
		resultDisplay.append(Double.toString(result));
	}
}

