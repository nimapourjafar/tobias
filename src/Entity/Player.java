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

    private ArrayList<BufferedImage> sprites;

    

    public Player(TileMap tm) {
        super(tm);


        width = 30;
		height = 50;
		cwidth = 20;
		cheight = 30;
		
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
		
		moneyDamage = 2;
		moneyOnScreen = new ArrayList<Money>();
		
		attackDamage = 5;
		attackRange = 40;
        
        gas = maxGas = 100;

        currentAction = IDLE;


        try {
			sprites = new ArrayList<BufferedImage>();
            
            BufferedImage idleImage = ImageIO.read(new File("/Users/nimapourjafar/Documents/GitHub/tobias/assets/player/idle.png"));

            sprites.add(idleImage);
            
            
		}
		catch(Exception e) {
			e.printStackTrace();
		}

    }

    public void checkBossCollision(Boss boss){
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

        for (int i = 0; i < moneyOnScreen.size(); i++) {
            if (moneyOnScreen.get(i).intersects(boss)) {
                boss.hit(moneyDamage);
                moneyOnScreen.get(i).setHit();
                break;
            }
        }

        if (intersects(boss)) {
            if (carMode) {
                return;
            }
            else{
                hit(boss.getDamage());
            }
        }
    }

    public void checkEnemyCollision(ArrayList<Enemy> enemies){
        
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
            

            for(int j = 0; j < moneyOnScreen.size(); j++) {
				if(moneyOnScreen.get(j).intersects(e)) {
					e.hit(moneyDamage);
					moneyOnScreen.get(j).setHit();
					break;
				}
			}
			
			// check enemy collision
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

    public void hit(int damage) {
		if(dead || iFrame) return;
		health -= damage;
		if(health < 0) health = 0;
		if(health == 0) dead = true;
		iFrame = true;
		iFrameCounter = System.nanoTime();
	}


    public void shootMoney(boolean b){
        throwingMoney = b;
    }
    public void setCarMode(boolean b){
        carMode = b;
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


        if (gas<maxGas && carMode==false) {
            gas+=1;
        }
        if (carMode && gas>0) {
            moveSpeed = 1.5;
    		maxSpeed = 2.6;
            gas-=2;
            if (gas<=0) {
                carMode= false;
            }
        }
        else if(carMode==false){
            moveSpeed = 0.3;
		    maxSpeed = 1.6;
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

        if (attacking) {
            width =60;

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
            }
        }

        // animation.setFrames(sprites.get(currentAction));
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

        if(facingRight) {
			g.drawImage(
				sprites.get(currentAction),
				(int)(x + xmap - width / 2 + width),
				(int)(y + ymap - height / 2),
				width,
				height,
				null
			);
			
		}
		else {
			g.drawImage(
				sprites.get(currentAction),
				(int)(x + xmap - width / 2),
				(int)(y + ymap - height / 2),
				-width,
				height,
				null
			);
		}
    }

    

}
