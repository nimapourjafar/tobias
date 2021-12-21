package GameState;

import java.util.ArrayList;

// Game state manager
public class GameStateManager {
    // Array of GameState objects
    private ArrayList<GameState> gameStates;
    // Current state indicated by index
    private int currentState;
    
    // Global variables for the state index
    public int MENUSTATE = 0;
    public int LEVEL1STATE = 1;

    public GameStateManager(){
        // initialize the game State array
        gameStates = new ArrayList<GameState>();
        currentState = MENUSTATE;
        // add a new game state to the array with gsm as the parameter
        gameStates.add(new MenuState(this));
    }
    
    public void setState(int state){
        // set the current state to a different state
        currentState = state;
        // call the init method of the new state
        gameStates.get(currentState).init();
    }

    public void update(){
        // update the current state
        gameStates.get(currentState).update();
    }

    public void draw(java.awt.Graphics2D g){
        // draw the current state and pass the graphics object we're using from the main class
        gameStates.get(currentState).draw(g);
    }

    public void keyPressed(int k){
        // pass the key pressed to the current state
        gameStates.get(currentState).keyPressed(k);
    }
    public void keyReleased(int k){
        // pass the key released to the current state
        gameStates.get(currentState).keyReleased(k);
    }
}
