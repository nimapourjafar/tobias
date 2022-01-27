package Entity;

import java.awt.image.BufferedImage;

// class to handle animations
public class Animation {
	
	// variables for the frames of the animation
	private BufferedImage[] frames;
	private int currentFrame;
	
	// variables to keep track of time for animation
	private long startTime;
	private long delay;
	

	// method to set the classes frames by getting an array of images
	public void setFrames(BufferedImage[] frames) {
		this.frames = frames;
		// set current frame to the first frame
		currentFrame = 0;
		// set the start time to now
		startTime = System.nanoTime();

	}
	// Setter for setting the animation delay
	public void setDelay(long d) { delay = d; }
	
	// update function to update the animation
	public void update() {
		
		if(delay == -1) return;
		// calculate time since last frame
		long elapsed = (System.nanoTime() - startTime) / 1000000;
		// if time difference is greater than the animation delay, we move to the next frame and set the animation timer to now
		if(elapsed > delay) {
			currentFrame++;
			startTime = System.nanoTime();
		}
		// wrap around the frames and go back to the first frame if we've gone through the entire frame array
		if(currentFrame == frames.length) {
			currentFrame = 0;
		}
		
	}
	
	// getter for the current image
	public BufferedImage getImage() { return frames[currentFrame]; }
	
}