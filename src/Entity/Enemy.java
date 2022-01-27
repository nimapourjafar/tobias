package Entity;

import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;

import Tiles.TileMap;

// class to handle and create an enemy, extends entity class
public class Enemy extends Entity {
	// variable to store health, damage, see if enemy is dead
    private int health;
    private boolean dead;
	private int damage;

	// declare animation class
	private Animation animation;
    
    public Enemy(TileMap tm){
		// declare entity super function
        super(tm);

		// set physics variables
        moveSpeed = 1.1;
		maxSpeed = 1.4;
		fallSpeed = 0.2;
		maxFallSpeed = 10.0;
		
		// variables for dimensions of image and collision box
		width = 30;
		height = 30;
		cwidth = 20;
		cheight = 20;
		
		// set health and damage
		health  = 2;
		damage = 1;

        try {
			
			// load sprites for all different frames
			BufferedImage[] sprites = new BufferedImage[2];

            sprites[0] = ImageIO.read(new File("./assets/enemy/0.png"));
            sprites[1] = ImageIO.read(new File("./assets/enemy/1.png"));

			// set animation
		    animation = new Animation();
		    animation.setFrames(sprites);
		    animation.setDelay(500);

		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		// enemy starts moving left by default
		left = true;
		
    }

	// getters
    public boolean isDead() { return dead; }
	
	public int getDamage() { return damage; }

	public int getHealth() { return health; }
	
	// method for enemy to take damage
	public void hit(int damage) {
		if(dead) return;
		health -= damage;
		if(health < 0) health = 0;
		if(health == 0) dead = true;
	}

	// calculate enemy's next position
    private void getNextPosition() {
		
		// movement
		if(left) {
			dx -= moveSpeed;
			if(dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		}
		else if(right) {
			dx += moveSpeed;
			if(dx > maxSpeed) {
				dx = maxSpeed;
			}
		}
		
		// falling
		if(falling) {
			dy += fallSpeed;
		}	
	}

    public void update() {
		// if not on screen, don't update the enemy
		if (notOnScreen()) {
            return;
        }
		// update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		
		// if it hits a wall, go other direction
		if(right && dx == 0) {
			right = false;
			left = true;
		}
		else if(left && dx == 0) {
			right = true;
			left = false;
		}
		
		// update animation
		animation.update();
		
	}

    public void draw(Graphics2D g){
        // set enemy position
        setMapPosition();
		
		// draw enemy image based on direction it's facing
		if(right) {
			g.drawImage(
				animation.getImage(),
				(int)(x + xmap - width / 2 + width),
				(int)(y + ymap - height / 2),
				-width,
				height,
				null
			);
			
		}
		else {
			g.drawImage(
				animation.getImage(),
				(int)(x + xmap - width / 2),
				(int)(y + ymap - height / 2),
				width,
				height,
				null
			);
		}
    }

}
