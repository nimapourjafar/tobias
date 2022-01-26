package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import Game.GamePanel;

public class HelpState extends GameState {

    private Font titleFont;
    private Font font;
    private String[] rules = {"A - Shoot projectiles", "S - Short attack", "D - Car mode","Defeat enemies with those attacks","Defeat boss to win"};

    public HelpState(GameStateManager gsm){
        this.gsm = gsm;
    }


    public void init() {
        titleFont = new Font("Arial", Font.PLAIN, 28);
        font = new Font("Arial", Font.PLAIN, 12);

    }

    public void update() {
        
    }

    public void draw(Graphics2D g) {
        g.setColor(new Color(116, 200, 255));
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

        g.setColor(Color.WHITE);
        g.setFont(titleFont);
        
        g.drawString("How to Play", 75, 60);
        g.setFont(font);

        for (int i = 0; i < rules.length; i++) {
            g.drawString(rules[i],40, 100 + i * 15);
        }

        g.drawString("Press ENTER to go back to main menu", 40, 200);
        
    }

    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ENTER) {
            gsm.setState(gsm.MENUSTATE);
        }
        
    }

    public void keyReleased(int k) {
        
    }
    
}
