/* Methods and variables of the gameplay board (768x768 square) 
 * not sure if the whole 1024x768 frame should be here? for HUD/GUI purposes 
 * idk if we need this at this point i feel like it's easier if we put all the paddle/ball/bricks in one model class??? */ 
public class BoardModel {
	int width; // 1024 if including HUD? 
	int height;

	//constructor
	public BoardModel(int width_, int height_) {
		width = width_;
		height = height_;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	
}
