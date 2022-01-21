package Entity;

import Tiles.TileMap;

public class Boss extends MapObject {

    private int health;
    private int maxHealth;
    private boolean dead;
	private int damage;
	

    public Boss(TileMap tm) {
        super(tm);

        health = maxHealth = 20;
		damage = 1;

    }
    
}
