package edu.ucsb.cs56.projects.utilities.calculator;
import java.awt.GridLayout;
import javax.swing.JComponent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event. ActionEvent;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JTextArea;


public class Keypad extends JComponent{

	//private Calculator calculator;
	//private MessageDestination display;


	public Keypad(/*Calculator calc, MessageDestination disp*/){
		super();
		//display = disp;
		//calculator = calc;
		

		this.setLayout(new GridLayout(4,0));

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
		makeButton("Enter");
		makeButton("+");


	}

	private void makeButton(String s){
		JButton jb = new JButton(s);
		jb.addActionListener(new ButtonListener(s));
		this.add(jb);
	}

   class ButtonListener implements ActionListener {
	private String num;

	public ButtonListener(String s) {
	    super();  // is this line necessary? what does it do?
	    this.num = s;
	}

	public void actionPerformed (ActionEvent event) {
/*
	    char turn=game.getTurn();
	    if (turn==' ')
		return;
	    if (!game.isBlank(num)) {
		md.append("That square is already occupied!\n");
		return;
	    }
	    if (mdTurn != null) {
		String turnMessage;
		if(turn == 'X')
			turnMessage = "O's turn";
		else if (turn == 'O')
			turnMessage = "X's turn";
		else
			turnMessage = "Game Over";
              mdTurn.append(turnMessage);
 	    }
	    char winner=game.move(num);
	    JButton jb = buttons[num];
	    jb.setFont(new Font("sansserif",Font.BOLD,36));
	    jb.setText(Character.toString(turn)); // this is how we convert char to String

	    // check for a winner
	    if (winner=='D'){
		if(mdTurn != null)
		    mdTurn.append("");
		md.append("Phooey.  It's a draw.\n");
	    }
	    else if (winner!=' '){
		if(mdTurn != null)
		    mdTurn.append("");
		md.append(winner + " wins!\n");
	    }
	*/
	}
    }


}