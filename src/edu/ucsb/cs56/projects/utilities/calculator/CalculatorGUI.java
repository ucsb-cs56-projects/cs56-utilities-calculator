package edu.ucsb.cs56.projects.utilities.calculator;
import javax.swing.JFrame;
import java.awt.ComponentOrientation;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.Font;
import java.awt.Dimension;

/** CalculatorGUI.java is a GUI interface for a Calculator

     @author Sam Dowell
     @version CS56, Spring 2013, UCSB
*/


public class CalculatorGUI  {

    /** main method to fire up a JFrame on the screen  
          @param args not used
     */
    
    public static void main (String[] args) {

       // create frame and components: Font, Display, Keypad, and Calculator
	
       JFrame frame = new JFrame();
       Font bigfont = new Font("Helvetica",Font.PLAIN, 28);
       JLabelMessageDestination display = new JLabelMessageDestination();
       Calculator calc = new Calculator(display);
       Keypad keys = new Keypad(calc);

       // add the display to the frame
       
       frame.add(javax.swing.Box.createHorizontalGlue());
       frame.getContentPane().add(display);

       // add keypad to the frame
       
       frame.add(javax.swing.Box.createHorizontalGlue());
       frame.setDefaultCloseOperation(JFrame. EXIT_ON_CLOSE);
       frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
       frame.getContentPane().add(keys);

       // set font and dimensions for display

       display.setFont(bigfont);
       display.setHorizontalAlignment(SwingConstants.RIGHT);
       display.setMaximumSize(new Dimension(600,40));
       
       keys.requestFocusInWindow();

       // to make sure that grids go left to right, no matter what

       frame.applyComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
       frame.setSize(600,800);
       frame.setVisible(true);
    }


}
