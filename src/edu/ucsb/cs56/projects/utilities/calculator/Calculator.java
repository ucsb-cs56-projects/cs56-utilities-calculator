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

	private ArrayList<Character> operators;
	private ArrayList<Double> numbers;
	private String currString;

//MY VARS

	//private String left, operator, right;
	//private boolean onRightSide; // true if appending to right side of expression, false if appending to left side
	//private boolean displayingResult;
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
		//displayingResult = false;
		this.display = display;
    this.resultDisplay = resultDisplay;
    resultDisplay.append("Hello!");
		refresh();

		operators = new ArrayList<Character>();
		numbers = new ArrayList<Double>();
		currString = "";

		//THIS is code for the hash table, which we're not going to use.
		/*
		functions = new HashMap<String, Callable<Double>>();
        functions.put("+", ()->Double.parseDouble(left)+Double.parseDouble(right));
        functions.put("-", ()->Double.parseDouble(left)-Double.parseDouble(right));
		functions.put("/", ()->Double.parseDouble(left)/Double.parseDouble(right));
		functions.put("*", ()->Double.parseDouble(left)*Double.parseDouble(right));
		functions.put("^", ()->Math.pow(Double.parseDouble(left),Double.parseDouble(right)));
		functions.put("√", ()->Math.pow(Double.parseDouble(right),1/Double.parseDouble(left)));
		*/

		

	}

    /**
       Call this method with a String to have the calculator do some operation
       (i.e. appending a digit to the current number, or appending an operator
       to the expression)
     */
	public void append(String s){
		
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

		/*
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
				displayingResult = false;
			}
		}
		else if (s.equals(".") && getCurrentSide().contains(".")) // Don't allow more than 1 decimal per side
			return;
		else if (displayingResult){
			left = s;
			displayingResult = false;
		} 
		else if(onRightSide)
			right = right + s;
		else
			left = left + s;
		refresh();
		*/

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
				result += ("" + numbers.get(i) + " " + operators.get(i));
			}
			result += currString;
			display.append(result);
		}

		/*
		if(onRightSide)
			display.append(left + " " + operator + " "
				       + right + "|");
		else
			display.append(left + "| " + operator + " " + right);
		*/
	}

    /**
       Clear out the expression and refresh the display
     */
	public void clear(){
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

		if (!(currString.equals("")))
			currString = currString.substring(0, currString.length() - 1);
                else if (operators.size() > 0){
                        operators.remove(operators.size() - 1);
			currString = Double.toString(numbers.get(numbers.size() - 1));
			numbers.remove(numbers.size() - 1);
		}
		refresh();

/*
    if(displayingResult)
      return;
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
*/
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
		clear();
		//displayingResult = true;
		resultDisplay.append(Double.toString(result));

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

