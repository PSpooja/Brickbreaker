package brickBreaker;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame obj = new JFrame();          // creating object for JFame
		Gameplay gamePlay = new Gameplay();
		obj.setBounds(10,10,700,600);         // setting properties for JFrame
		obj.setTitle("Breakout Ball");
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(gamePlay);                // adding gamePlay object in JFrame object

    }

}
