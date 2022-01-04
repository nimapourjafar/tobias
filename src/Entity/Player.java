package Entity;

import Tiles.TileMap;

import java.awt.Graphics2D;

public class Player extends MapObject {
    
    private int health;
    private int maxHealth;
    private int money;
    private int maxMoney;

    private boolean dead;



    private boolean throwingMoney;
    private int moneyCost;
    private int moneyDamage;

    private boolean attacking;
    private int attackDamage;
    private int attackRange;

    private boolean iFrame;
    private long iFrameCounter;

    private boolean carMode;

    private int IDLE = 0;
    private int WALKING = 1;
    private int JUMPING = 2;
    private int FALLING = 3;
    private int MONEY = 4;    
    private int CAR = 5;
    private int ATTACK = 6;
    

    public Player(TileMap tm) {
        super(tm);


        width = 30;
		height = 30;
		cwidth = 20;
		cheight = 20;
		
		moveSpeed = 0.3;
		maxSpeed = 1.6;
		stopSpeed = 0.4;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -4.8;
		stopJumpSpeed = 0.3;
		
		facingRight = true;
        falling = true;
		
		health = maxHealth = 5;
		maxMoney = 2500;
        money = maxMoney;
		
		moneyCost = 200;
		moneyDamage = 5;
		//fireBalls = new ArrayList<FireBall>();
		
		attackDamage = 8;
		attackRange = 40;

        animation = new Animation();
		currentAction = IDLE;
		
		animation.setDelay(400);
    }

    public void shootMoney(){
        throwingMoney = true;
    }
    public void setCarMode(){
        carMode = !carMode;
    }
    public void setAttacking(){
        attacking = true;
    }

    public void update(){

        getNextPosition();
        checkTileMapCollision(); 
        setPosition(xtemp, ytemp); 

        if(iFrame) {
			long elapsed =
				(System.nanoTime() - iFrameCounter) / 1000000;
			if(elapsed > 1000) {
				iFrame = false;
			}
		}

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
        else if(dy>0){
            if (currentAction != FALLING) {
                currentAction = FALLING;
            }
        }
        else if(dy<0){
            if (currentAction != JUMPING) {
                currentAction = JUMPING;
            }
        }
        else if(left || right){
            if (currentAction != WALKING) {
                currentAction = WALKING;
            }
        }
        else{
            if (currentAction!=IDLE) {
                currentAction = IDLE;
            }
        }
        if (right) {
            facingRight = true;
        }
        else if (left) {
            facingRight = false;
        }
    }

    private void getNextPosition() {
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
		else {
			if(dx > 0) {
				dx -= stopSpeed;
				if(dx < 0) {
					dx = 0;
				}
			}
			else if(dx < 0) {
				dx += stopSpeed;
				if(dx > 0) {
					dx = 0;
				}
			}
		}
		
		
		// jumping
		if(jumping && !falling) {
			dy = jumpStart;
			falling = true;	
		}
		
		// falling
		if(falling) {
			
			
			dy += fallSpeed;
			
			if(dy > 0) jumping = false;
			if(dy < 0 && !jumping) dy += stopJumpSpeed;
			
			if(dy > maxFallSpeed) dy = maxFallSpeed;
			
		}
    }

    public void draw(Graphics2D g){

        setMapPosition();

        if(iFrame){
            long elapsed = (System.nanoTime()-iFrameCounter) / 1000000;
            if (elapsed/100 %2 ==0) {
                return;
            }
        }

        super.draw(g);
    }

    

}
