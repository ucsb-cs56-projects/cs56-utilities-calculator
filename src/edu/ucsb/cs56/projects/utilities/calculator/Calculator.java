package edu.ucsb.cs56.projects.utilities.calculator;

import java.lang.NumberFormatException;

class Calculator {

	private String left, operator, right;
	private boolean onRightSide; // true if appending to right side of expression, false if appending to left side
	private JLabelMessageDestination display;
	public Calculator(JLabelMessageDestination display){
		left = "";
		operator = "";
		right = "";
		onRightSide = false;
		this.display = display;
		refresh();
	}
	public void append(String s){
		if(s.equals("Enter")){
			operate();
			return;
		}
		if(s.equals("Clear"))
			clear();
		else if(s.equals("Delete"))
			delete();
		else if(s.equals("-") && !onRightSide && left.equals(""))
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
	public void refresh(){
		if(onRightSide)
			display.append(left + " " + operator + " " + right + "|");
		else
			display.append(left + "| " + operator + " " + right);

	}
	public void clear(){
		left = "";
		operator = "";
		right = "";
		onRightSide = false;
		refresh();
	}
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
	}
	public void operate(){
		double result = 0.0;
		try{
		if(left.equals("") || operator.equals("") || right.equals("")){ return; }
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
		}catch(NumberFormatException nfe){
			display.append("Error: Number Formatting");
		}
	}
}