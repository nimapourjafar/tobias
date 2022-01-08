package GameState;

import Tiles.Background;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class HelpState extends GameState {

    private Background bg;
    private Color titleColor;
    private Font titleFont;
    private Font font;

    public HelpState(GameStateManager gsm){
        this.gsm = gsm;

        try{
            bg = new Background("/Users/nimapourjafar/Documents/GitHub/tobias/assets/background/background.png", 1);
            bg.setVector(-0.1,0);

            titleColor = new Color(128, 0, 0);
            titleFont = new Font("Arial", Font.PLAIN, 28);
            font = new Font("Arial", Font.PLAIN, 12);

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void init() {
        // TODO Auto-generated method stub
        
    }

    public void update() {
        // TODO Auto-generated method stub
        
    }

    public void draw(Graphics2D g) {
        // TODO Auto-generated method stub
        
    }

    public void keyPressed(int k) {
        // TODO Auto-generated method stub
        
    }

    public void keyReleased(int k) {
        // TODO Auto-generated method stub
        
    }
    
}
