package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import Game.GamePanel;

// State that shows help screen
// Extends game state
public class HelpState extends GameState {

    // fonts
    private Font titleFont;
    private Font font;
    // array of rules
    private String[] rules = {"A - Shoot projectiles", "S - Short attack", "D - Car mode","Defeat enemies with those attacks","Defeat boss to win"};

    // init gsm
    public HelpState(GameStateManager gsm){
        this.gsm = gsm;
    }

    // init the fonts
    public void init() {
        titleFont = new Font("Arial", Font.PLAIN, 28);
        font = new Font("Arial", Font.PLAIN, 12);

    }

    public void update() {
        
    }

    // draw the menu screen
    public void draw(Graphics2D g) {
        // set background
        g.setColor(new Color(116, 200, 255));
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

        g.setColor(Color.WHITE);
        g.setFont(titleFont);
        // draw string
        g.drawString("How to Play", 75, 60);
        g.setFont(font);
        // loop through array of rules and draw them
        for (int i = 0; i < rules.length; i++) {
            g.drawString(rules[i],40, 100 + i * 15);
        }

        g.drawString("Press ENTER to go back to main menu", 40, 200);
        
    }

    public void keyPressed(int k) {
        // if user enters enter key, set state to menu state
        if (k == KeyEvent.VK_ENTER) {
            gsm.setState(gsm.MENUSTATE);
        }
        
    }

    public void keyReleased(int k) {
        
    }
    
}
