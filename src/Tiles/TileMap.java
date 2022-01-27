package Tiles;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;

import javax.imageio.ImageIO;


import Game.GamePanel;
// the tile map represents the entire map of the game
public class TileMap {
	
	// position (top left of tile map)
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
	
	// tiles loaded from assets 
	private Tile[] tiles;
	
	// drawing
	private int rowOffset;
	private int colOffset;
	private int numRowsToDraw;
	private int numColsToDraw;
	
	// takes in tile size
	public TileMap(int tileSize) {
		// init tile size
		this.tileSize = tileSize;
		// calculate rows and cols to draw
		numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
		numColsToDraw = GamePanel.WIDTH / tileSize + 2;
		// load tiles from assets folder
        this.loadTiles();
	}
	
	public void loadTiles() {
		
		try {
			this.tiles = new Tile[19];
            BufferedImage tile;
			// get all tiles from assets folder
            tile = ImageIO.read(new File("./assets/tilemap/0.png"));
            tiles[0] = new Tile(tile,Tile.BLOCKED);
            tile = ImageIO.read(new File("./assets/tilemap/1.png"));
            tiles[1] = new Tile(tile, Tile.BLOCKED);
            tile = ImageIO.read(new File("./assets/tilemap/2.png"));
            tiles[2] = new Tile(tile, Tile.BLOCKED);
            tile = ImageIO.read(new File("./assets/tilemap/3.png"));
            tiles[3] = new Tile(tile, Tile.BLOCKED);
            tile = ImageIO.read(new File("./assets/tilemap/4.png"));
            tiles[4] = new Tile(tile, Tile.BLOCKED);
            tile = ImageIO.read(new File("./assets/tilemap/5.png"));
            tiles[5] = new Tile(tile, Tile.BLOCKED);
            tile = ImageIO.read(new File("./assets/tilemap/6.png"));
            tiles[6] = new Tile(tile, Tile.BLOCKED);
            tile = ImageIO.read(new File("./assets/tilemap/7.png"));
            tiles[7] = new Tile(tile, Tile.BLOCKED);
            tile = ImageIO.read(new File("./assets/tilemap/8.png"));
            tiles[8] = new Tile(tile, Tile.BLOCKED);
			tile = ImageIO.read(new File("./assets/tilemap/9.png"));
            tiles[9] = new Tile(tile, Tile.BLOCKED);
			tile = ImageIO.read(new File("./assets/tilemap/10.png"));
            tiles[10] = new Tile(tile, Tile.NORMAL);
			tile = ImageIO.read(new File("./assets/tilemap/11.png"));
            tiles[11] = new Tile(tile, Tile.NORMAL);
			tile = ImageIO.read(new File("./assets/tilemap/12.png"));
            tiles[12] = new Tile(tile, Tile.NORMAL);
			tile = ImageIO.read(new File("./assets/tilemap/13.png"));
            tiles[13] = new Tile(tile, Tile.NORMAL);
			tile = ImageIO.read(new File("./assets/tilemap/14.png"));
            tiles[14] = new Tile(tile, Tile.NORMAL);
			tile = ImageIO.read(new File("./assets/tilemap/15.png"));
            tiles[15] = new Tile(tile, Tile.BLOCKED);
			tile = ImageIO.read(new File("./assets/tilemap/16.png"));
            tiles[16] = new Tile(tile, Tile.BLOCKED);
			tile = ImageIO.read(new File("./assets/tilemap/17.png"));
            tiles[17] = new Tile(tile, Tile.BLOCKED);
			tile = ImageIO.read(new File("./assets/tilemap/18.png"));
            tiles[18] = new Tile(tile, Tile.BLOCKED);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	// method to change a specific tile
	public void changeTile(int row, int col,int token){
		map[row][col] = token;
	}
	
	// load the map file
	public void loadMap(String s) {
		
		try {
			
			Scanner sc = new Scanner(new File(s));
            sc.useDelimiter(",");
			// read in the num rows
			numRows = Integer.parseInt(sc.nextLine());
			// read in the num cols
			numCols = Integer.parseInt(sc.nextLine());
			// create a new map
			map = new int[numRows][numCols];
			// calculate width and height
			width = numCols * tileSize;
			height = numRows * tileSize;
			// calculate bounds
			xmin = GamePanel.WIDTH-width;
			xmax = 0;
			ymax =0;
			ymin = GamePanel.HEIGHT-height; 
			// loop through each row and add each token to the map object
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
	// getters
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
		// if token is -1, then there is no tile meaning entities can pass through it
		if (token==-1) {
			return Tile.NORMAL;
		}
		return tiles[token].getType();
	}
	// set position of the time map
	public void setPosition(double x, double y) {
		
		this.x += (x - this.x);
		this.y += (y - this.y);
		
		// make sure map doesn't go out of bounds
		fixBounds();

		// fix offset
		colOffset = (int)this.x *-1/ tileSize;
		rowOffset = (int)this.y *-1 / tileSize;
		
	}
	
	// method to fix the bounds of the tile map to make sure it doesn't go out of bounds
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
	
	// method to draw the tile map
	public void draw(java.awt.Graphics2D g) {
		// loop through rows from offset to the number of rows to draw on screen
		for( int row = rowOffset;row < rowOffset + numRowsToDraw; row++) {
			// make sure we don't go out of bounds
			if(row >= numRows){
				break;
			}
			// loop through cols from offset to the number of cols to draw on screen
			for(int col = colOffset; col < colOffset + numColsToDraw; col++) {
				// make sure we don't go out of bounds
				if(col >= numCols){
					break;
				}
				// get token
				int token = map[row][col];

				// if token is -1, that means there is no tile and we can ignore 
				if(token == -1){
					continue;
				}
				// draw the tile
				g.drawImage(
					tiles[token].getImage(),
					(int)x + col * tileSize,
					(int)y + row * tileSize,
					null
				);

				
			}
			
		}
		
	}
	
}

