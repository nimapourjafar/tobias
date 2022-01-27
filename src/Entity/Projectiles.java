package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import Tiles.TileMap;

// class to handle a projectile
// extends entity class
public class Projectiles extends Entity {
    // boolean to check if projectile has collided with anything
    private boolean collision;
    // image for sprite
    private BufferedImage sprite;
    // takes in tile map, the direction the projectile is facing, and the path to the image
    public Projectiles(TileMap tm, boolean right, String path){
        super(tm);
        // movement variable
        moveSpeed = 3.8;
        // declare the vector variable, if going right it's positive, if going left it's negative
        if (right) {
            dx = moveSpeed;
        }
        else{
            dx = -1* moveSpeed;
        }
        // dimension variables
        width = 15;
        height = 8;
        cwidth = 14;
        cheight = 14;
        // declare sprite
        try{
            sprite =  ImageIO.read(new File(path));
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    // set the projectile has hit if it collided with something
    public void setHit(){
        collision = true;
    }
    // returns if the projectile collided with something and to undraw it
    public boolean undrawFromGame(){
        return collision;
    }
    // update method
    public void update(){
        // set positions and check for tile collision
        checkTileMapCollision();
        setPosition(xtemp, ytemp);
        // if the x vector stops when it hits a wall, it means the projectile has collided with a tile
        if (dx==0 && !collision) {
            collision = true;
        }

        
    }

    // draw method
    public void draw(Graphics2D g){
        // set position on map
        setMapPosition();
        // draw the projectile
        if(facingRight) {
			g.drawImage(
				sprite,
				(int)(x + xmap - width / 2),
				(int)(y + ymap - height / 2),
				null
			);
		}
		else {
			g.drawImage(
				sprite,
				(int)(x + xmap - width / 2 + width),
				(int)(y + ymap - height / 2),
				-width,
				height,
				null
			);
		}
    }



}
