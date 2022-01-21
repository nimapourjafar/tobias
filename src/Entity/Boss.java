package Entity;

import java.util.ArrayList;
import Tiles.TileMap;

public class Boss extends MapObject {

    private int health;
    private int maxHealth;
    private boolean dead;
	private int damage;

    private ArrayList<Money> projectiles;
    private int projectileDamage;

    private long projectileTimerCounter;
	

    public Boss(TileMap tm) {
        super(tm);

        health = maxHealth = 20;
		damage = 1;

        projectiles = new ArrayList<Money>();
        projectileTimerCounter = System.nanoTime();
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
		
	}

    public void update(){
        getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
        
        long elapsed = (System.nanoTime() - projectileTimerCounter) / 1000000;

        if (elapsed > 5000) {
            Money projectile = new Money(tileMap,facingRight);
            projectile.setPosition(x, y+20);
            projectiles.add(projectile);
            projectile.setPosition(x, y);
            projectiles.add(projectile);
            projectile.setPosition(x, y-20);
            projectiles.add(projectile);
            projectileTimerCounter = System.nanoTime();
        }

        for (int i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).update();
            if (projectiles.get(i).undrawFromGame()) {
                projectiles.remove(i);
                i--;
            }
        }

    }


    public void draw(){
        
    }
    
}
