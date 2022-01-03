package GameState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import Entity.Player;
import Game.GamePanel;
import Tiles.Background;
import Tiles.TileMap;

public class Level1State extends GameState {

    private TileMap tileMap;
    private Background bg;
    private Player player;

    public Level1State(GameStateManager gsm){
        this.gsm = gsm;
        init();
    }

    public void init() {
        tileMap = new TileMap(18);
        tileMap.loadMap("/Users/nimapourjafar/Documents/GitHub/tobias/assets/map/world.csv");

        bg = new Background("/Users/nimapourjafar/Documents/GitHub/tobias/assets/background/background.png",0.2);

        player = new Player(tileMap);
        player.setPosition(100,100);
    }

    public void update() {
        // TODO Auto-generated method stub
        player.update();

        tileMap.setPosition(
			GamePanel.WIDTH / 2 - player.getx(),
			GamePanel.HEIGHT / 2 - player.gety()
		);
        
    }

    public void draw(Graphics2D g) {
        
        g.setColor(Color.white);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        
        bg.draw(g);
        tileMap.draw(g);
        player.draw(g);
        
        
    }

    public void keyPressed(int k) {
        if (k== KeyEvent.VK_LEFT) {
            player.setLeft(true);
        }
        if (k==KeyEvent.VK_RIGHT) {
            player.setRight(true);
        }
        if (k==KeyEvent.VK_UP) {
            player.setJumping(true);
        }
        if (k==KeyEvent.VK_A) {
            player.shootMoney();
        }
        if (k==KeyEvent.VK_S) {
            player.setCardMode();
        }
        if (k==KeyEvent.VK_D) {
            player.setAttacking();
        }
        
    }

    public void keyReleased(int k) {
        if (k== KeyEvent.VK_LEFT) {
            player.setLeft(false);
        }
        if (k==KeyEvent.VK_RIGHT) {
            player.setRight(false);
        }
        if (k==KeyEvent.VK_UP) {
            player.setJumping(false);
        }
        
    }
    
}
