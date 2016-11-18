package edu.ucsb.cs56.projects.utilities.calculator;

import java.lang.NumberFormatException;
import java.util.HashMap;
import java.util.concurrent.Callable;


/**
   This class represents the portion of the calculator that does all of the
   computations, and sends it to the screen.
   @author Sam Dowell
 */
class Calculator {

	private String left, operator, right;
	private boolean onRightSide; // true if appending to right side of expression, false if appending to left side
	private JLabelMessageDestination display;
	private final HashMap<String, Callable<Double>> functions; // Hash map of calculator operator to lambda function

    /**
       Constructor
       @param display The JLabelMessageDestination to send the operations
       and results to
     */
	public Calculator(JLabelMessageDestination display){
		left = "";
		operator = "";
		right = "";
		onRightSide = false;
		this.display = display;
		refresh();

		functions = new HashMap<String, Callable<Double>>();
        functions.put("+", ()->Double.parseDouble(left)+Double.parseDouble(right));
        functions.put("-", ()->Double.parseDouble(left)-Double.parseDouble(right));
		functions.put("/", ()->Double.parseDouble(left)/Double.parseDouble(right));
		functions.put("*", ()->Double.parseDouble(left)*Double.parseDouble(right));
		functions.put("^", ()->Math.pow(Double.parseDouble(left),Double.parseDouble(right)));
		functions.put("√", ()->Math.pow(Double.parseDouble(right),1/Double.parseDouble(left)));
	}

    /**
       Call this method with a String to have the calculator do some operation
       (i.e. appending a digit to the current number, or appending an operator
       to the expression)
     */
	public void append(String s){
		if(s.equals("-") && !onRightSide && left.equals(""))
			left = left + s;
		else if(s.equals("-") && onRightSide
			&& !operator.equals("") && right.equals(""))
			right = right + s; 

		else if(functions.get(s) != null){   // Checks if symbol is in 'functions' HashMap
			if (left.equals("") || left.equals("-"))
				return;
			else if(operator.equals("") || right.equals("")){
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
			display.append(left + " " + operator + " "
				       + right + "|");
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
       Delete the rightmost character in the expression. Called by using
       backspace or clicking the Delete button
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
			if(left.equals("") || operator.equals("")
			   || right.equals(""))   { return; }
			result = functions.get(operator).call();
			displayResult(result);
		}catch(NumberFormatException nfe){
			display.append("Error: Number Formatting");
		}catch(Exception e){
			display.append(e.toString());
		}
	}
 
 /**
     Displays result by replacing left String
     @param Double result to be displayed
  */
	private void displayResult(double result){
		clear();
		if ((int)result == result)
			left = "" + (int)result;
		else
			left = "" + result;
		refresh();
		onRightSide = false;
	}
 
   /**
     This method is for testing use only
   */
   public String getLeft() { return left; }
}
