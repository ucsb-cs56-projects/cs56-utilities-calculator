package edu.ucsb.cs56.projects.utilities.calculator;

import java.lang.NumberFormatException;
import java.lang.Math;


/**
   This class represents the portion of the calculator that does all of the computations, and sends it to the screen.
   @author Sam Dowell
 */
class Calculator {

	private String left, operator, right, tempL, tempOp, tempR;
	private boolean onRightSide, isInParenthesis, hasParenthesis; // true if appending to right side of expression, false if appending to left side
	private JLabelMessageDestination display;
    private int locationOfParen; //where is the parenthesis, on the left = 0, on the right = 1;
    /**
       Constructor
       @param display The JLabelMessageDestination to send the operations and results to
     */
	public Calculator(JLabelMessageDestination display){
		left = "";
		operator = "";
		right = "";
		onRightSide = false;
        isInParenthesis = false;
        hasParenthesis = false;
        locationOfParen = 0;
		this.display = display;
		refresh();
	}
    /**
       Call this method with a String to have the calculator do some operation (i.e. appending a digit to the current number, or appending an operator to the expression)
     */
	public void append(String s){
		System.out.println("S = " + s);
        System.out.println(isInParenthesis);
        if(s.equals("Enter")){
			operate();
			return;
		}
        if (isInParenthesis)
        {
            if (s.equals(")"))
            {
                isInParenthesis = false;
                //System.out.println("bool here is: " + isInParenthesis);
                if (onRightSide)
                {
                    right = right + s;
                    //onRightSide = false;
                }
                else
                {
                    left = left + s;
                    onRightSide = true;
                }
            }
            else if (s.equals("Clear"))
                clear();
            else if (s.equals("Delete"))
                delete();
            else if (onRightSide)
                right = right + s;
            else
                left = left + s;
        }
        else
        {
		if(s.equals("Clear"))
			clear();
		else if(s.equals("Delete"))
			delete();
        else if (s.equals("("))
        {
            isInParenthesis = true;
            hasParenthesis = true;
            if (onRightSide)
            {
                right = right + s;
                locationOfParen = 1;
            }
            else
                left = left + s;
        }
        else if (s.equals("cos") && !onRightSide && left.equals(""))
        {
            left = left + "";
            if(operator.equals("") || right.equals("")){
				operator = s;
				onRightSide = true;
            }
        }

        else if (s.equals("sin") && !onRightSide && left.equals(""))
        {
            left = left + "";
            if(operator.equals("") || right.equals("")){
				operator = s;
				onRightSide = true;
            }
        }
        
        else if (s.equals("sqrt") && !onRightSide && left.equals(""))
        {
            left = left + "";
            if(operator.equals("") || right.equals("")){
				operator = s;
				onRightSide = true;
            }
        }
        else if(s.equals("-") && !onRightSide && left.equals(""))
			left = left + s;
		else if(s.equals("-") && onRightSide && !operator.equals("") && right.equals(""))
			right = right + s;
		else if(s.equals("*") || s.equals("+") || s.equals("-") || s.equals("/") || s.equals("^")){
            if(operator.equals("") || right.equals("")){
				operator = s;
				onRightSide = true;
			}
		}
		else if(onRightSide)
			right = right + s;
		else
			left = left + s;
        }
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
		System.out.println("left is: " + left);
        System.out.println("right is: " + right);
        System.out.println("operator is: " + operator);
        left = "";
		operator = "";
		right = "";
		onRightSide = false;
        isInParenthesis = false;
        hasParenthesis = false;
        locationOfParen = 0;
		refresh();
	}
    /**
       Delete the rightmost character in the expression. Called by using backspace or clicking the Delete button
     */
	public void delete(){
		if(onRightSide){
            //System.out.println("i am on the right");
			if(right.equals("")){
				operator = "";
				onRightSide = false;
			}
			else
            {
                char end = right.charAt(right.length() - 1);
                System.out.println("end is " + end);
                if (end == '(')
                {
                    //System.out.println("begin parenthesis");
                    isInParenthesis = false;
                }
                else if (end == ')')
                    isInParenthesis = true;
                right = right.substring(0, right.length() - 1);
            }
		}else{
            //System.out.println("I am not on the right");
			if(!left.equals(""))
            {
                char end  = left.charAt(left.length() - 1);
                System.out.println("end is " + end);
                if (end == '(')
                {
                    //System.out.println("begin parenthesis second case");
                    isInParenthesis = false;
                }
                else if (end == ')')
                    isInParenthesis = true;
				left = left.substring(0,left.length() - 1);
            }
		}
	}
    /**
       Operate on the current expression and display the result
     */
	public void operate(){
		double result = 0.0;
		try{
            if (hasParenthesis)
            {
                if (locationOfParen == 0) //parenthesis on the left
                {
                    
                }
                else  //parenthesis on the left
                {
                    
                }
            }
            else
            {
                if(left.equals(""))
                    {
            if (operator.equals("") || right.equals(""))
                return;
            else
            {
                if (operator.equals("sqrt"))
                {
                    result = Math.sqrt(Double.parseDouble(right));
                    System.out.println(result);
                    clear();
                    left = "" + result;
                    refresh();
                    onRightSide = false;
                }
                
                else if (operator.equals("sin"))
                {
                    result = Math.sin(Math.toRadians(Double.parseDouble(right)));
                    System.out.println(result);
                    clear();
                    left = "" + result;
                    refresh();
                    onRightSide = false;
                }
                
                else if (operator.equals("cos"))
                {
                    result = Math.cos(Math.toRadians(Double.parseDouble(right)));
                    System.out.println(result);
                    clear();
                    left = "" + result;
                    refresh();
                    onRightSide = false;
                }

            }
                
        }
                else
                    {
		if(operator.equals("+")){
			result = Double.parseDouble(left) + Double.parseDouble(right);
			clear();
			left = "" + result;
			refresh();
			onRightSide = false;
		}
		else if (operator.equals("-")){
			result = Double.parseDouble(left) - Double.parseDouble(right);
			clear();
			left = "" + result;
			refresh();
			onRightSide = false;
		}
		else if (operator.equals("*")){
			result = Double.parseDouble(left) * Double.parseDouble(right);
			clear();
			left = "" + result;
			refresh();
			onRightSide = false;
		}
		else if (operator.equals("/")){
			if (Double.parseDouble(right) != 0){
				result = Double.parseDouble(left) / Double.parseDouble(right);
				clear();
				left = "" + result;
				refresh();
				onRightSide = false;
			} else 
				display.append("Error: Divide by zero");
		}
        else if (operator.equals("^"))
        {
            if (Double.parseDouble(right) == 0)
            {
                result = 1.0;
                clear();
                left += "" + result;
                refresh();
                onRightSide = false;
            }
            else if (Double.parseDouble(right) < 0)
            {
                double newLeft = 1 / Double.parseDouble(left);
                result = newLeft;
                for (int i = 0; i < ((-1) *Double.parseDouble(right)) - 1; i++)
                {
                    result = result * result;
                }
                clear();
                left += "" + result;
                refresh();
                onRightSide = false;

            }
            else
            {
                result = Double.parseDouble(left);
                for (int i = 0; i < Double.parseDouble(right) - 1; i++)
                {
                    result *= result;
                }
                clear();
                left += "" + result;
                refresh();
                onRightSide = false;
            }
        }
        
        }
            }
        }
            catch(NumberFormatException nfe){
			display.append("Error: Number Formatting");
		}
	}
}
