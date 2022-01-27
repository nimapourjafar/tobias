package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Tiles.TileMap;

// class to create and handle the boss, extends entity class
public class Boss extends Entity {
    
    // variable to store health, damage, see if boss is dead, and if boss spawned
    private int health;
    private boolean dead;
	private int damage;
    private boolean spawned;

    // variables for projectiles
    private ArrayList<Projectiles> projectiles;
    private long projectileTimerCounter;
    
    // variables for iFrame (invincibility frames)
    private boolean iFrame;
    private long iFrameCounter;

    // sprite to draw as the boss
    private BufferedImage sprite;
	

    public Boss(TileMap tm) {
        // declare entity super function
        super(tm);

        // set physics variables
        moveSpeed = 1.1;
		maxSpeed = 1.4;
		fallSpeed = 0.2;
		maxFallSpeed = 10.0;
		
        // variables for dimensions of image and collision box
		width = 40;
		height = 50;
		cwidth = 20;
		cheight = 30;
    
        // set health and damage
        health = 20;
		damage = 1;

        // init the boss as alive
        dead = false;

        // init the array of projectiles
        projectiles = new ArrayList<Projectiles>();
        // start the timer as soon as the boss is made
        projectileTimerCounter = System.nanoTime();

        // boss is not spawned by default
        spawned = false;

        // boss moves right by default
        right = true;

        try {
            // load sprite
            sprite = ImageIO.read(new File("./assets/player/mswong.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    // function to move the boss automatically
    private void getNextPosition() {
		
		// if moving left, decrease the x vector (negative x means going left) 
		if(left) {
			dx -= moveSpeed;
            // cap the speed at the max speed
			if(dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		}
        // if moving left, increase the x vector (positive x means going right) 
		else if(right) {
			dx += moveSpeed;
            // cap the speed at the max speed
			if(dx > maxSpeed) {
				dx = maxSpeed;
			}
		}
	}
    
    // getter and setter functions

    public int getDamage(){
        return damage;
    }

    public boolean isDead(){
        return dead;
    }

    public boolean isSpawned(){
        return spawned;
    }

    public void setSpawned(boolean b){
        spawned = b;
    }

    // function for the boss to take damage, takes in damage as parameter
    public void hit(int damage) {
        // if the boss is invincble or dead, ignore the incoming damage
		if(dead||iFrame) return;
        // otherwise, decrease the health
		health -= damage;
        // if the health goes below 0, set it to 0
		if(health < 0) health = 0;
        // if the health is 0, set the boss as dead
		if(health == 0) dead = true;
        // once the boss takes damage, set the iFrame boolean to true and start the timer
        iFrame = true;
		iFrameCounter = System.nanoTime();
	}

    // check if boss collides with player (passes in player object)
    public void checkPlayerCollisions(Player player){
        // loop through the boss' current projectiles and see if it intersects with the player
        for(int i = 0; i < projectiles.size(); i++) {
            if(projectiles.get(i).intersects(player)) {
                player.hit(damage);
                projectiles.get(i).setHit();
                break;
            }
        }
        // if the player intersects the boss, damage the player
        if (intersects(player)) {
            player.hit(damage);
        }
    }
    // update method
    public void update(){
        // calculate the next position
        getNextPosition();
        // check for tile collision
		checkTileMapCollision();
        // set the x position
		setPosition(xtemp, 240);        

        // switch to left if x momentum stops and was facing right
        if(right && dx == 0) {
			right = false;
			left = true;
		}
        // switch to right if x momentum stops and was facing left
		else if(left && dx == 0) {
			right = true;
			left = false;
		}
        // count to see how long it was since last projectile was shot
        long elapsed = (System.nanoTime() - projectileTimerCounter) / 1000000;
        // if 4 seconds elapsed, shoot new projectiles
        if (elapsed > 4000) {
            // add new projectiles to array
            Projectiles projectile1 = new Projectiles(tileMap,right,"./assets/projectiles/1.png");
            Projectiles projectile2 = new Projectiles(tileMap,right,"./assets/projectiles/1.png");
            Projectiles projectile3 = new Projectiles(tileMap,right,"./assets/projectiles/1.png");
            // set the projectiles at different heights
            projectile1.setPosition(x, y+20);
            projectile2.setPosition(x, y);
            projectile3.setPosition(x, y-20);
            projectiles.add(projectile1);
            projectiles.add(projectile2);
            projectiles.add(projectile3);
            // reset the timer
            projectileTimerCounter = System.nanoTime();
        }
        // check if currently invincble
        if(iFrame) {
            // check elapsed time and set iFrame apappropriately
			long elapsedIFrame =
				(System.nanoTime() - iFrameCounter) / 1000000;
			if(elapsedIFrame > 1000) {
				iFrame = false;
			}
		}
        // update all projectiles on screen
        for (int i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).update();
            // if projectile got hit and needs to disappear, remove it from array and reduce index (so we dont go out of bounds)
            if (projectiles.get(i).undrawFromGame()) {
                projectiles.remove(i);
                i--;
            }
        }

    }

    // draw method
    public void draw(Graphics2D g){
        // set map position
        setMapPosition();
        // if not spawned, don't draw
        if (!spawned) {
            return;
        }
        // draw projectiles
        for (int i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).draw(g);
        }
        // if iFrame, create a flashing effect by only drawing every other frame
        if(iFrame){
            long elapsed = (System.nanoTime()-iFrameCounter) / 1000000;
            if (elapsed/100 %2 ==0) {
                return;
            }
        }
        // draw the sprite image, if facing right flip the image, otherwise draw it normally
        if(right) {
			g.drawImage(
				sprite,
				(int)(x + xmap - width / 2 + width),
				(int)(y + ymap - height / 2),
				-width,
				height,
				null
			);
			
		}
		else {
			g.drawImage(
				sprite,
				(int)(x + xmap - width / 2),
				(int)(y + ymap - height / 2),
				width,
				height,
				null
			);
		}
    }
    
}

