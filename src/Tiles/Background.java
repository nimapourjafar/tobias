package Tiles;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import Game.GamePanel;

// class for background image
public class Background {
    // image
    private BufferedImage image;
    // position of background
    private double x;
    private double y;
    // directional values
    private double dx;
    private double dy;
    // move scale variable
    private double moveScale;

    public Background() {
        // set move scale
        moveScale = 1.2;
        try{
            // load images
            image =  ImageIO.read(new File("./assets/background/background.png"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    // set position of background image
    public void setPosition(double x, double y) {
        this.x = (x * moveScale) % GamePanel.WIDTH;
        this.y = (y * moveScale) % GamePanel.HEIGHT;
    }
    // set vector of bacgkround image
    public void setVector(double dx, double dy){
        this.dx = dx;
        this.dy = dy;
    }
    // update method
    public void update(){
        x+=dx;
        y+=dy;
    }

    public void draw(Graphics2D g){
        // draw image
        g.drawImage(image, (int)x, (int)y, null);
        // if image is going to the left of the screen, draw another at the right
        if(x<0){
            g.drawImage(image, (int)x+GamePanel.WIDTH, (int)y, null);
        }
        // if the image is going to the right of the rightt, draw another at the left
        if(x>0){
            g.drawImage(image, (int)x-GamePanel.WIDTH, (int)y, null);
        }
    }

}
