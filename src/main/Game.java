package main;

import javax.swing.JFrame;

public class Game{

    public static void main(String[] args) {
        // declare JFrame
        JFrame window = new JFrame("Game");
        // set contents of the panel to our game panel
        window.setContentPane(new GamePanel());
        // window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);
    }

}