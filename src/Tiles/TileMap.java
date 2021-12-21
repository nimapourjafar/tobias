package Tiles;

import java.io.File;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import Game.GamePanel;

public class TileMap {
    private double x;
    private double y;
    private int xmin;
    private int ymin;
    private int xmax;
    private int ymax;

    private int[][] map;
    private int tileSize;
    private int numRows;
    private int numCols;

    private int width;
    private int height;

    private BufferedImage tileset;
    private int numTilesAcross;
    private Tile[][] tiles;

    private int rowOffset;
    private int colOffset;
    private int numRowsToDraw;
    private int numColsToDraw;

    public TileMap(int tileSize){
        this.tileSize = tileSize;
        this.numRowsToDraw = GamePanel.HEIGHT/tileSize;
        this.numColsToDraw = GamePanel.HEIGHT/tileSize;
    }

    public void loadTiles(String s){

        try{
            this.tileset = ImageIO.read(new File(""));
            this.numTilesAcross = this.tileset.getWidth()/tileSize;
            this.tiles = new Tile[2][this.numTilesAcross];

            BufferedImage subimage;
            for (int i = 0; i < numTilesAcross; i++) {
                //TODO Fix so that tiles are just one long row
                subimage = tileset.getSubimage(i*tileSize, 0, tileSize, tileSize);
                this.tiles[0][i] = new Tile(subimage,Tile.NORMAL);
                subimage = tileset.getSubimage(i*tileSize, tileSize, tileSize, tileSize);
                this.tiles[1][i] = new Tile(subimage,Tile.BLOCKED) ;
            }

            
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    public void loadMap(String s){
        try{
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
