package group19.controller;

public class GameStateController {
	private int currentState;
	public double BGMvolume; //volume controls placed here because every scene has an instance of GSC
	public double SFXvolume;
	public static final int MENU = 0;
	public static final int GAME_IN_PROGRESS = 1;
	public static final int GAME_COMPLETE = 2;
	public static final int GAME_PAUSED = 3;
	
	public GameStateController() {
		setGameState(MENU); // initial state is MENU
		BGMvolume = 0.6; //start volume at a normal volume 
		SFXvolume = 0.6;
	}

	public void setGameState(int gameState) {
		currentState = gameState;
	}
	public int getCurrentGameState() {
		return currentState;
	}
	public boolean isFinished() { 
		if (currentState == GAME_COMPLETE) {
			return true;
		}
		else return false;
	}

	public double getBGMVolume() {
		return BGMvolume;
	}
	public double getSFXVolume() {
		return SFXvolume;
	}
	
	public void setBGMVolume(double value) { 
		BGMvolume = value;
	}
	
	public void setSFXVolume(double value) {
		SFXvolume = value;
	}

}
	
