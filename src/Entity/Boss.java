package Entity;

import java.awt.Graphics2D;
import java.util.ArrayList;

import Tiles.TileMap;

public class Boss extends MapObject {

    private int health;
    private int maxHealth;
    private boolean dead;
	private int damage;

    private ArrayList<Money> projectiles;
    private long projectileTimerCounter;
    private boolean spawned;
	

    public Boss(TileMap tm) {
        super(tm);

        moveSpeed = 1.1;
		maxSpeed = 1.4;
		fallSpeed = 0.2;
		maxFallSpeed = 10.0;
		
		width = 30;
		height = 30;
		cwidth = 20;
		cheight = 20;
        

        health = maxHealth = 20;
		damage = 1;

        dead = false;

        projectiles = new ArrayList<Money>();
        projectileTimerCounter = System.nanoTime();

        spawned = false;

        right = true;
        facingRight = true;
        
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
		if(dead) return;
		health -= damage;
		if(health < 0) health = 0;
		if(health == 0) dead = true;
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

        if (elapsed > 1000) {
            Money projectile1 = new Money(tileMap,facingRight);
            Money projectile2 = new Money(tileMap,facingRight);
            Money projectile3 = new Money(tileMap,facingRight);

            projectile1.setPosition(x, y+20);
            projectile2.setPosition(x, y);
            projectile3.setPosition(x, y-20);
            projectiles.add(projectile1);
            projectiles.add(projectile2);
            projectiles.add(projectile3);
            
            projectileTimerCounter = System.nanoTime();
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

        super.draw(g);
    }
    
}
