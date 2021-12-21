package GameState;

// Game state manager
public class GameStateManager {
    // Array of GameState objects
    private GameState[] gameStates;
    // Current state indicated by index
    private int currentState;
    
    // Global variables for the state index
    public int MENUSTATE = 0;
    public int LEVEL1STATE = 1;

    public GameStateManager(){
        // initialize the game State array
        GameState[] gameStates = new GameState[3];
        currentState = MENUSTATE;
        gameStates[MENUSTATE] = new MenuState(this);
        
    }
    
    public void setState(int state){
        // set the current state to a different state
        currentState = state;
        // call the init method of the new state
        gameStates[currentState].init();
    }

    public void update(){
        // update the current state
        gameStates[currentState].update();
    }

    public void draw(java.awt.Graphics2D g){
        // draw the current state and pass the graphics object we're using from the main class
        gameStates[currentState].draw(g);
    }

    public void keyPressed(int k){
        // pass the key pressed to the current state
        gameStates[currentState].keyPressed(k);
    }
    public void keyReleased(int k){
        // pass the key released to the current state
        gameStates[currentState].keyReleased(k);
    }
}
