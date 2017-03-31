// One "brick" of a wall, implements the interface IWall 

package group19.model;

import group19.testcases.IWall;

public class BrickModel extends ObjectModel implements IWall {
	boolean isDestroyed; // boolean holding state of brick 
	private int height;
	private int width;
	// constructor 
	public BrickModel(int x, int y) {
		super(x, y);
		isDestroyed = false; // initiate wall as not destroyed 
		width = 15;
		height = 8;
	}
	
	@Override
	public void setXPos(int x) {
		super.setXPos(x);
	}
	
	@Override 
	public void setYPos(int y) {
		super.setYPos(y);
	}
	
	// get state of brick, i.e. whether or not it is destroyed
	@Override
	public boolean isDestroyed() {
		return isDestroyed;
	}

	// destroy brick
	public void destroy() {
		isDestroyed = true;
	}
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
}