package Entity;

import java.awt.Rectangle;

import Game.GamePanel;
import Tiles.Tile;
import Tiles.TileMap;
// the entity class handles all the entities (or moving things) on the screen
// Entitiy class is abstract so that things like player, enemies, ect. can extend off of it
public abstract class Entity {
	
	// tile map variables
	protected TileMap tileMap;
	protected int tileSize;
	protected double xmap;
	protected double ymap;
	
	// position and vector
	protected double x;
	protected double y;
	protected double dx;
	protected double dy;
	
	// dimensions
	protected int width;
	protected int height;
	
	// collision box
	protected int cwidth;
	protected int cheight;
	
	// collision
	protected int currRow;
	protected int currCol;
	protected double xdest;
	protected double ydest;
	protected double xtemp;
	protected double ytemp;
	protected boolean topLeft;
	protected boolean topRight;
	protected boolean bottomLeft;
	protected boolean bottomRight;
	
	// for drawing
	protected boolean facingRight;
	
	// movement
	protected boolean left;
	protected boolean right;
	protected boolean up;
	protected boolean down;
	protected boolean jumping;
	protected boolean falling;
	
	// movement attributes
	protected double moveSpeed;
	protected double maxSpeed;
	protected double stopSpeed;
	protected double fallSpeed;
	protected double maxFallSpeed;
	protected double jumpStart;
	protected double stopJumpSpeed;
	
	// constructor that gets in current tilemap
	public Entity(TileMap tm) {
		tileMap = tm;
		tileSize = tm.getTileSize(); 
	}
	// method to check if the collision rectanlges of 2 entities intersects
	public boolean intersects(Entity o) {
		Rectangle r1 = getRectangle();
		Rectangle r2 = o.getRectangle();
		return r1.intersects(r2);
	}
	// method to return the collision box of an entity
	public Rectangle getRectangle() {
		return new Rectangle(
				(int)x - cwidth,
				(int)y - cheight,
				cwidth,
				cheight
		);
	}
	// method to check the tile corners of an entity and see if the tiles are blocked or normal
	public void calculateCorners(double x, double y) {
		// gets the rows and columns of the corner tiles (-1 was added for right and bottom because it kept bugging out)
		int leftTile = (int)(x - cwidth / 2) / tileSize;
		int rightTile = (int)(x + cwidth / 2 - 1) / tileSize;
		int topTile = (int)(y - cheight / 2) / tileSize;
		int bottomTile = (int)(y + cheight / 2 - 1) / tileSize;
		
		// gets the tile objects from the rows and columns 
		int tl = tileMap.getType(topTile, leftTile);
		int tr = tileMap.getType(topTile, rightTile);
		int bl = tileMap.getType(bottomTile, leftTile);
		int br = tileMap.getType(bottomTile, rightTile);
		
		// sets boolean values for whether or not the corner tiles are blocked
		topLeft = tl == Tile.BLOCKED;
		topRight = tr == Tile.BLOCKED;
		bottomLeft = bl == Tile.BLOCKED;
		bottomRight = br == Tile.BLOCKED;
		
	}
	
	// method to check entities collision with tiles
	public void checkTileMapCollision() {
		// get entities current col and row
		currCol = (int)x / tileSize;
		currRow = (int)y / tileSize;
		
		// calculate where the entity wants to go
		xdest = x + dx;
		ydest = y + dy;
		
		// set temporary variables
		xtemp = x;
		ytemp = y;
		
		// Calculate corners for y destination
		calculateCorners(x, ydest);
		if(dy < 0) {
			// if the entity is jumping and the top left or top right tiles are blocked, set the y momentum to 0 and place the entity a tile below where they hit their head
			if(topLeft || topRight) {
				dy = 0;
				ytemp = currRow * tileSize + cheight / 2;
			} 
			// otherwise continue incrementing the y momentum
			else {
				ytemp += dy;
			}
		}
		if(dy > 0) {
			// if the entity is falling and the bottom left or bottom right tiles are blocked, set the y momentum to 0, set the falling variable to false, and set the entity a tile above where they fell
			if(bottomLeft || bottomRight) {
				dy = 0;
				falling = false;
				ytemp = (currRow + 1) * tileSize - cheight / 2;
			}
			// otherwise continue incrementing the y momentum
			else {
				ytemp += dy;
			}
		}
		// Calculate corners for x destination
		calculateCorners(xdest, y);
		if(dx < 0) {
			if(topLeft || bottomLeft) {
				dx = 0;
				xtemp = currCol * tileSize + cwidth / 2;
			}
			else {
				xtemp += dx;
			}
		}
		if(dx > 0) {
			if(topRight || bottomRight) {
				dx = 0;
				xtemp = (currCol + 1) * tileSize - cwidth / 2;
			}
			else {
				xtemp += dx;
			}
		}
		// if the entity is not falling then calculate the corners the check if they should be falling
		if(!falling) {
			// calculate the tiles below the entity 
			calculateCorners(x, ydest + 1);
			// if they are normal then set the entity to be falling
			if(!bottomLeft && !bottomRight) {
				falling = true;
			}
		}
		
	}
	
	// getter methods
	public double getx() { return x; }
	public double gety() { return y; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public int getCWidth() { return cwidth; }
	public int getCHeight() { return cheight; }

	// setter methods
	
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	public void setMapPosition() {
		xmap = tileMap.getX();
		ymap = tileMap.getY();
	}


	
	public void setLeft(boolean b) { left = b; }
	public void setRight(boolean b) { right = b; }
	public void setUp(boolean b) { up = b; }
	public void setDown(boolean b) { down = b; }
	public void setJumping(boolean b) { jumping = b; }

	// method to check if entity is not on screen
	public boolean notOnScreen() {
		return x + xmap + width < 0 ||
			x + xmap - width > GamePanel.WIDTH ||
			y + ymap + height < 0 ||
			y + ymap - height > GamePanel.HEIGHT;
	}

}

