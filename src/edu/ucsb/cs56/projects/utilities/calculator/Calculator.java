package edu.ucsb.cs56.projects.utilities.calculator;

import java.util.ArrayList;
import java.lang.NumberFormatException;
import java.util.concurrent.Callable;
import java.text.DecimalFormat;
import java.util.Stack;

/**
   This class represents the portion of the calculator that does all of the
   computations, and sends it to the screen.
   @author Sam Dowell
*/
class Calculator {
    private String entry;
    private int parenCount;
    private boolean displayingResult;
    private JLabelMessageDestination display;
    private JLabelMessageDestination resultDisplay;
    
    /**
       Constructor
       @param display The JLabelMessageDestination to send the operations
       and results to
    */
    public Calculator(JLabelMessageDestination display,
		      JLabelMessageDestination resultDisplay){
	displayingResult = false;
	this.display = display;
	this.resultDisplay = resultDisplay;
	resultDisplay.append("Hello!");
	refresh();
	entry = "";
    }
    
    /**
       Call this method with a String to have the calculator do some operation
       (i.e. appending a digit to the current number, or appending an operator
       to the expression)
    */
    public void append(String s){
	
	char d = s.charAt(0);
	
	if (displayingResult){
	    clear();
	    displayingResult = false;
	}
	
	// Checks to see if we have more closed parentheses than open
	if (s.equals(")")){
	    if (parenCount < 1)
		return;
	}
	
	// If the calculator is blank
	if (entry == ""){
	    if ((isOperator(s) && !(s.equals("-"))) || s.equals(")"))
		return;
	    else if (Character.isDigit(d) || s.equals("(") || s.equals("-"))
		{
		    entry += s;
		    if (s.equals("("))
			parenCount++;
		}
	}
	
	// If we're on an operator
	else if (isOperator(entry.substring(entry.length() - 1))) {
	    if (Character.isDigit(d))
		entry += s;
	    else if (s.equals("(")){
		entry += s;
		parenCount++;
	    }
	    else
		return;
	}
	
	// If we're on a number
	else if (Character.isDigit(entry.charAt(entry.length() - 1))) {
	    if (Character.isDigit(d) || isOperator(s) || s.equals("."))
		entry += s;
	    else if (s.equals(")")) {
		entry += s;
		parenCount--;
	    }
	}
	
	// If we're on a decimal place
	else if (entry.charAt(entry.length()-1) == '.'){
	    if (Character.isDigit(d)){
		entry += s;
	    }
	}
	
	// If we're on an open parenthese
	else if (entry.charAt(entry.length()-1) == '('){
	    if (Character.isDigit(d))
		entry += s;
	    if (s.equals("(")) {
		entry += s;
		parenCount++;
	    }
	    else if (s.equals(")")) {
		entry += s;
		parenCount--;
	    }
	    else if (s.equals("-"))
		entry += s;
	}
	
		// If we're on a closed parenthese
	else if (entry.charAt(entry.length()-1) == ')'){
	    if (isOperator(s))
		entry += s;
	    if (s.equals(")")){
		entry += s;
		parenCount--;
	    }
	}
       	refresh();
    }
    
    private boolean isOperator(String s){
	if (s.equals("*") || s.equals("/") || s.equals("+") || s.equals("-") || s.equals("^"))
	    return true;
	else
	    return false;
    }
    
    
    /**
       Refresh the display to update it to the current state of the expression
    */
    public void refresh(){
	display.append(entry);
    }
    
    /**
       Clear out the expression and refresh the display
    */
    public void clear(){
	entry = "";
	parenCount = 0;
	resultDisplay.append("Cleared");
	refresh();
    }
    
    /**
       Delete the rightmost character in the expression. Called by using
       backspace or clicking the Delete button
    */
    public void delete(){
	if (entry.equals("")){
	    clear();
	    return;
	} else if ((entry.substring(entry.length() - 1)).equals(")")) {
	    parenCount++;
	} else if ((entry.substring(entry.length()-1)).equals("(")) {
	    parenCount--;
	}
	entry = entry.substring(0, entry.length() - 1);
	refresh();
    }
    
    /**
       Operate on the current expression and display the result
    */
    public void operate(){
	if(entry.length() == 0)
	    return;
	if(!isOperator(entry.substring(entry.length() - 1))){
	    double result = evaluate(entry);
	    displayResult(result);
	}
    }
    
    public double evaluate(String expression) {
	Stack<Double> values = new Stack<Double>();
        Stack<Character> ops = new Stack<Character>();
	for (int i = 0; i < expression.length(); i++){
	    if(i == 0 && expression.charAt(i) == '-' && expression.length() > 1){
		StringBuffer sbuf = new StringBuffer();
		sbuf.append(expression.charAt(i));
		i++;
		if((expression.charAt(i) >= '0' && expression.charAt(i) <= '9') || expression.charAt(i) == '.'){
		    while(i < expression.length() && ((expression.charAt(i) >= '0' && expression.charAt(i) <= '9') || expression.charAt(i) == '.')){
			sbuf.append(expression.charAt(i));
			i++;
		    }
		    if(i < expression.length()) i--;
		    values.push(Double.parseDouble(sbuf.toString()));
		}
	    } else if((expression.charAt(i) >= '0' && expression.charAt(i) <= '9') || expression.charAt(i) == '.'){
		StringBuffer sbuf = new StringBuffer();
		while(i < expression.length() && ((expression.charAt(i) >= '0' && expression.charAt(i) <= '9') || expression.charAt(i) == '.')){
		    sbuf.append(expression.charAt(i));
		    i++;
		}
		if(i < expression.length()) i--;
		values.push(Double.parseDouble(sbuf.toString()));
	    } else if (expression.charAt(i) == '(') {
                ops.push(expression.charAt(i));
	    } else if (isOperator(expression.substring(i,i+1))){
		while (!ops.empty() && hasPrecedence(expression.charAt(i), ops.peek())){
                  values.push(applyOp(ops.pop(), values.pop(), values.pop()));
		}
                ops.push(expression.charAt(i));
            } else if (expression.charAt(i) == ')'){
                while (ops.peek() != '(')
                  values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                ops.pop();
            }
        }
	while (!ops.empty()){
	    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
	}
	return values.pop();
    }
    
    public boolean hasPrecedence(char op1, char op2){
        if (op2 == '(' || op2 == ')')
            return false;
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
            return false;
        else
            return true;
    }
 
    public double applyOp(char op, double b, double a){
        switch (op){
        case '+':
            return a + b;
        case '-':
            return a - b;
        case '*':
            return a * b;
	case '^':
	    return Math.pow(a,b);
        case '/':
            if (b == 0)
                throw new
                UnsupportedOperationException("Cannot divide by zero");
            return a / b;
        }
        return 0;
    }
    
    /**
       Displays result by replacing left String
       @param Double result to be displayed
    */
    private void displayResult(double result){
	displayingResult = true;
	DecimalFormat decimalformat = new DecimalFormat("0.00000000E0");
	result = Double.valueOf(decimalformat.format(result));
	resultDisplay.append(Double.toString(result));
    }
}

