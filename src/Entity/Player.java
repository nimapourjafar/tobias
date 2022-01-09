package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Tiles.TileMap;

public class Player extends MapObject {
    
    private int health;
    private int maxHealth;
    private int money;
    private int maxMoney;

    private boolean dead;



    private boolean throwingMoney;
    private ArrayList<Money> moneyOnScreen;
    private int moneyDamage;

    private boolean attacking;
    private int attackDamage;
    private int attackRange;

    private boolean iFrame;
    private long iFrameCounter;

    private boolean carMode;
    private int gas;
    private int maxGas;

    private int IDLE = 0;
    private int MONEY = 1;    
    private int CAR = 2;
    private int ATTACK = 3;

    private ArrayList<BufferedImage[]> sprites;

    

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
		maxMoney = 25;
        money = maxMoney;
		
		moneyDamage = 5;
		moneyOnScreen = new ArrayList<Money>();
		
		attackDamage = 8;
		attackRange = 40;


        try {
			sprites = new ArrayList<BufferedImage[]>();

            BufferedImage[] idle = new BufferedImage[1];
            idle[0] = ImageIO.read(new File("/Users/nimapourjafar/Documents/GitHub/tobias/assets/player/idle.png"));
            
            sprites.add(idle);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}

        animation = new Animation();
		currentAction = IDLE;
		
		animation.setDelay(400);
    }

    public void shootMoney(boolean b){
        throwingMoney = b;
    }
    public void setCarMode(){
        carMode = !carMode;
    }
    public void setAttacking(boolean b){
        attacking = b;
    }

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

    public void update(){

        getNextPosition();
        checkTileMapCollision(); 
        setPosition(xtemp, ytemp); 

        if (currentAction == ATTACK) {
            if (animation.hasPlayedOnce()) {
                attacking = false;
            }
        }
        if (currentAction==MONEY) {
            if (animation.hasPlayedOnce()) {
                throwingMoney = false;
            }
        }

        if (throwingMoney && currentAction != MONEY) {
            if (money>0) {
                money -=1;
                Money money = new Money(tileMap,facingRight);
                money.setPosition(x, y);
                moneyOnScreen.add(money);
            }
        }

        for (int i = 0; i < moneyOnScreen.size(); i++) {
            moneyOnScreen.get(i).update();
            if (moneyOnScreen.get(i).undrawFromGame()) {
                moneyOnScreen.remove(i);
                i--;
            }
        }


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
        else if(carMode){
            if (currentAction!=CAR) {
                currentAction= CAR;
            }
        }
        else{
            if (currentAction!=IDLE) {
                currentAction = IDLE;
                animation.setFrames(sprites.get(IDLE));
                
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

        for (int i = 0; i < moneyOnScreen.size(); i++) {
            moneyOnScreen.get(i).draw(g);
        }

        if(iFrame){
            long elapsed = (System.nanoTime()-iFrameCounter) / 1000000;
            if (elapsed/100 %2 ==0) {
                return;
            }
        }

        super.draw(g);
    }

    

}
