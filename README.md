cs56-utilities-calculator
=========================

**DESRIPTION**

A graphical calculator written in Java

![](http://i.imgur.com/b8w5bk8.png)

A simple 4 function calculator that takes numbers and performs basic operations on them such as addition subtraction, multiplication, and division.  Newer version comes with power and parenthesis support. Supports floating point math, and comes with Clear and Delete buttons to erase result and inputs.

**HOW TO USE IT!**

Users can use their keyboard or mouse to input the numbers and operators. The calculator supports multi-operations. Any legal input a user provides will be displayed on the Calculator at the top right.  Pressing Enter will start the operation, which will return a number as the answer and display it.  Pressing the Delete key will erase the most recent input, and hitting Clear will erase the entire display.

Run calculator with <code>ant run</code>

Run tests with <code>ant test</code>

project history
===============
```
 W14 | andrewberls 4pm | twodimension, Nazgugu |  A graphical calculator written in Java

 F16 | oshen 6pm | nediamond, oliverleifshen | More user-friendly, less buggy
 
 F17 | scottpchow23, 4pm | richawadaskar, Justin-Nilsen | More functionality, better design
``` 
__F17 final remarks__

The calculator has been redesigned to handle multi-operator functionality, as well as parenthesis, and the evaluation uses stacks to calculate the answer of the input. However, there are multiple areas where improvements can be made to the calculator. First, checking for a balanced parenthesis expression before calculating the output could be implemented. The square root and trig functions can also be added. There is a lot of refactoring that can be done, and cool features that can be added such as incorporating a cursor, or a pos/neg toggle button.

__F16 Final Remarks__

There has been major improvements to the calculator both in terms of the neatness of the code and the presentation of the GUI.  However, there is still a lot of limitations for both performance and reasonable use.  Power and root functions are experimental, which causes a quite of few problems when using it.  Multiplicative math with floating point numbers gives bizarre answers.  Performance wise the calculator can only do one operation at a time, so a possible development is to expand it so it can take more operators and expressions.  However, the whole theme of this project is to keep things simple, don't go overboard with features; make it simple to use while still being accurate.
