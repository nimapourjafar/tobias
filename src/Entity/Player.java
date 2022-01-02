package Entity;

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

    private boolean carMode;

    private int IDLE = 0;
    private int WALKING = 1;
    private int JUMPING = 2;
    private int FALLING = 3;
    private int MONEY = 4;    
    private int CAR = 5;
    private int ATTACK = 6;
    

}
