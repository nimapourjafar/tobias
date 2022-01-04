package Entity;

import java.awt.image.BufferedImage;

public class Animation {
    private BufferedImage[] frames;
    private int currentFrame;
    private long startTime;
    private long delay;
    private boolean playedOnce;

    public Animation(){
        playedOnce = false;
    }

    public void setFrames(BufferedImage[] frames){
        this.frames = frames;
        currentFrame = 0;
        startTime= System.nanoTime();
        playedOnce = false;
    }

    public void setDelay(long d){
        delay = d;
    }

    public void setFrame(int i){
        currentFrame = i;
    }

    public void update(){
        if (delay==-1) {
            return;
        }

        long elapsed = System.nanoTime() - this.startTime / 1000000;

        if(elapsed>delay){
            currentFrame+=1;
            startTime = System.nanoTime();
        }
        if(currentFrame == frames.length){
            currentFrame= 0;
            playedOnce=  true;
        }
    }

    public BufferedImage getImage(){
        return frames[currentFrame];
    }

}
