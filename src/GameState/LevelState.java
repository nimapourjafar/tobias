package GameState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Entity.Boss;
import Entity.Enemy;
import Entity.Player;
import Game.GamePanel;
import Tiles.Background;
import Tiles.HUD;
import Tiles.TileMap;

// state for the level & extends off of gamestate
public class LevelState extends GameState {
    // tilemap for current level
    private TileMap tileMap;
    // background
    private Background bg;
    // player object
    private Player player;
    // hude object
    private HUD hud;
    // enemies in level
    private ArrayList<Enemy> enemies;
    // boss object
    private Boss boss;

    // method to pass in gsm and init the state
    public LevelState(GameStateManager gsm){
        this.gsm = gsm;
        init();
    }


    public void init() {
        // make tileMap with each tile sized at 18
        tileMap = new TileMap(18);
        // read map from files
        tileMap.loadMap("./assets/map/world.csv");
        // set tileMap top left corner at 0,0
        tileMap.setPosition(0, 0);

        // create background
        bg = new Background();

        // create player on the level tile map
        player = new Player(tileMap);
        // set the position of the player
        player.setPosition(100,100);
        // create the HUD
        hud = new HUD(player);

        // create boss on the level tile map
        boss = new Boss(tileMap);
        // set boss position in boss room
        boss.setPosition(2500, 120);

        // declare enemies array
        enemies = new ArrayList<Enemy>();
		
		// array of points for enemies
		Point[] points = new Point[] {
			new Point(200, 100),
            new Point(700,200),
            new Point(866,255),
            new Point(1255,147),
            new Point(1748,183),
            new Point(2064,255),
            new Point(2100,255),
		};
        // create enemies and set their positions based on the point array
		for(int i = 0; i < points.length; i++) {
			Enemy enemy = new Enemy(tileMap);
			enemy.setPosition(points[i].x, points[i].y);
			enemies.add(enemy);
		}
    }

    // update method
    public void update() {
        // update player
        player.update();
        // move the tile map based on where the player is
		tileMap.setPosition(
			GamePanel.WIDTH / 2 - player.getx(),
			GamePanel.HEIGHT / 2 - player.gety()
		);

        // check if player collides with enemy
        player.checkEnemyCollision(enemies);

        // check if boss is spawned
        if (boss.isSpawned()) {
            // update boss
            boss.update();
            // check collisions
            player.checkBossCollision(boss);
            boss.checkPlayerCollisions(player);
        }
        // update all enemies and remove the ones that are dead from array list
        for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
			if(e.isDead()) {
				enemies.remove(i);
				i--;
			}
		}
        
        // lock player inside boss room when they enter is
        if (player.getx() > 2360) {
            tileMap.changeTile(12, 129, 5);
            tileMap.changeTile(13, 129, 5);
            tileMap.changeTile(14, 129, 5);
            // spawn boss
            boss.setSpawned(true);
        }
        // if boss or player dies, go back to main menu
        if (boss.isDead() || player.getHealth()<=0) {
            gsm.setState(gsm.MENUSTATE);
        }

    }
    // draw level
    public void draw(Graphics2D g) {
        // fill panel with white
        g.setColor(Color.white);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        // draw background
        bg.draw(g);
        // draw tile map
        tileMap.draw(g);
        // draw enemies
        for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}
        // draw boss
        boss.draw(g);
        // draw player
        player.draw(g);
        // draw hud
        hud.draw(g);

        
    }

    public void keyPressed(int k) {
        // key events for player
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
        // key events for player
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
        
    }
    
}
