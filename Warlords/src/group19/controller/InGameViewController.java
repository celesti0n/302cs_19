package group19.controller;

import group19.testcases.IGame;
import group19.view.GameMenuView;
import group19.view.InGameView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import group19.model.*;

import javafx.animation.AnimationTimer;


//The in-game view controller is a user-input controller which listens to key events on the view, and updates model attributes 
//accordingly. The class also controls some game-wide controls, like whether the game is finished or the tick() mechanism to update the view.
//References to both the models (from 'GameModel') and the view are placed here.
public class InGameViewController implements IGame {
	//static GameModel game = new GameModel();
	static GameModel game;
	public static InGameView view;
	public static GameStateController gsc = new GameStateController(); //to control whether the game is complete, at menu, etc.
	public final Loop gameLoop = new Loop();
	//private int startTime; 
	private int remainingTime;
	private boolean isFinished;
	
	/*public InGameViewController() {
		super();
		view = new InGameView(1024,768, game);
		//startTime = 120; // gametime of 2 minutes 
		remainingTime = 120;
		gameLoop.start();
	}*/
	
	public InGameViewController(GameModel models) {
		super();
		view = new InGameView(1024,768, models);
		//startTime = 120; // gametime of 2 minutes 
		remainingTime = 120;
		gameLoop.start();
		game = models;
	}
	
	
	public class Loop extends AnimationTimer {
		private long lastTick = 0;
		
		@Override
		public void handle(long currentTime) {
			
			//private long lastTick = 0;
			
			if (lastTick == 0) { // first frame 
				lastTick = currentTime; 
				return;
			}			
			
			
			if (currentTime - lastTick >= 64000000) { // 64ms 
				KeyEventListener();		
				tick();
				lastTick = currentTime;
			}
			
			// TODO: timer actually counts down
			//remainingTime = startTime-(currentTime/1000000000);
			
			
		}
	}
	
	@Override
	public void tick() {
		//System.out.println("xpos before check:" + game.getBall().getXPos());
		game.getBall().moveBall();
		checkBallCollision(); 
		checkPaddleBounds();
		
		
		if (isFinished()) {
			if (remainingTime <= 0) {
				game.getWarlord1().setWinner();
				game.getWarlord2().setWinner(); // cuz everyone's a winner! :-) 
			}
			
			
			//gameLoop.stop();
		} 
		
		System.out.println("xpos in tick:" + game.getBall().getXPos());
	}

	@Override
	public boolean isFinished() {
		return ((remainingTime <= 0) || (game.getWarlord1().hasWon()) || game.getWarlord2().hasWon());
	}

	// I don't know why you'd need a method for this...
	@Override
	public void setTimeRemaining(int seconds) {
		remainingTime = seconds;		
	}
	
	/*Added function for debugging: pressing tab closes the in game window*/
	/*Listen for key input for paddle to move.*/
	public void KeyEventListener() {
		view.getScene().addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
	      if (key.getCode() == KeyCode.TAB) {
	          System.out.println("You pressed TAB, exiting and moving to main menu...");
	          gsc.setGameState(0);//back to menu state (game did not 'complete')
	          GameMenuView.getWindow().setScene(GameMenuView.getGameMenu());// switch back to main menu 
	      }
	      else if (key.getCode() == KeyCode.LEFT) {
	  		System.out.println("left pressed");
	  		game.getPaddle().setXPos(game.getPaddle().getXPos() - game.getPaddle().getXVelocity());
	      }
	      else if (key.getCode() == KeyCode.RIGHT) {
	  		System.out.println("right pressed");
	  		game.getPaddle().setXPos(game.getPaddle().getXPos() + game.getPaddle().getXVelocity());
	      }
	      });	
	}
	
	

	
	public void checkBallCollision() {
		//if (((game.getBall().getXPos())-(game.getBall().getRadius()) < 0)) { // hit left wall
		if (game.getBall().getXPos() < 0) {	
			game.getBall().setXPos(game.getBall().getRadius());
			game.getBall().bounceX();
			System.out.println("ball bounced");
		}
		
		if (((game.getBall().getXPos()+game.getBall().getRadius()) > 1024)) { // right left wall
			game.getBall().setXPos(1024-game.getBall().getRadius());
			game.getBall().bounceX();
		}
		
		if (((game.getBall().getYPos()+game.getBall().getRadius())) > 768) { // hit top wall 
			game.getBall().setYPos(768-game.getBall().getRadius());
			game.getBall().bounceY();
		}
		
		if ((game.getBall().getYPos()-game.getBall().getRadius()) < 0) { // hit bottom wall 
			game.getBall().setYPos(game.getBall().getRadius());
			game.getBall().bounceY();
		}
		
		if (InGameView.drawBall().intersects(InGameView.drawPaddle().getBoundsInLocal())) { 
			game.getBall().setYPos(game.getPaddle().getYPos()); // TODO: account for height
			game.getBall().bounceY();
		}
		
		// I don't know why I need to use getBoundsInParent() for this
		if (InGameView.drawBall().intersects(InGameView.drawWarlord1().getBoundsInLocal())) { 
			game.getWarlord1().setDead();
			game.getWarlord2().setWinner();
		}
		
		if (InGameView.drawBall().intersects(InGameView.drawWarlord2().getBoundsInParent())) { 
			game.getWarlord2().setDead();
			game.getWarlord1().setWinner();
		}
		
		if (InGameView.drawBall().intersects(InGameView.drawBrick().getBoundsInLocal())) { 
			game.getBall().setYPos(game.getBrick().getYPos());
			game.getBall().bounceY();
			game.getBrick().destroy(); // TODO: remove brick from view 
			game.getWarlord1().addScore();
		}
	}
	
	public void checkPaddleBounds() {
		if ((game.getPaddle().getXPos()-((game.getPaddle().getWidth())/2) < 0)) { // hit left wall
			game.getPaddle().setXPos(((game.getPaddle().getWidth())/2));
		}
		
		if (((game.getPaddle().getXPos()+((game.getPaddle().getWidth())/2)) > 1024)) { // hit right wall
			game.getPaddle().setXPos((1024 - (game.getPaddle().getWidth())/2));
		}
		
		if ((game.getPaddle().getYPos()-((game.getPaddle().getWidth())/2) < 0)) { // hit bottom wall
			game.getPaddle().setYPos((game.getPaddle().getWidth())/2);
		}
		
		if (((game.getPaddle().getYPos()+((game.getPaddle().getWidth())/2)) > 768)) { // hit top wall
			game.getPaddle().setYPos((game.getPaddle().getWidth())/2);
		}
		
		// TODO: paddle shouldn't be able to move out of each player's bounds
	}
}
