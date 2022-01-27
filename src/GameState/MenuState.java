package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import Tiles.Background;

// state for the main menu (extends game state)
public class MenuState extends GameState{

    // background
    private Background bg;
    // choice in menu screen
    private int currentChoice = 0;
    // options on screen
    private String[] options = {"Start", "Help", "Quit"};
    // fonts
    private Font titleFont;
    private Font font;
    // pass the gsm in
    public MenuState(GameStateManager gsm){
        // init gsm 
        this.gsm = gsm;

        try{
            // set background and it's vector
            bg = new Background();
            bg.setVector(-0.1,0);
            // set the fonts
            titleFont = new Font("Arial", Font.PLAIN, 28);
            font = new Font("Arial", Font.PLAIN, 12);

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void init(){

    }
    // update background
    public void update(){
        bg.update();
    }
    // draw the graphics
    public void draw(Graphics2D g){
        // draw bg
        bg.draw(g);

        // draw title
        g.setColor(Color.BLACK);
        g.setFont(titleFont);
        g.drawString("Tobias", 115, 70);

        // set font
        g.setFont(font);

        // loop through all options and draw them (the one selected is black)
        for (int i = 0; i < options.length; i++) {
            if(i==currentChoice){
                g.setColor(Color.BLACK);
            }
            else{
                g.setColor(Color.RED);
            }
            g.drawString(options[i], 145, 140 + i * 15);
        }
    }

    public void keyPressed(int k){
        if(k==KeyEvent.VK_ENTER){
            select();
        }
        // if click up, decrement the current choice and wrap around if necessary
        else if(k== KeyEvent.VK_UP){
            currentChoice--;
            if(currentChoice == -1){
                currentChoice = options.length - 1;
            }
        }
        // if click down, increment the current choice and wrap around if necessary
        else if(k== KeyEvent.VK_DOWN){
            currentChoice++;
            if(currentChoice == options.length){
                currentChoice = 0;
            } 
        }
    }

    public void keyReleased(int k){

    }
    // launch the respective game state depending on which choice was selected
    private void select(){
        if(currentChoice==0){
            gsm.setState(gsm.LEVEL1STATE);
        }
        else if(currentChoice==1){
            gsm.setState(gsm.HELPSTATE);

        }
        else if(currentChoice==2){
            // quit
            System.exit(0);
        }
    }

    
}
