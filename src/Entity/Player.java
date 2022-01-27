package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Tiles.TileMap;
// class that handles the player
// extends entity class
public class Player extends Entity {
    
    // health variables
    private int health;
    private int maxHealth;
    // money variables
    private int money;
    private int maxMoney;
    // variable to check if player is dead
    private boolean dead;
    // variables for money
    private boolean throwingMoney;
    private ArrayList<Projectiles> moneyOnScreen;
    private int moneyDamage;
    // variables for attacking
    private boolean attacking;
    private boolean attackStarted;
    private long attackTimer;
    private int attackDamage;
    private int attackRange;
    // variables for iFrame (invincibility frames)
    private boolean iFrame;
    private long iFrameCounter;

    // variables for car mode
    private boolean carMode;
    private int gas;
    private int maxGas;

    // global action variables
    private int IDLE = 0;
    private int MONEY = 1;    
    private int CAR = 2;
    private int ATTACK = 3;
    
    // array of sprites
    private ArrayList<BufferedImage> sprites;
    // variable for player's current action
    private int currentAction;
    

    public Player(TileMap tm) {
        super(tm);

        // dimensions
        width = 30;
		height = 50;
		cwidth = 20;
		cheight = 30;
		
        // movement variables
		moveSpeed = 0.3;
		maxSpeed = 1.6;
		stopSpeed = 0.4;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -4.8;
		stopJumpSpeed = 0.3;
		
        // facing right and falling by default
		facingRight = true;
        falling = true;
		
        // set health
		health = maxHealth = 5;
        // set money
        money = maxMoney = 10;
		
        // set damage for money
		moneyDamage = 2;
        // set array for money
		moneyOnScreen = new ArrayList<Projectiles>();
		
        // set attack variables
		attackDamage = 3;
		attackRange = 40;
        attackStarted = false;
        
        // gas variable
        gas = maxGas = 100;
        // set action as idle
        currentAction = IDLE;


        try {
            // load in sprites
			sprites = new ArrayList<BufferedImage>();
            
            BufferedImage idleImage = ImageIO.read(new File("./assets/player/idle.png"));
            BufferedImage moneyImage = ImageIO.read(new File("./assets/player/shooting.png"));
            BufferedImage attackImage = ImageIO.read(new File("./assets/player/attack.png"));
            BufferedImage driveImage = ImageIO.read(new File("./assets/player/driving.png"));

            sprites.add(idleImage);
            sprites.add(moneyImage);
            sprites.add(driveImage);
            sprites.add(attackImage);
            
		}
		catch(Exception e) {
			e.printStackTrace();
		}

    }
    // method to check for player collion with boss
    public void checkBossCollision(Boss boss){
        // checks to see if the boss colides with the player's attack hitbox to deal damage
        if (attacking) {
            if(facingRight) {
                if(
                    boss.getx() > x &&
                    boss.getx() < x + attackRange && 
                    boss.gety() > y - height / 2 &&
                    boss.gety() < y + height / 2
                ) {
                    boss.hit(attackDamage);
                }
            }
            else {
                if(
                    boss.getx() < x &&
                    boss.getx() > x - attackRange &&
                    boss.gety() > y - height / 2 &&
                    boss.gety() < y + height / 2
                ) {
                    boss.hit(attackDamage);
                }
            }
        }
        // checks to see if money collides with boss and deletes money if it does collide
        for (int i = 0; i < moneyOnScreen.size(); i++) {
            if (moneyOnScreen.get(i).intersects(boss)) {
                boss.hit(moneyDamage);
                moneyOnScreen.get(i).setHit();
                break;
            }
        }
        // if the player intersects the boss in car mode, the damage is ignored, otherwise the player gets dealt damage
        if (intersects(boss)) {
            if (carMode) {
                return;
            }
            else{
                hit(boss.getDamage());
            }
        }
    }
    // checks for collisions with enemies
    public void checkEnemyCollision(ArrayList<Enemy> enemies){
        // loop through enemies and check if they collide with short attack hitbox
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            if (attacking) {
                if(facingRight) {
					if(
						e.getx() > x &&
						e.getx() < x + attackRange && 
						e.gety() > y - height / 2 &&
						e.gety() < y + height / 2
					) {
						e.hit(attackDamage);
					}
				}
				else {
					if(
						e.getx() < x &&
						e.getx() > x - attackRange &&
						e.gety() > y - height / 2 &&
						e.gety() < y + height / 2
					) {
						e.hit(attackDamage);
					}
				}
            }
            
            // if enemy collides with player's money, damage the enemy and set the money as collided 
            for(int j = 0; j < moneyOnScreen.size(); j++) {
				if(moneyOnScreen.get(j).intersects(e)) {
					e.hit(moneyDamage);
					moneyOnScreen.get(j).setHit();
					break;
				}
			}
			
			// check enemy collision, player deals damage if in car mode, if not the player gets dealt damage
			if(intersects(e)) {
                if (carMode) {
                    e.hit(e.getHealth());
                }
                else{
                    hit(e.getDamage());
                }
			}
        }
    }
    // method for the player to take damage and turn invincible for a few frames (it also ignors damage if the player is currently invincible)
    public void hit(int damage) {
		if(dead || iFrame){
            return;
        }
		health -= damage;
		if(health < 0){
            health = 0;
        }
		if(health == 0){
            dead = true;
        }
		iFrame = true;
		iFrameCounter = System.nanoTime();
	}
    // method to set player's modes
    public void shootMoney(boolean b){
        throwingMoney = b;
    }
    public void setCarMode(boolean b){
        carMode = b;
    }
    public void setAttacking(boolean b){
        attacking = b;
    }
    // getters
    public int getHealth(){
        return health;
    }

    public int getMaxHealth(){
        return maxHealth;
    }

    public int getMoney(){
        return money;
    }

    public int getMaxMoney(){
        return maxMoney;
    }

    public int getGas(){
        return gas;
    }

    public int getMaxGas(){
        return maxGas;
    }
    // update method
    public void update(){
        // calculate next position
        getNextPosition();
        // check for collisions
        checkTileMapCollision(); 
        // set player position
        setPosition(xtemp, ytemp); 
        // recharge gas if player is not driving and is below 100 gas
        if (gas<maxGas && carMode==false) {
            gas+=1;
        }
        // increase speed if in car mode
        if (carMode && gas>0) {
            moveSpeed = 1.5;
    		maxSpeed = 2.6;
            // decrease gas when in car mode
            gas-=2;
            // get out of car mode and set player to idle when out of gas
            if (gas<=0) {
                carMode= false;
                currentAction = IDLE;
            }
        }
        // set speed back to normal
        else if(carMode==false){
            moveSpeed = 0.3;
		    maxSpeed = 1.6;
        }
        // if the player is throwing money, check to see if they have money left and create a new projectile object
        if (throwingMoney && currentAction != MONEY) {
            if (money>0) {
                // decrease player's current money
                money -=1;
                Projectiles money = new Projectiles(tileMap,facingRight,"./assets/projectiles/0.png");
                money.setPosition(x, y);
                moneyOnScreen.add(money);
            }
        }
        // update all the money projectiles on the screen
        // if a money is "hit", or has collided with something, remove it from the money array list
        for (int i = 0; i < moneyOnScreen.size(); i++) {
            moneyOnScreen.get(i).update();
            if (moneyOnScreen.get(i).undrawFromGame()) {
                moneyOnScreen.remove(i);
                i--;
            }
        }
        // if the player is performing a short attack, increase it's width
        if (attacking) {
            width =60;
            // start attack timer if the attack hasn't started
            if (attackStarted == false) {
                attackStarted= true;
                attackTimer = System.nanoTime();
            }
            // check how long the attack has lasted
            long elapsed =(System.nanoTime() - attackTimer) / 1000000;
            // if it's been over the elapsed time, set attacking to false and width back to normal
            if (elapsed>100) {
                attacking = false;
                attackStarted = false;
                width = 30;
            }

        }
        // update and see if iFrame timer has exceeded
        if(iFrame) {
			long elapsed =
				(System.nanoTime() - iFrameCounter) / 1000000;
			if(elapsed > 1000) {
				iFrame = false;
			}
		}
        // set current action for sprites based on action variables
        if (attacking) {
            if (currentAction!=ATTACK) {
                currentAction = ATTACK;
                
            }
        }
        else if(throwingMoney){
            if (currentAction!=MONEY) {
                currentAction= MONEY;
            }
        }
        else if(carMode){
            if (currentAction!=CAR) {
                currentAction= CAR;
            }
        }
        else{
            if (currentAction!=IDLE) {
                currentAction = IDLE;
            }
        }
        // check which direction the player is facing for drawing
        if (right) {
            facingRight = true;
        }
        else if (left) {
            facingRight = false;
        }
    }
    // get the players next position
    private void getNextPosition() {
        // decrease x direction to go left
        if(left) {
			dx -= moveSpeed;
            // cap speed to max
			if(dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		}
        // increase x direction to go left
		else if(right) {
			dx += moveSpeed;
            // cap speed to max
			if(dx > maxSpeed) {
				dx = maxSpeed;
			}
		}
        // if the player is not going left or right, stop the player's speed
		else {
            // decrease dx if the player was moving right and stop decreasing at 0
			if(dx > 0) {
				dx -= stopSpeed;
				if(dx < 0) {
					dx = 0;
				}
			}
            // increase dx if the player was moving left and stop increasing at 0
			else if(dx < 0) {
				dx += stopSpeed;
				if(dx > 0) {
					dx = 0;
				}
			}
		}
		
		
		// jumping
		if(jumping && !falling) {
            // set directional y value to jump start
			dy = jumpStart;
            // set falling to true so the jump start value gradually decreases
			falling = true;	
		}
		
		// falling
		if(falling) {
			
			// increase dy by fall speed (increasing dy makes the player go down )
			dy += fallSpeed;
            // increase dy by stopJumpSpeed if the player is not jumping anymore and heading back down
			if(dy < 0 && !jumping){ 
                dy += stopJumpSpeed;
            }
			// cap falling speed
			if(dy > maxFallSpeed){
                dy = maxFallSpeed;
            }
			
		}
    }
    // method to draw player
    public void draw(Graphics2D g){
        // set player's position relative to where the map is
        setMapPosition();
        // draw all projectiles on screen by looping through them
        for (int i = 0; i < moneyOnScreen.size(); i++) {
            moneyOnScreen.get(i).draw(g);
        }
        // create a flashing effect by only drawing the player every other frame when invincble
        if(iFrame){
            long elapsed = (System.nanoTime()-iFrameCounter) / 1000000;
            if (elapsed/100 %2 ==0) {
                return;
            }
        }
        // draw the players image based on whether they're facing right or left
        // draw the sprite based on the current action
        if(facingRight) {
			g.drawImage(
				sprites.get(currentAction),
				(int)(x + xmap - width / 2 + width),
				(int)(y + ymap - height / 2),
				-width,
				height,
				null
			);
			
		}
		else {
			g.drawImage(
				sprites.get(currentAction),
				(int)(x + xmap - width / 2),
				(int)(y + ymap - height / 2),
				width,
				height,
				null
			);
		}
    }

    

}