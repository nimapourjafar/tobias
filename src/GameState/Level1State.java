package GameState;

import java.awt.Color;
import java.awt.Graphics2D;

import Game.GamePanel;
import Tiles.Background;
import Tiles.TileMap;

public class Level1State extends GameState {

    private TileMap tileMap;
    private Background bg;

    public Level1State(GameStateManager gsm){
        this.gsm = gsm;
        init();
    }

    public void init() {
        tileMap = new TileMap(18);
        tileMap.loadMap("/Users/nimapourjafar/Documents/GitHub/tobias/assets/map/world.csv");

        bg = new Background("/Users/nimapourjafar/Documents/GitHub/tobias/assets/background/background.png",0.2);
    }

    public void update() {
        // TODO Auto-generated method stub
        
    }

    public void draw(Graphics2D g) {
        
        g.setColor(Color.white);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        
        bg.draw(g);
        tileMap.draw(g);
        
        
    }

    public void keyPressed(int k) {
        // TODO Auto-generated method stub
        
    }

    public void keyReleased(int k) {
        // TODO Auto-generated method stub
        
    }
    
}
