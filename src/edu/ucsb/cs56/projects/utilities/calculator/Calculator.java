package edu.ucsb.cs56.projects.utilities.calculator;

import java.util.ArrayList;
import java.lang.NumberFormatException;
import java.util.HashMap;
import java.util.concurrent.Callable;


/**
   This class represents the portion of the calculator that does all of the
   computations, and sends it to the screen.
   @author Sam Dowell
 */
class Calculator {

    private ArrayList<Character> operators;
    private ArrayList<Double> numbers;
    private String currString;
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
	
	operators = new ArrayList<Character>();
	numbers = new ArrayList<Double>();
	currString = "";
    }

    /**
       Call this method with a String to have the calculator do some operation
       (i.e. appending a digit to the current number, or appending an operator
       to the expression)
    */
    public void append(String s){
	
	if (displayingResult){
	    clear();
	    displayingResult = false;
	}
	
	// If s is a decimal point inside of a number or is a number itself
	if ((!(currString.equals("")) && s.equals(".")) || Character.isDigit(s.charAt(0)))
	    currString += s;
	
	// If the string is not empty (meaning it's not following a symbol)
	else if (!(currString.equals("")))
	    {
		switch (s) {
		case "+":
		case "-":
		case "*":
		case "/":
		case "^":
		    double val;
		if ((currString.substring(0,1)).equals("√"))
		    val = Math.sqrt(Double.parseDouble(currString.substring(1,currString.length())));
		else
		    val = Double.parseDouble(currString);
		numbers.add(val);
		operators.add(s.charAt(0));
		currString = "";
		break;
		default:
		}
	    }
	
	// If the user is signifying a negative number
	else if ((s.charAt(0) == '-') || (s.charAt(0) == '√'))
	    currString += s;
	
	refresh();
    }

    /**
       Refresh the display to update it to the current state of the expression
    */
    public void refresh(){
	
	String result = "";
	if (operators == null)
	    display.append(currString);
	else if (operators.size() == 0)
	    display.append(currString);
	else {
	    for (int i = 0; i < operators.size(); i++){
		if (i > 0)
		    result += " ";
		    if ((numbers.get(i) % 1)== 0)
			result += (int)((numbers.get(i)).intValue());
		    else
			result += Double.toString(numbers.get(i));
		    result += (" " + operators.get(i));
	    }
	    if (!(currString.equals("")))
		result += currString;
	    display.append(result);
	}
    }
    
    /**
       Clear out the expression and refresh the display
    */
    public void clear(){
	currString = "";
	operators.clear();
	numbers.clear();
	resultDisplay.append("Cleared");
	refresh();
    }
    
    /**
       Delete the rightmost character in the expression. Called by using
       backspace or clicking the Delete button
    */
    public void delete(){
	
	if (!(currString.equals("")))
	    currString = currString.substring(0, currString.length() - 1);
	else if (operators.size() > 0){
	    operators.remove(operators.size() - 1);
	    double d = numbers.get(numbers.size() - 1);
	    if (d%1 == 0)
		currString += (int)d;
	    else
		currString += Double.toString(d);
	    numbers.remove(numbers.size() - 1);
	}
	refresh();	
    }

    /**
       Operate on the current expression and display the result
    */
    public void operate(){
	if (currString.equals(""))
	    return;
	
	numbers.add(Double.parseDouble(currString));
	if (operators.size() == 0)
	    displayResult(numbers.get(0));
	else {
	    double result = numbers.get(0);
	    numbers.remove(0);
	    for (char c : operators){
		double d2 = numbers.get(0);
		numbers.remove(0);
		switch (c){
		case '+':
		    result += d2;
		    break;
		case '-':
		    result -= d2;
		    break;
		case '*':
		    result *= d2;
		    break;
		case '/':
		    result /= d2;
		    break;
		case '^':
		    result = Math.pow(result, d2);
		    break;
		default:
		}
	    }
	    displayResult(result);
	}
    }		
    
    /**
       Displays result by replacing left String
       @param Double result to be displayed
    */
    private void displayResult(double result){
	displayingResult = true;
	//displayingResult = true;
	resultDisplay.append(Double.toString(result));
    }
}

