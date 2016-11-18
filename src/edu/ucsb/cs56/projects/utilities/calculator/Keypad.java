package edu.ucsb.cs56.projects.utilities.calculator;
import java.awt.GridLayout;
import javax.swing.JComponent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JTextArea;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
	Keypad is a class to represent a keypad which sends signals to a calculator
	@author Sam Dowell
*/
public class Keypad extends JComponent implements KeyListener{

	private Calculator calculator;
	
/**
	Constructor
	@param calc Takes a Calculator object as an argument, to send signals to
*/
	public Keypad(Calculator calc){
		super();

		calculator = calc;
		setFocusable(true);
		addKeyListener(this);
		this.setLayout(new GridLayout(0,4));

		makeButton("Clear", () -> this.calculator.clear());
		makeButton("^");
		makeButton("√");
		makeButton("Delete", () -> this.calculator.delete());
		makeButton("7");
		makeButton("8");
		makeButton("9");
		makeButton("/");
		makeButton("4");
		makeButton("5");
		makeButton("6");
		makeButton("*");
		makeButton("1");
		makeButton("2");
		makeButton("3");
		makeButton("-");
		makeButton("0");
		makeButton(".");
		makeButton("Enter", () -> this.calculator.operate());
		makeButton("+");
	}
/**
	Helper method that initializes the JButtons for the GUI
	@param s String representation of what signal the button will send
*/
	private void makeButton(String s){
		JButton jb = new JButton(s);
		jb.addActionListener(new ButtonListener(s));
		this.add(jb);
	}
	private void makeButton(String s, Runnable func){
		JButton jb = new JButton(s);
		jb.addActionListener(new ButtonListener(s, func));
		this.add(jb);
	}
/**
	Helper method to ensure that this window has the focus
*/
	private void resetFocus(){
		setFocusable(true);
		requestFocusInWindow();
	}
/**
	Inner class that implements an ActionListener to handle button presses
*/
   class ButtonListener implements ActionListener{
		private String num;
		private Runnable func;

		public ButtonListener(String s) {
			super();  // is this line necessary? what does it do?
			this.num = s;
			this.func = () -> calculator.append(num);
		}

		public ButtonListener(String s, Runnable func) {
			super();  // is this line necessary? what does it do?
			this.num = s;
			this.func = func;
		}

		public void actionPerformed (ActionEvent event) {
			this.func.run();
			resetFocus();
		}
    }

    @Override public void keyReleased(KeyEvent ke){}
    @Override public void keyTyped(KeyEvent ke){}

	/**
		Handles keyboard buttons
	*/
    @Override public void keyPressed(KeyEvent ke){
	String key = java.awt.event.KeyEvent.getKeyText(ke.getKeyCode());
		char k = ke.getKeyChar();
		if((k >= '0' && k <= '9') || k == '+' || k == '-' || k == '*' || k == '/' || k == '.')
			calculator.append("" + k);
		else if(k == 'c' || k == 'C')
			calculator.clear();
		else if(key.equals("Enter") || key.equals("Equals"))
			calculator.operate();
		else if(key.equals("NumPad +"))
			calculator.append("+");
		else if(key.equals("NumPad -"))
			calculator.append("-");
		else if(key.equals("NumPad *"))
			calculator.append("*");
		else if(key.equals("NumPad /"))
			calculator.append("/");
		else if(key.equals("Backspace"))
			calculator.delete();
		}
}
