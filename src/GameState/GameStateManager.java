package GameState;

import java.util.ArrayList;

// Game state manager
public class GameStateManager {
	// array list of game states
	private ArrayList<GameState> gameStates;
	// index of current state
	private int currentState;
	
	// references to each state's index
	public final int MENUSTATE = 0;
	public final int LEVEL1STATE = 1;
	public final int HELPSTATE = 2;
	
	// init game state
	public GameStateManager() {
		
		// declare array list
		gameStates = new ArrayList<GameState>();
		
		// set current state to menu
		currentState = MENUSTATE;
		// add all states and pass THIS game state manager into them
		gameStates.add(new MenuState(this));
		gameStates.add(new LevelState(this));
		gameStates.add(new HelpState(this));
		
	}
	
	// method to set the game state state 
	public void setState(int state) {
		currentState = state;
		gameStates.get(currentState).init();
	}

	// all of these methods are passed from the GamePanel, to the GameStateManager, to the current GameState
	
	// update method to update the current state
	public void update() {
		gameStates.get(currentState).update();
	}
	// draw method to draw the current state
	public void draw(java.awt.Graphics2D g) {
		gameStates.get(currentState).draw(g);
	}
	// key methods for current state
	public void keyPressed(int k) {
		gameStates.get(currentState).keyPressed(k);
	}
	
	public void keyReleased(int k) {
		gameStates.get(currentState).keyReleased(k);
	}
	
}

