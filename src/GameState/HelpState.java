package GameState;

import Tiles.Background;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class HelpState extends GameState {

    private Color titleColor;
    private Font titleFont;
    private Font font;

    public HelpState(GameStateManager gsm){
        this.gsm = gsm;
    }

    public void init() {
    }

    public void update() {
        
    }

    public void draw(Graphics2D g) {
        g.setBackground(Color.BLUE);

        g.setColor(Color.WHITE);
        g.setFont(titleFont);
        g.drawString("How to Play", 80, 70);
        g.drawString("How to Play", 80, 70);
        
    }

    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ENTER) {
            gsm.setState(gsm.MENUSTATE);
        }
        
    }

    public void keyReleased(int k) {
        
    }
    
}
