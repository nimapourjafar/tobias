package GameState;

import java.awt.*;

public abstract class GameState {
    
    GameStateManager gsm;

    abstract public void init();
    abstract public void update();
    abstract public void draw(Graphics2D g);
    abstract public void keyPressed(int k);
    abstract public void keyReleased(int k);

}
