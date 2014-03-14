package edu.ucsb.cs56.projects.utilities.calculator;

import java.lang.NumberFormatException;
import java.lang.Math;
import java.util.ArrayList;


/**
   This class represents the portion of the calculator that does all of the computations, and sends it to the screen.
   @author Sam Dowell
 */
class Calculator {

	private String left, operator, right, tempL, tempOp, tempR;
	private boolean onRightSide, isInParenthesis, hasParenthesis, parenRight; // true if appending to right side of expression, false if appending to left side
	private JLabelMessageDestination display;
    private int locationOfParen, counter;//where is the parenthesis, on the left = 0, on the right = 1;
    private ArrayList<String> resultString;
    /**
       Constructor
       @param display The JLabelMessageDestination to send the operations and results to
     */
	public Calculator(JLabelMessageDestination display){
		left = "";
		operator = "";
		right = "";
        tempL = "";
        tempOp = "";
        tempR = "";
        resultString = new ArrayList<String>();
		onRightSide = false;
        isInParenthesis = false;
        hasParenthesis = false;
        parenRight = false;
        locationOfParen = 0;
        counter = 0;
		this.display = display;
		refresh();
	}
    /**
       Call this method with a String to have the calculator do some operation (i.e. appending a digit to the current number, or appending an operator to the expression)
     */
	public void append(String s){
		/*System.out.println("S = " + s);
        System.out.println(isInParenthesis);*/
        if(s.equals("Enter")){
			operate();
			return;
		}
        if (s.equals("<--"))
        {
            counter -= 1;
            goBack();
            return;
        }
        if (s.equals("-->"))
        {
            counter += 1;
            goNext();
            return;
        }
        if (isInParenthesis)
        {
            /*if (s.equals("Clear"))
                clear();
            else if (s.equals("Delete"))
                delete();*/
            if (onRightSide && !s.equals("Delete"))
                right = right + s;
            else if (!onRightSide && !s.equals("Delete"))
                left = left + s;
            if (s.equals(")"))
            {
                isInParenthesis = false;
                //System.out.println("bool here is: " + isInParenthesis);
                /*if (onRightSide)
                {
                    right = right + s;
                    //onRightSide = false;
                }*/
                if (!onRightSide)
                    onRightSide = true;
            }
            else if (s.equals("Clear"))
                clear();
            else if (s.equals("Delete"))
                delete();
            else if (s.equals("cos") && !parenRight && tempL.equals(""))
            {
                tempL = tempL + "";
                if(tempOp.equals("") || tempR.equals("")){
                    tempOp = s;
                    parenRight = true;
                }
            }
            else if (s.equals("sin") && !parenRight && tempL.equals(""))
            {
                tempL = tempL + "";
                if(tempOp.equals("") || tempR.equals("")){
                    tempOp = s;
                    parenRight = true;
                }
            }
            else if (s.equals("sqrt") && !parenRight && tempL.equals(""))
            {
                tempL = tempL + "";
                if(tempOp.equals("") || tempR.equals("")){
                    tempOp = s;
                    parenRight = true;
                }
            }
            else if(s.equals("-") && !parenRight && tempL.equals(""))
                tempL = tempL + s;
            else if(s.equals("-") && parenRight && !tempOp.equals("") && tempR.equals(""))
                tempR = tempR + s;
            else if(s.equals("*") || s.equals("+") || s.equals("-") || s.equals("/") || s.equals("^")){
                if(tempOp.equals("") || tempR.equals("")){
                    tempOp = s;
                    parenRight = true;
                }
            }
            else if (parenRight)
                tempR = tempR + s;
            else
                tempL = tempL + s;
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
	
    public void goBack()
    {
        System.out.println(counter);
        if (counter >= resultString.size())
        {
            counter = resultString.size();
            clear();
            refresh();
            display.append(resultString.get(counter - 1));
            return;
        }
        if (counter < 0)
        {
            counter = 0;
            clear();
            refresh();
            display.append(resultString.get(counter));
            return;
        }
        if (resultString.size() < 1)
        {
            counter = 0;
            return;
        }
        else
        {
            clear();
            refresh();
            display.append(resultString.get(counter));
        }
    }
    
    public void goNext()
    {
        System.out.println(counter);
        if (counter >= resultString.size())
        {
            counter = resultString.size();
            clear();
            refresh();
            display.append(resultString.get(counter - 1));
            return;
        }
        if (counter < 0)
        {
            counter = 0;
            clear();
            refresh();
            display.append(resultString.get(counter));
            return;
        }
        if (resultString.size() < 1)
        {
            counter = 0;
            return;
        }
        else
        {
            clear();
            refresh();
            display.append(resultString.get(counter));
        }
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
        tempL = "";
        tempOp = "";
        tempR = "";
		onRightSide = false;
        isInParenthesis = false;
        hasParenthesis = false;
        parenRight = false;
        locationOfParen = 0;
		refresh();
	}
    /**
       Delete the rightmost character in the expression. Called by using backspace or clicking the Delete button
     */
	public void delete(){
		boolean isTempOp = false;
        int opLength = 0;
        if(onRightSide){
            if(right.equals("")){
				operator = "";
				onRightSide = false;
			}
			else
            {
                char end = right.charAt(right.length() - 1);
                if (isInParenthesis)
                {
                    if (parenRight)
                    {
                        if (tempR.equals(""))
                        {
                            opLength = tempOp.length();
                            tempOp = "";
                            isTempOp = true;
                            parenRight = false;
                        }
                        else
                            tempR = tempR.substring(0, tempR.length() - 1);
                    }
                    else
                    {
                        if (!tempL.equals(""))
                            tempL = tempL.substring(0, tempL.length() - 1);
                    }
                }
                if (end == '(')
                {
                    isInParenthesis = false;
                    hasParenthesis = false;
                }
                else if (end == ')')
                    isInParenthesis = true;
                //System.out.println("subString is: " + right.substring(0, right.length() - 1));
                if (isTempOp)
                    right = right.substring(0,right.length() - opLength);
                else
                    right = right.substring(0, right.length() - 1);
            }
        }
        else{
            if(!left.equals(""))
            {
                if (isInParenthesis)
                {
                    if (parenRight)
                    {
                        if (tempR.equals(""))
                        {
                            opLength = tempOp.length();
                            tempOp = "";
                            isTempOp = true;
                            parenRight = false;
                        }
                        else
                            tempR = tempR.substring(0, tempR.length() - 1);
                    }
                    else
                    {
                        if (!tempL.equals(""))
                            tempL = tempL.substring(0, tempL.length() - 1);
                    }
                }
                char end  = left.charAt(left.length() - 1);
                if (end == '(')
                {
                    isInParenthesis = false;
                    hasParenthesis = false;
                }
                else if (end == ')')
                    isInParenthesis = true;
                if (isTempOp)
                    left = left.substring(0,left.length() - opLength);
                else
                    left = left.substring(0,left.length() - 1);
            }
		}
	}
    
    public void calculateParenthesis()
    {
        double result = 0.0;
        try
        {
            if(tempL.equals(""))
            {
                if (tempOp.equals("") || tempR.equals(""))
                    return;
                else
                {
                    if (tempOp.equals("sqrt"))
                        result = Math.sqrt(Double.parseDouble(tempR));
                    else if (tempOp.equals("sin"))
                        result = Math.sin(Math.toRadians(Double.parseDouble(tempR)));
                    else if (tempOp.equals("cos"))
                        result = Math.cos(Math.toRadians(Double.parseDouble(tempR)));
                }
                
            }
            else
            {
                if(tempOp.equals("+"))
                    result = Double.parseDouble(tempL) + Double.parseDouble(tempR);
                else if (tempOp.equals("-"))
                    result = Double.parseDouble(tempL) - Double.parseDouble(tempR);
                else if (tempOp.equals("*"))
                    result = Double.parseDouble(tempL) * Double.parseDouble(tempR);
                else if (tempOp.equals("/")){
                    if (Double.parseDouble(tempR) != 0)
                        result = Double.parseDouble(tempL) / Double.parseDouble(tempR);
                    else
                        display.append("Error: Divide by zero");
                }
                else if (tempOp.equals("^"))
                {
                    if (Double.parseDouble(tempR) == 0)
                        result = 1.0;
                    else if (Double.parseDouble(tempR) < 0)
                    {
                        double newLeft = 1 / Double.parseDouble(tempL);
                        result = newLeft;
                        for (int i = 0; i < ((-1) *Double.parseDouble(tempR)) - 1; i++)
                        {
                            result = result * result;
                        }
                    }
                    else
                    {
                        result = Double.parseDouble(tempL);
                        for (int i = 0; i < Double.parseDouble(tempR) - 1; i++)
                        {
                            result *= result;
                        }
                    }
                }
                
            }
            if (locationOfParen == 0)
                left = Double.toString(result);
            else
                right = Double.toString(result);
        }
        catch(NumberFormatException nfe)
        {
			display.append("Error: Number Formatting");
		}
    }
    /**
     Operate on the current expression and display the result
     */
    
	public void operate(){
		double result = 0.0;
        String resultS = "";
        String paren = "";
        try
        {
            if (hasParenthesis)
            {
                if (locationOfParen == 0)
                    paren += left;
                else
                    paren += right;
                calculateParenthesis();
            }
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
                        if (!hasParenthesis)
                        {
                            resultS += left + " " + operator + " " + right + " = " + String.valueOf(result);
                        }
                        else
                        {
                            if (locationOfParen == 0)
                                resultS = paren + " " + operator + " " + right + " = " + String.valueOf(result);
                            else
                                resultS = left + " " + operator + " " + paren + " = " + String.valueOf(result);
                        }
                        resultString.add(resultS);
                        counter = resultString.size();
                        clear();
                        left = "" + result;
                        refresh();
                        onRightSide = false;
                    }
                
                    else if (operator.equals("sin"))
                    {
                        result = Math.sin(Math.toRadians(Double.parseDouble(right)));
                        System.out.println(result);
                        if (!hasParenthesis)
                        {
                            resultS += left + " " + operator + " " + right + " = " + String.valueOf(result);
                        }
                        else
                        {
                            if (locationOfParen == 0)
                                resultS = paren + " " + operator + " " + right + " = " + String.valueOf(result);
                            else
                                resultS = left + " " + operator + " " + paren + " = " + String.valueOf(result);
                        }
                        resultString.add(resultS);
                        counter = resultString.size();
                        clear();
                        left = "" + result;
                        refresh();
                        onRightSide = false;
                    }
                
                    else if (operator.equals("cos"))
                    {
                        result = Math.cos(Math.toRadians(Double.parseDouble(right)));
                        System.out.println(result);
                        if (!hasParenthesis)
                        {
                            resultS += left + " " + operator + " " + right + " = " + String.valueOf(result);
                        }
                        else
                        {
                            if (locationOfParen == 0)
                                resultS = paren + " " + operator + " " + right + " = " + String.valueOf(result);
                            else
                                resultS = left + " " + operator + " " + paren + " = " + String.valueOf(result);
                        }
                        resultString.add(resultS);
                        counter = resultString.size();
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
            if (!hasParenthesis)
            {
                resultS += left + " " + operator + " " + right + " = " + String.valueOf(result);
            }
            else
            {
                if (locationOfParen == 0)
                    resultS = paren + " " + operator + " " + right + " = " + String.valueOf(result);
                else
                    resultS = left + " " + operator + " " + paren + " = " + String.valueOf(result);
            }
            resultString.add(resultS);
            counter = resultString.size();
			clear();
			left = "" + result;
			refresh();
			onRightSide = false;
		}
		else if (operator.equals("-")){
			result = Double.parseDouble(left) - Double.parseDouble(right);
            if (!hasParenthesis)
            {
                resultS += left + " " + operator + " " + right + " = " + String.valueOf(result);
            }
            else
            {
                if (locationOfParen == 0)
                    resultS = paren + " " + operator + " " + right + " = " + String.valueOf(result);
                else
                    resultS = left + " " + operator + " " + paren + " = " + String.valueOf(result);
            }
            resultString.add(resultS);
            counter = resultString.size();
			clear();
			left = "" + result;
			refresh();
			onRightSide = false;
		}
		else if (operator.equals("*")){
			result = Double.parseDouble(left) * Double.parseDouble(right);
            if (!hasParenthesis)
            {
                resultS += left + " " + operator + " " + right + " = " + String.valueOf(result);
            }
            else
            {
                if (locationOfParen == 0)
                    resultS = paren + " " + operator + " " + right + " = " + String.valueOf(result);
                else
                    resultS = left + " " + operator + " " + paren + " = " + String.valueOf(result);
            }
            resultString.add(resultS);
            counter = resultString.size();
			clear();
			left = "" + result;
			refresh();
			onRightSide = false;
		}
		else if (operator.equals("/")){
			if (Double.parseDouble(right) != 0){
				result = Double.parseDouble(left) / Double.parseDouble(right);
                if (!hasParenthesis)
                {
                    resultS += left + " " + operator + " " + right + " = " + String.valueOf(result);
                }
                else
                {
                    if (locationOfParen == 0)
                        resultS = paren + " " + operator + " " + right + " = " + String.valueOf(result);
                    else
                        resultS = left + " " + operator + " " + paren + " = " + String.valueOf(result);
                }
                resultString.add(resultS);
                counter = resultString.size();
				clear();
				left = "" + result;
				refresh();
				onRightSide = false;
			} else
            {
                if (!hasParenthesis)
                {
                    resultS += left + " " + operator + " " + right + " = Error:Divide by zero";
                }
                else
                {
                    if (locationOfParen == 0)
                        resultS = paren + " " + operator + " " + right + " = Error:Divide by zero";
                    else
                        resultS = left + " " + operator + " " + paren + " = Error:Divide by zero";
                }
                resultString.add(resultS);
                counter = resultString.size();
				display.append("Error: Divide by zero");
            }
		}
        else if (operator.equals("^"))
        {
            if (Double.parseDouble(right) == 0)
            {
                result = 1.0;
                if (!hasParenthesis)
                {
                    resultS += left + " " + operator + " " + right + " = " + String.valueOf(result);
                }
                else
                {
                    if (locationOfParen == 0)
                        resultS = paren + " " + operator + " " + right + " = " + String.valueOf(result);
                    else
                        resultS = left + " " + operator + " " + paren + " = " + String.valueOf(result);
                }
                resultString.add(resultS);
                counter = resultString.size();
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
                if (!hasParenthesis)
                {
                    resultS += left + " " + operator + " " + right + " = " + String.valueOf(result);
                }
                else
                {
                    if (locationOfParen == 0)
                        resultS = paren + " " + operator + " " + right + " = " + String.valueOf(result);
                    else
                        resultS = left + " " + operator + " " + paren + " = " + String.valueOf(result);
                }
                resultString.add(resultS);
                counter = resultString.size();
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
                if (!hasParenthesis)
                {
                    resultS += left + " " + operator + " " + right + " = " + String.valueOf(result);
                }
                else
                {
                    if (locationOfParen == 0)
                        resultS = paren + " " + operator + " " + right + " = " + String.valueOf(result);
                    else
                        resultS = left + " " + operator + " " + paren + " = " + String.valueOf(result);
                }
                resultString.add(resultS);
                counter = resultString.size();
                clear();
                left += "" + result;
                refresh();
                onRightSide = false;
            }
        }
        
        }
        }
            catch(NumberFormatException nfe)
        {
			display.append("Error: Number Formatting");
		}
	}




}
