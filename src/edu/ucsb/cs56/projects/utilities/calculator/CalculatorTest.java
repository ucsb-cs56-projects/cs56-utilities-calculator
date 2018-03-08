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

// }


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
    private final String[] test12 = {"4","*","-","2"};
    private final String[] test13 = {"4","+","(","-","2",")"};
    private final String[] test14 = {"4","+","-","(","3",")"};
    private final String[] test15 = {"(","-","(","9",")",")"};
    private final String[] test16 = {"(","-","2","9",")"};
    private final String[] test17 = {"-","(","5",")","(","(","6","+","2",")","-","-","1","0",")"};
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
        assertEquals(cal.getAnswer(),"47.0");
    }

    @Test
    public void testSubtraction() {
        evaluateNoException(test2);
        assertEquals(cal.getAnswer(),"3.0");
    }

    @Test
    public void testMultiplication() {
        evaluateNoException(test3);
        assertEquals(cal.getAnswer(),"66.8");
    }

    @Test
    public void testDivision() {
        evaluateNoException(test4);
        assertEquals(cal.getAnswer(),"40.0");
    }

    @Test
    public void testDelete() {
        evaluateNoException(test5);
        assertEquals(cal.getAnswer(),"60.0");
    }

    @Test
    public void testClear() {
        evaluateNoException(test6);
        assertEquals(cal.getAnswer(),"4.0");
    }

    /*@Test
    public void testOperatorReplacement() {
        evaluateNoException(test7);
        assertEquals(cal.getAnswer(),"1111.0");
    }

    @Test
    public void testDivisionByZero() {
        evaluateNoException(test8);
        assertEquals(cal.getAnswer(),"Infinity");
    }

    @Test
    public void testMultipleDecimals() {
	evaluateNoException(test9);
	assertEquals(cal.getAnswer(),"1.1");
    }

    @Test
    public void testPowerOf() {
	evaluateNoException(test10);
	assertEquals(cal.getAnswer(),"-9.0");
    }

    @Test
    public void testRootOf() {
	evaluateNoException(test11);
	assertEquals(cal.getAnswer(),"-3.0");
    }*/

    @Test
    public void testNegativeNextToOperator() {
	evaluateNoException(test12);
	assertEquals(cal.getAnswer(),"-8.0");
    }

    @Test
    public void testNegativeInParentheses() {
	evaluateNoException(test13);
	assertEquals(cal.getAnswer(),"2.0");
    }

    @Test
    public void testNegativeAfterOperatorBeforeParentheses() {
	evaluateNoException(test14);
	assertEquals(cal.getAnswer(),"1.0");
    }

    @Test
    public void testNegativeDistributingIntoParentheses() {
	evaluateNoException(test15);
	assertEquals(cal.getAnswer(),"-9.0");
    }

    @Test
    public void testNegativeOnlyInParentheses() {
	evaluateNoException(test16);
	assertEquals(cal.getAnswer(),"-29.0");
    }

    @Test
    public void testImplicitMultiplication() {
	evaluateNoException(test17);
	assertEquals(cal.getAnswer(),"-90.0");
    }

} // CalculatorTest
