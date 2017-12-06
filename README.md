cs56-utilities-calculator
=========================

**DESRIPTION**

The Mark-III Platinum-Edition 50-Megaton EcoBoost Java Calculator

![](http://i.imgur.com/b8w5bk8.png)

A simple, 4-function calculator that takes a correctly-entered algebraic expression and evaluates it.  It supports power functionality, subtraction, multiplication, division, and even addition (which is a big plus).  This newer, ecofriendly, zero-waste version allows multiple terms, nested expressions, and follows PEMDAS order of operations.  It supports floating point math, negative numbers, and has Clear and Delete Buttons.  

**HOW TO USE IT!**

Users can use their keyboard or mouse to input the numbers and operators.  Any legal input a user provides will be displayed on the Calculator at the top right.  Pressing Enter will start the operation, which will return a number as the answer and display it.  Pressing the Delete key will erase the most recent input, and hitting Clear will erase the entire display.

*General Rules:*

Implicit multiplication (i.e. 5(9+1) instead of 5*(9+1)) is not supported - however, this is listed as an issue on GitHub which YOU, intrepid undergrad, can solve out of the goodness of your heart (and points).

The square root button does not work.  Trust us.  This is also listed as an issue.

Generally, the calculator won't let you enter an expression that would be illegal, i.e. a plus after a plus, but it won't check some things until runtime - like mismatched parentheses.  Furthermore, if you hit enter on an incomplete expression (like when you're on an operator) nothing will happen.
(However, if you do manage to enter an illegal expression, please contact the police immediately)



Run calculator with <code>ant run</code>

project history
===============
```
 W14 | andrewberls 4pm | twodimension, Nazgugu |  A graphical calculator written in Java

 F16 | oshen 6pm | nediamond, oliverleifshen | More user-friendly, less buggy

 F17 | scottpchow23 | Justin-Nilsen, richawadaskar | A multi-operation, nested-expression-supporting calculator
``` 

**F17 Final Remarks**

The implementation of the multi-operation and nested-expression features meant most of the code for Calculator.java had to be torn up like old carpet and replaced.  As a result, it's solidly functional (except for the square root function), but it's clunky.  As you'll see in the issues, a lot of the code should be refactored into helper methods and cleaned up to make it more readable (we apologize in advance for that).  We've already gotten started with the isOperator() function.  Now, the code can be daunting to look at since it's so interconnected, but here's a basic rundown of how it works:

*Files You Need to Look At:*  Calculator.java and Keypad.java.  The others are GUI stuff.  Unless you're really overhauling this project to try and one-up us, these are the only files you need to even look at. Basically, Keypad.java maps functions to buttons using lambda expressions.  The makeButton() method makes it easy to add new buttons with new operators with relatively little code.

Calculator is where the real magic happens.  In particular, append() and evaluate() are going to be the methods you want to take a close look at.
