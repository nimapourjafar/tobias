package Game;

import javax.swing.JFrame;

public class Game {
	// Main function for game
	public static void main(String[] args) 
	{
		// Create JFrame and set it's properties
		JFrame window = new JFrame("Tobias");
		// Our game will be running as a JPanel inside of the JFrame
		window.setContentPane(new GamePanel());
		// Make sure program quits when window is closed
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Set more properties
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
	}
}