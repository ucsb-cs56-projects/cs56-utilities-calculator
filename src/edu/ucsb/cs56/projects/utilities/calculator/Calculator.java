package edu.ucsb.cs56.projects.utilities.calculator;

import java.lang.NumberFormatException;


/**
   This class represents the portion of the calculator that does all of the computations, and sends it to the screen.
   @author Sam Dowell
 */
class Calculator {

	private String left, operator, right;
	private boolean onRightSide; // true if appending to right side of expression, false if appending to left side
	private JLabelMessageDestination display;
    /**
       Constructor
       @param display The JLabelMessageDestination to send the operations and results to
     */
	public Calculator(JLabelMessageDestination display){
		left = "";
		operator = "";
		right = "";
		onRightSide = false;
		this.display = display;
		refresh();
	}
    /**
       Call this method with a String to have the calculator do some operation (i.e. appending a digit to the current number, or appending an operator to the expression)
     */
	public void append(String s){
		if(s.equals("-") && !onRightSide && left.equals(""))
			left = left + s;
		else if(s.equals("-") && onRightSide && !operator.equals("") && right.equals(""))
			right = right + s; 
		else if(s.equals("*") || s.equals("+") || s.equals("-") || s.equals("/")){
			if(operator.equals("") || right.equals("")){
				operator = s;
				onRightSide = true;
			}
		}
		else if(onRightSide)
			right = right + s;
		else
			left = left + s;
		refresh();

	}
    /**
       Refresh the display to update it to the current state of the expression
     */
	public void refresh(){
		if(onRightSide)
			display.append(left + " " + operator + " " + right + "|");
		else
			display.append(left + "| " + operator + " " + right);

	}
    /**
       Clear out the expression and refresh the display
     */
	public void clear(){
		left = "";
		operator = "";
		right = "";
		onRightSide = false;
		refresh();
	}
    /**
       Delete the rightmost character in the expression. Called by using backspace or clicking the Delete button
     */
	public void delete(){
		if(onRightSide){
			if(right.equals("")){
				operator = "";
				onRightSide = false;
			}
			else
				right = right.substring(0, right.length() - 1);
		}else{
			if(!left.equals(""))
				left = left.substring(0,left.length() - 1);
		}
		refresh();
	}
    /**
       Operate on the current expression and display the result
     */
	public void operate(){
		double result = 0.0;
		try{
		if(left.equals("") || operator.equals("") || right.equals("")){ return; }
		if(operator.equals("+")){
			result = Double.parseDouble(left) + Double.parseDouble(right);
			displayResult(result);
		}
		else if (operator.equals("-")){
			result = Double.parseDouble(left) - Double.parseDouble(right);
			displayResult(result);
		}
		else if (operator.equals("*")){
			result = Double.parseDouble(left) * Double.parseDouble(right);
			displayResult(result);
		}
		else if (operator.equals("/")){
			if (Double.parseDouble(right) != 0){
				result = Double.parseDouble(left) / Double.parseDouble(right);
				displayResult(result);
			} else 
				display.append("Error: Divide by zero");
		}
		}catch(NumberFormatException nfe){
			display.append("Error: Number Formatting");
		}
	}

	private void displayResult(double result){
		clear();
		left = "" + result;
		refresh();
		onRightSide = false;
	}
}
