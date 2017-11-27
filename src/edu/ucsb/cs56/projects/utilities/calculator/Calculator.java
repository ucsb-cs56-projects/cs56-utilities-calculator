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


//MY VARS

	private String entry;
	private int parenCount;

//MY VARS

	//private String left, operator, right;
	//private boolean onRightSide; // true if appending to right side of expression, false if appending to left side
	private boolean displayingResult;
	private JLabelMessageDestination display;
  private JLabelMessageDestination resultDisplay;
	//private final HashMap<String, Callable<Double>> functions; // Hash map of calculator operator to lambda function

    /**
       Constructor
       @param display The JLabelMessageDestination to send the operations
       and results to
     */
	public Calculator(JLabelMessageDestination display,
                    JLabelMessageDestination resultDisplay){
		//left = "";
		//operator = "";
		//right = "";
		//onRightSide = false;
		displayingResult = false;
		this.display = display;
    this.resultDisplay = resultDisplay;
    resultDisplay.append("Hello!");
		refresh();

		operators = new ArrayList<Character>();
		numbers = new ArrayList<Double>();
		currString = "";
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
	
		// Refreshes
		refresh();

		/*

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

		*/

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

/*
		String result = "";
		if (operators == null)
			display.append(currString);
		else if (operators.size() == 0)
			display.append(currString);
		else {
			for (int i = 0; i < operators.size(); i++){
				if (i > 0)
					result += " ";
				result += ("" + numbers.get(i) + " " + operators.get(i));
			}
			if (!(currString.equals("")))
				result += Double.toString(Double.parseDouble(currString));
			display.append(result);
		}

*/

	}

    /**
       Clear out the expression and refresh the display
     */
	public void clear(){
		entry = "";
		parenCount = 0;
		currString = "";
		operators.clear();
		numbers.clear();
		//left = "";
		//operator = "";
		//right = "";
		//onRightSide = false;
		resultDisplay.append("Cleared");
		refresh();
	}

    /**
       Delete the rightmost character in the expression. Called by using
       backspace or clicking the Delete button
     */
	public void delete(){
		
		if (entry.equals(""))
			return;

		if ((entry.substring(entry.length() - 1)).equals(")"))
			parenCount++;
		else if ((entry.substring(entry.length()-1)).equals("("))
			parenCount--;
		entry = entry.substring(0, entry.length() - 1);

		refresh();
	}

/*
		if (!(currString.equals("")))
			currString = currString.substring(0, currString.length() - 1);
                else if (operators.size() > 0){
                        operators.remove(operators.size() - 1);
			currString = Double.toString(numbers.get(numbers.size() - 1));
			numbers.remove(numbers.size() - 1);
		}



		refresh();

	}
*/


    /**
       Operate on the current expression and display the result
     */
	public void operate(){

		displayResult(0.0);

	}

/*
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
*/

	/*
		double result = 0.0;
		try{
			if(left.equals("") || operator.equals("")
			   || right.equals(""))   { return; }
			result = functions.get(operator).call();
			displayResult(result);
		}catch(NumberFormatException nfe){
      clear();
			resultDisplay.append("Error: Number Formatting");
		}catch(Exception e){
      clear();
			resultDisplay.append(e.toString());
		}
	*/

 
 /**
     Displays result by replacing left String
     @param Double result to be displayed
  */
	private void displayResult(double result){
		displayingResult = true;
		//resultDisplay.append(Double.toString(result));
		resultDisplay.append("THIS IS A TEST");
		/*
		if ((int)result == result)
			left = "" + (int)result;
		else
			left = "" + result;
		resultDisplay.append(left);
		onRightSide = false;
		displayingResult = true;
		*/
	}
 
   /*
     This method is for testing use only
   
   public String getLeft() { return left; }

   public String getCurrentSide(){
	   if (onRightSide)
	       return right;
		return left;
   }
	*/
}

