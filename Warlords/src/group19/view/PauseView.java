package group19.view;

import group19.controller.GameStateController;
import group19.controller.InGameViewController;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PauseView {
	static GameStateController gsc = new GameStateController();
	static Scene pauseScene;
	public static int currentItem = 0;
	private static VBox menuBox;

	public static void showScene() {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL); //block input events in other windows 

		Pane layout = new Pane(); //settings for parent node - bg, color, size
		BackgroundFill bg = new BackgroundFill(Color.BLACK, null, null);
		layout.setBackground(new Background(bg));
		layout.setPrefWidth(1024);
		layout.setPrefHeight(768);
		Text title = new Text(200, 300, "- game is paused -"); //settings for title - position, alignment, size/color
		title.setTextAlignment(TextAlignment.CENTER);
		title.setFont(new Font(70));
		title.setFill(Color.ANTIQUEWHITE);

        menuBox = new VBox(10, //settings for menuBox (helper functions below) - spacing, position
                new MenuItem("resume game"),
                new MenuItem("options"),
                new MenuItem("credits"),
                new MenuItem("quit game")
        		);
        menuBox.setTranslateX(235); 
        menuBox.setTranslateY(358);

        AudioClip menuSelect = new AudioClip(GameMenuView.class.getClassLoader().getResource("res/sounds/menu_select.wav").toString());
        AudioClip modeSelect = new AudioClip(GameMenuView.class.getClassLoader().getResource("res/sounds/game_start.wav").toString());
        menuSelect.setVolume(gsc.getSFXVolume());  //set volume of SFX according to gsc attribute
        modeSelect.setVolume(gsc.getSFXVolume());
        getMenuItem(0).setActive(true); //highlight first item in menu
		layout.getChildren().addAll(title, menuBox);
		pauseScene = new Scene(layout);
		window.setResizable(false);
		window.setScene(pauseScene);
		window.sizeToScene();
		window.show();
		//keypress functionality and menu navigation
        pauseScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) {
                if (currentItem > 0) { //swap active options, play sound
                    getMenuItem(currentItem).setActive(false);
                    getMenuItem(--currentItem).setActive(true); 
                    menuSelect.play();
                    
                }
            }

            if (event.getCode() == KeyCode.DOWN) {
                if (currentItem < menuBox.getChildren().size() - 1) {
                    getMenuItem(currentItem).setActive(false);
                    getMenuItem(++currentItem).setActive(true);
                    menuSelect.play();
                }
            }

            if (event.getCode() == KeyCode.ENTER) {
                modeSelect.play();
                switch(currentItem) {
                case 0: //single player
                	gsc.setGameState(1); //1 = game_in_progress
                	System.out.println("resume game");
                	break;
                case 1: 
                	System.out.println("options");
                	break;
                case 2:
                	System.out.println("credits");
                	break;
                case 3:
                	System.out.println("quit game");
                	System.exit(0); //macro to close application
                	break;

                }
            }
        });
	}
    private static MenuItem getMenuItem(int index) {
        return (MenuItem)menuBox.getChildren().get(index); //cast MenuBox to MenuItem (since it consists of them)
    }
    private static class MenuItem extends HBox {
        private Text text;

        public MenuItem(String name) {
            text = new Text(name);
            text.setFont(Font.font("Arial", FontWeight.LIGHT, 40));
            getChildren().addAll(text);
            setActive(false); //apart from first element, rest of options are not active
        }
        public void setActive(boolean b) {
            text.setFill(b ? Color.ANTIQUEWHITE : Color.GREY); //if b = true (active option) then fill white 
        }

    }
}