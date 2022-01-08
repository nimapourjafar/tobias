package Entity;

import Tiles.TileMap;
import java.awt.Graphics2D;

public class Money extends MapObject {

    private boolean collision;


    public Money(TileMap tm, boolean right){
        super(tm);

        moveSpeed = 3.8;
        if (right) {
            dx = moveSpeed;
        }
        else{
            dx = -1* moveSpeed;
        }

        width = 30;
        height = 30;
        cwidth = 14;
        cheight = 14;

        try{
            animation = new Animation();
            // TODO add fireball frames
            // animation.setFrames();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setHit(){
        collision = true;
    }

    public boolean undrawFromGame(){
        return collision;
    }

    public void update(){
        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        if (dx==0 && !collision) {
            collision = true;
        }

        animation.update();
        
    }

    public void draw(Graphics2D g){
        setMapPosition();
        super.draw(g);
    }



}
