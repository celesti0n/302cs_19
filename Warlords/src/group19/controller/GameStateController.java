package group19.controller;

import group19.view.GameMenuView;
import javafx.scene.media.AudioClip;

public class GameStateController {
	private int currentState;
	public static final int MENU = 0;
	public static final int GAME_IN_PROGRESS = 1;
	public static final int GAME_COMPLETE = 2;
	public static final int GAME_PAUSED = 3;
	public boolean isSinglePlayer;
    AudioClip gameOver = new AudioClip(GameMenuView.class.getClassLoader().getResource("res/sounds/game_over.wav").toString());
	
	public GameStateController() {
		setGameState(MENU); // initial state is MENU
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
	
	public void setSinglePlayer(boolean b) {
		if (b) {
			isSinglePlayer = true;
		}
		else isSinglePlayer = false;
	}
	public boolean getSinglePlayer() {
		return isSinglePlayer;
	}
	public void playGameOver() {
		gameOver.play();
	}

}
	
