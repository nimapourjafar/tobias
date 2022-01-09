package Tiles;

import java.awt.Color;
import java.awt.Graphics2D;

import Entity.Player;

public class HUD {
    private Player player;

    public HUD(Player p) {
		player = p;
	}

    public void draw(Graphics2D g){
        g.setColor(Color.WHITE);
		g.drawString(
			"Health: "+player.getHealth() + "/" + player.getMaxHealth(),
			30,
			25
		);
		g.drawString(
			"Money: "+player.getMoney()  + "/" + player.getMaxMoney() ,
			30,
			45
		);

        g.drawString(
			"Gas: "+player.getGas()  + "/" + player.getMaxGas() ,
			30,
            65
		);

    }
}
