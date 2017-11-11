package edu.ucsb.cs56.projects.utilities.calculator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;


/**
 * Testing operations in Calculator, MAKE TESTS FIRST
 * BEFORE IMPLEMENTING NEW OPERATORS IN CALCULATOR.
 * IF CURRENT TESTS CONFLICT WITH NEW IMPLEMENTATIONS,
 * UPDATE THE TESTS TO PASS UNDER NEW CIRCUMSTANCES
 * @see edu.ucsb.cs56.projects.utilities.calculator;
 */

public class CalculatorTest {

}

/*
    // begin instance variables
    private final Calculator cal;
    private final JLabelMessageDestination fill;
    private final String[] test1 = {"1","2","+","3","5"};
    private final String[] test2 = {"-","2","-","-","5"};
    private final String[] test3 = {"3","3",".","4","*","2"};
    private final String[] test4 = {"2","0","/",".","5"};
    private final String[] test5 = {"3","2","DEL","0","*","2"};
    private final String[] test6 = {"2","3","3","5",".","CLEAR","2","+","2"};
    private final String[] test7 = {"1","0","1","+","/","*","1","1"};
    private final String[] test8 = {"1","/","0"};
    private final String[] test9 = {"1",".",".","3","-",".",".","2"};
    private final String[] test10 = {"-","3","^","2"};
    private final String[] test11 = {"3","√","-","2","7"};
    // end instance variables

    public CalculatorTest() {
	fill = new JLabelMessageDestination();
        cal = new Calculator(fill,fill);
    }

    public void evaluateNoException(String[] test) {
	for (String app : test) {
	    if (app.equals("DEL"))
	        cal.delete();
	    else if (app.equals("CLEAR"))
	        cal.clear();
	    else
	        cal.append(app);
	}
	cal.operate();
    }

    @Test
    public void testAddition() {
        evaluateNoException(test1);
        assertEquals(cal.getLeft(),"47");
    }

    @Test
    public void testSubtraction() {
        evaluateNoException(test2);
        assertEquals(cal.getLeft(),"3");
    }

    @Test
    public void testMultiplication() {
        evaluateNoException(test3);
        assertEquals(cal.getLeft(),"66.8");
    }

    @Test
    public void testDivision() {
        evaluateNoException(test4);
        assertEquals(cal.getLeft(),"40");
    }

    @Test
    public void testDelete() {
        evaluateNoException(test5);
        assertEquals(cal.getLeft(),"60");
    }


    @Test
    public void testClear() {
        evaluateNoException(test6);
        assertEquals(cal.getLeft(),"4");
    }

    @Test
    public void testOperatorReplacement() {
        evaluateNoException(test7);
        assertEquals(cal.getLeft(),"1111");
    }

    @Test
    public void testDivisionByZero() {
        evaluateNoException(test8);
        assertEquals(cal.getLeft(),"Infinity");
    }

    @Test
    public void testMultipleDecimals() {
	evaluateNoException(test9);
	assertEquals(cal.getLeft(),"1.1");
    }

    @Test
    public void testPowerOf() {
	evaluateNoException(test10);
	assertEquals(cal.getLeft(),"9");
    }

    @Test
    public void testRootOf() {
	evaluateNoException(test11);
	assertEquals(cal.getLeft(),"-3");
    }
    
} // CalculatorTest

*/
