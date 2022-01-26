package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import Tiles.TileMap;

public class Projectiles extends MapObject {

    private boolean collision;


    public Projectiles(TileMap tm, boolean right, String path){
        super(tm);

        moveSpeed = 3.8;
        if (right) {
            dx = moveSpeed;
        }
        else{
            dx = -1* moveSpeed;
        }

        width = 15;
        height = 8;
        cwidth = 14;
        cheight = 14;

        try{
            animation = new Animation();
            BufferedImage moneyImage = ImageIO.read(new File("./assets/projectiles/0.png"));
            BufferedImage[] sprites = new BufferedImage[1];
            sprites[0] = moneyImage;
            animation.setFrames(sprites);
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
        if(facingRight) {
			g.drawImage(
				animation.getImage(),
				(int)(x + xmap - width / 2),
				(int)(y + ymap - height / 2),
				null
			);
		}
		else {
			g.drawImage(
				animation.getImage(),
				(int)(x + xmap - width / 2 + width),
				(int)(y + ymap - height / 2),
				-width,
				height,
				null
			);
		}
    }



}
