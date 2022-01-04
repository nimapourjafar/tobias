package Tiles;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;

import javax.imageio.ImageIO;

import Game.Game;
import Game.GamePanel;

public class TileMap {
	
	// position
	private double x;
	private double y;
	
	// bounds
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax;
	
	// map
	private int[][] map;
	private int tileSize;
	private int numRows;
	private int numCols;
	private int width;
	private int height;
	
	// tileset
	private BufferedImage tileset;
	private int numTilesAcross;
	private Tile[] tiles;
	
	// drawing
	private int rowOffset;
	private int colOffset;
	private int numRowsToDraw;
	private int numColsToDraw;
	
	public TileMap(int tileSize) {
		this.tileSize = tileSize;
		numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
		numColsToDraw = GamePanel.WIDTH / tileSize + 2;
        this.loadTiles();

		
	}
	
	public void loadTiles() {
		
		try {
			this.tiles = new Tile[16];
            BufferedImage tile;
            tile = ImageIO.read(new File("/Users/nimapourjafar/Documents/GitHub/tobias/assets/tilemap/0.png"));
            tiles[0] = new Tile(tile,Tile.BLOCKED);
            tile = ImageIO.read(new File("/Users/nimapourjafar/Documents/GitHub/tobias/assets/tilemap/1.png"));
            tiles[1] = new Tile(tile, Tile.BLOCKED);
            tile = ImageIO.read(new File("/Users/nimapourjafar/Documents/GitHub/tobias/assets/tilemap/2.png"));
            tiles[2] = new Tile(tile, Tile.BLOCKED);
            tile = ImageIO.read(new File("/Users/nimapourjafar/Documents/GitHub/tobias/assets/tilemap/3.png"));
            tiles[3] = new Tile(tile, Tile.NORMAL);
            tile = ImageIO.read(new File("/Users/nimapourjafar/Documents/GitHub/tobias/assets/tilemap/4.png"));
            tiles[4] = new Tile(tile, Tile.NORMAL);
            tile = ImageIO.read(new File("/Users/nimapourjafar/Documents/GitHub/tobias/assets/tilemap/5.png"));
            tiles[5] = new Tile(tile, Tile.NORMAL);
            tile = ImageIO.read(new File("/Users/nimapourjafar/Documents/GitHub/tobias/assets/tilemap/6.png"));
            tiles[6] = new Tile(tile, Tile.BLOCKED);
            tile = ImageIO.read(new File("/Users/nimapourjafar/Documents/GitHub/tobias/assets/tilemap/7.png"));
            tiles[7] = new Tile(tile, Tile.BLOCKED);
            tile = ImageIO.read(new File("/Users/nimapourjafar/Documents/GitHub/tobias/assets/tilemap/8.png"));
            tiles[8] = new Tile(tile, Tile.BLOCKED);
			tile = ImageIO.read(new File("/Users/nimapourjafar/Documents/GitHub/tobias/assets/tilemap/9.png"));
            tiles[9] = new Tile(tile, Tile.BLOCKED);
			tile = ImageIO.read(new File("/Users/nimapourjafar/Documents/GitHub/tobias/assets/tilemap/10.png"));
            tiles[10] = new Tile(tile, Tile.NORMAL);
			tile = ImageIO.read(new File("/Users/nimapourjafar/Documents/GitHub/tobias/assets/tilemap/11.png"));
            tiles[11] = new Tile(tile, Tile.NORMAL);
			tile = ImageIO.read(new File("/Users/nimapourjafar/Documents/GitHub/tobias/assets/tilemap/12.png"));
            tiles[12] = new Tile(tile, Tile.NORMAL);
			tile = ImageIO.read(new File("/Users/nimapourjafar/Documents/GitHub/tobias/assets/tilemap/13.png"));
            tiles[13] = new Tile(tile, Tile.NORMAL);
			tile = ImageIO.read(new File("/Users/nimapourjafar/Documents/GitHub/tobias/assets/tilemap/14.png"));
            tiles[14] = new Tile(tile, Tile.NORMAL);
			tile = ImageIO.read(new File("/Users/nimapourjafar/Documents/GitHub/tobias/assets/tilemap/15.png"));
            tiles[15] = new Tile(tile, Tile.NORMAL);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void loadMap(String s) {
		
		try {
			
			Scanner sc = new Scanner(new File(s));
            sc.useDelimiter(",");
			numRows = Integer.parseInt(sc.nextLine());
			numCols = Integer.parseInt(sc.nextLine());
			map = new int[numRows][numCols];
			width = numCols * tileSize;
			height = numRows * tileSize;

			xmin = GamePanel.WIDTH-width;
			xmax = 0;
			ymax =0;
			ymin = GamePanel.HEIGHT-height; 
			
			for(int row = 0; row < numRows; row++) {
				String line = sc.nextLine();
				String[] tokens = line.split(",");
				for(int col = 0; col < numCols; col++) {
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public int getTileSize() { 
		return tileSize; 
	}
	public int getX() { 
		return (int)x; 
	}
	public int getY() {
		 return (int)y; 
	}
	public int getWidth() { 
		return width;
	 }
	public int getHeight() { 
		return height; 
	}
	public int getNumRows() { 
		return numRows;
	 }
	public int getNumCols() { 
		return numCols; 
	}
	
	public int getType(int row, int col) {
		
		int token = map[row][col];
		if (token==-1) {
			return Tile.NORMAL;
		}
		return tiles[token].getType();
	}
	
	public void setPosition(double x, double y) {
		
		this.x += (x - this.x);
		this.y += (y - this.y);
		
		fixBounds();

		
		colOffset = (int)this.x *-1/ tileSize;
		rowOffset = (int)this.y *-1 / tileSize;
		
	}
	
	private void fixBounds() {
		if(x < xmin) {
			x = xmin;
		}
		if(y < ymin) {
			y = ymin;
		}
		if(x > xmax){
			x = xmax;
		}
		if(y > ymax){ 
			y = ymax;
		}
	}
	
	public void draw(java.awt.Graphics2D g) {
		
		for(
			int row = rowOffset;
			row < rowOffset + numRowsToDraw;
			row++) {
			
			if(row >= numRows) break;
			
			for(
				int col = colOffset;
				col < colOffset + numColsToDraw;
				col++) {
				
				if(col >= numCols) break;
				
				if(map[row][col] == -1) continue;
				
				int token = map[row][col];

				
				g.drawImage(
					tiles[token].getImage(),
					(int)x + col * tileSize,
					(int)y + row * tileSize,
					null
				);

				g.drawLine((int)x + col * tileSize, 0, (int)x + col * tileSize, GamePanel.HEIGHT);
				g.drawLine(0,(int)y + row * tileSize,GamePanel.WIDTH,(int)y + row * tileSize);
				
			}
			
		}
		
	}
	
}

