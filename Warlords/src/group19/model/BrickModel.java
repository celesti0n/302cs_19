package group19.model;

import group19.view.GameMenuView;
import javafx.scene.media.AudioClip;

// One "brick" in a wall, implements the interface IWall
public class BrickModel extends ObjectModel {
	boolean isDestroyed; // boolean holding state of brick 
	private int height;
	private int width;
	AudioClip brickBreak = new AudioClip(GameMenuView.class.getClassLoader().getResource("res/sounds/brick_break.wav").toString());
	// Constructor: Create ball at position (x,y) 
	public BrickModel(int x, int y) {
		super(x, y);
		isDestroyed = false; // initiate brick as not destroyed 
		width = 100;
		height = 50;
	}
	
	// get state of brick, i.e. whether or not it is destroyed
	public boolean isDestroyed() {
		return isDestroyed;
	}

	// destroy brick
	public void destroy() {
		isDestroyed = true;
		brickBreak.play();
		setXPos(1500);
	}
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
}