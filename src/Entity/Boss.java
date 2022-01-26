package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Tiles.TileMap;

public class Boss extends Entity {

    private int health;
    private boolean dead;
	private int damage;

    private ArrayList<Projectiles> projectiles;
    private long projectileTimerCounter;
    private boolean spawned;

    private boolean iFrame;
    private long iFrameCounter;

    private BufferedImage sprite;
	

    public Boss(TileMap tm) {
        super(tm);

        moveSpeed = 1.1;
		maxSpeed = 1.4;
		fallSpeed = 0.2;
		maxFallSpeed = 10.0;
		
		width = 40;
		height = 50;
		cwidth = 20;
		cheight = 30;
    
        health = 20;
		damage = 1;

        dead = false;

        projectiles = new ArrayList<Projectiles>();
        projectileTimerCounter = System.nanoTime();

        spawned = false;

        right = true;
        facingRight = true;

        try {
            sprite = ImageIO.read(new File("./assets/player/mswong.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
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

        if(falling) {
			dy += fallSpeed;
		}
    
	}

    public int getDamage(){
        return damage;
    }

    public boolean isDead(){
        return dead;
    }

    public boolean isSpawend(){
        return spawned;
    }

    public void setSpawned(boolean b){
        spawned = b;
    }



    public void hit(int damage) {
		if(dead||iFrame) return;
		health -= damage;
		if(health < 0) health = 0;
		if(health == 0) dead = true;
        iFrame = true;
		iFrameCounter = System.nanoTime();
	}

    public void checkPlayerCollisions(Player player){

        for(int i = 0; i < projectiles.size(); i++) {
            if(projectiles.get(i).intersects(player)) {
                player.hit(damage);
                projectiles.get(i).setHit();
                break;
            }
        }

        if (intersects(player)) {
            player.hit(damage);
        }
    }

    public void update(){
        getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, 240);        

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
        
        long elapsed = (System.nanoTime() - projectileTimerCounter) / 1000000;
        

        if (elapsed > 4000) {
            Projectiles projectile1 = new Projectiles(tileMap,facingRight,"./assets/projectiles/1.png");
            Projectiles projectile2 = new Projectiles(tileMap,facingRight,"./assets/projectiles/1.png");
            Projectiles projectile3 = new Projectiles(tileMap,facingRight,"./assets/projectiles/1.png");

            projectile1.setPosition(x, y+20);
            projectile2.setPosition(x, y);
            projectile3.setPosition(x, y-20);
            projectiles.add(projectile1);
            projectiles.add(projectile2);
            projectiles.add(projectile3);
            
            projectileTimerCounter = System.nanoTime();
        }

        if(iFrame) {
			long elapsedIFrame =
				(System.nanoTime() - iFrameCounter) / 1000000;
			if(elapsedIFrame > 1000) {
				iFrame = false;
			}
		}

        for (int i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).update();
            if (projectiles.get(i).undrawFromGame()) {
                projectiles.remove(i);
                i--;
            }
        }

        if (health<=0) {
            dead= true;
        }

    }


    public void draw(Graphics2D g){
        setMapPosition();
        if (!spawned) {
            return;
        }

        for (int i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).draw(g);
        }

        if(iFrame){
            long elapsed = (System.nanoTime()-iFrameCounter) / 1000000;
            if (elapsed/100 %2 ==0) {
                return;
            }
        }

        if(facingRight) {
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
