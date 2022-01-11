package GameState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Entity.Enemy;
import Entity.Player;
import Game.GamePanel;
import Tiles.Background;
import Tiles.HUD;
import Tiles.TileMap;

public class LevelState extends GameState {

    private TileMap tileMap;
    private Background bg;
    private Player player;
    private HUD hud;
    private ArrayList<Enemy> enemies;

    public LevelState(GameStateManager gsm){
        this.gsm = gsm;
        init();
    }


    public void init() {
        tileMap = new TileMap(18);
        tileMap.loadMap("/Users/nimapourjafar/Documents/GitHub/tobias/assets/map/world.csv");
        tileMap.setPosition(0, 0);

        bg = new Background("/Users/nimapourjafar/Documents/GitHub/tobias/assets/background/background.png",0.2);

        player = new Player(tileMap);
        player.setPosition(100,100);
        hud = new HUD(player);

        enemies = new ArrayList<Enemy>();
		
		
		Point[] points = new Point[] {
			new Point(200, 100),
		};
		for(int i = 0; i < points.length; i++) {
			Enemy enemy = new Enemy(tileMap);
			enemy.setPosition(points[i].x, points[i].y);
			enemies.add(enemy);
		}
    }

    public void update() {
        
        player.update();
		tileMap.setPosition(
			GamePanel.WIDTH / 2 - player.getx(),
			GamePanel.HEIGHT / 2 - player.gety()
		);

        player.checkEnemyCollision(enemies);

        for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
			if(e.isDead()) {
				enemies.remove(i);
				i--;
			}
		}
        
    }

    public void draw(Graphics2D g) {
        
        g.setColor(Color.white);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        
        bg.draw(g);
        tileMap.draw(g);

        for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}


        player.draw(g);
        
        
        hud.draw(g);

        
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
            player.shootMoney(true);
        }
        if (k==KeyEvent.VK_S) {
            player.setCarMode(true);
        }
        if (k==KeyEvent.VK_D) {
            player.setAttacking(true);
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
        if (k==KeyEvent.VK_A) {
            player.shootMoney(false);
        }
        if (k==KeyEvent.VK_S) {
            player.setCarMode(false);
        }
        if (k==KeyEvent.VK_D) {
            player.setAttacking(false);
        }

        
    }
    
}
