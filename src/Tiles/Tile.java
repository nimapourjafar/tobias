package Tiles;

import java.awt.image.BufferedImage;

// class for a tile
public class Tile {
    // image of the tyle
    private BufferedImage image;
    // type of tile
    private int type;
    // normal tiles can be passed through, blocked tiles can't
    public static int NORMAL = 0;
    public static int BLOCKED = 1;
    // init tile by passing in image and type
    public Tile(BufferedImage image, int type){
        this.image = image;
        this.type = type;
    }
    // getters
    public BufferedImage getImage() {
        return this.image;
    }
    
    public int getType() {
        return this.type;
    }

}
