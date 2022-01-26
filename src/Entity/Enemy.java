package Entity;

import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;

import Tiles.TileMap;

public class Enemy extends Entity {

    private int health;
    private boolean dead;
	private int damage;
	
	private boolean iFrame;
	private long iFrameTimer;
    
    public Enemy(TileMap tm){
        super(tm);

        moveSpeed = 1.1;
		maxSpeed = 1.4;
		fallSpeed = 0.2;
		maxFallSpeed = 10.0;
		
		width = 30;
		height = 30;
		cwidth = 20;
		cheight = 20;
		
		health  = 2;
		damage = 1;

        try {
			
			BufferedImage[] sprites = new BufferedImage[2];

            sprites[0] = ImageIO.read(new File("./assets/enemy/0.png"));
            sprites[1] = ImageIO.read(new File("./assets/enemy/1.png"));

		    animation = new Animation();
		    animation.setFrames(sprites);
		    animation.setDelay(500);

		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		right = false;
		facingRight = false;
		left = true;
		
    }


    public boolean isDead() { return dead; }
	
	public int getDamage() { return damage; }

	public int getHealth() { return health; }
	
	public void hit(int damage) {
		if(dead || iFrame) return;
		health -= damage;
		if(health < 0) health = 0;
		if(health == 0) dead = true;
		iFrame = true;
		iFrameTimer = System.nanoTime();
	}

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
		if (notOnScreen()) {
            return;
        }
		// update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		// check flinching
		if(iFrame) {
			long elapsed =
				(System.nanoTime() - iFrameTimer) / 1000000;
			if(elapsed > 400) {
				iFrame = false;
			}
		}
		
		// if it hits a wall, go other direction
		if(right && dx == 0) {
			right = false;
			left = true;
			facingRight = false;
		}
		else if(left && dx == 0) {
			right = true;
			left = false;
			facingRight = true;
		}
		
		// update animation
		animation.update();
		
	}

    public void draw(Graphics2D g){
        
        setMapPosition();
		
		// super.draw(g);

		if(facingRight) {
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
