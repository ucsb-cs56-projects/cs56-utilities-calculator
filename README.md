cs56-utilities-calculator
=========================

W14 ready! (Andrew Berls)

A graphical calculator written in Java

W14 edited by Zhe Liu and Wei Guo

![](http://s11.postimg.org/vi9b7cu6b/Screenshot_from_2014_03_13_16_46_16.png)

added function for sqrt, cos, sin.

added support for nested operations.

added support for retrieve functions for previous results and expressions.
 
how to use: 

1. download the whole file.
 
2. unzip it and go to the top directory.
 
3. in the command line window, type "ant run".

opeartion:

 "sqrt": used java.math.sqrt here, every time there is a right opeartor, the sqrt function will call the java.math.sqrt function.
 "cos":  used java.math.cos here, every time there is a right parameter, the cos function will call the java.math.cos
 "sin":  used java.math.sin here, every time there is a right parameter, the cos function will call the java.math.sin
 "^": used java.math.pow function, every time there is a left parameter and right parameter, the ^ function will call the java.math.pow
 "(" ad ")": we set parameter to check the parentheses whether or not is complete.
