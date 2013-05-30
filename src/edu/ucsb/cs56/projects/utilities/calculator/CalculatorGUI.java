package edu.ucsb.cs56.projects.utilities.calculator;
import javax.swing.JFrame;
import java.awt.ComponentOrientation;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.Font;
/** CalculatorGUI.java is a GUI interface for a Calculator

     @author Sam Dowell
     @version CS56, Spring 2011, UCSB
*/


public class CalculatorGUI {

    /** main method to fire up a JFrame on the screen  
          @param args not used
     */

    public static void main (String[] args) {
       JFrame frame = new JFrame() ;
       frame. setDefaultCloseOperation(JFrame. EXIT_ON_CLOSE) ;
	frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

       Font bigfont = new Font("serif", Font.BOLD, 28);
	//JLabelMessageDestination display = new JLabelMessageDestination();
	//frame.getContentPane().add(display);
	//display.setFont(bigfont);
	Keypad keys = new Keypad();
/*
       TicTacToeGame game = new TicTacToeGrid();
       TicTacToeComponent tttc = new TicTacToeComponent(game,md,turn);
*/
       frame.getContentPane().add(keys);


       // to make sure that grids go left to right, no matter what

       frame .applyComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
       frame. setSize(300,500) ;
       frame. setVisible(true) ;
    }
}
