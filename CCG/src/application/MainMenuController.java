package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class MainMenuController extends ViewController {

    @FXML
    private Button startButton;

    @FXML
    private Button gameEditerButton;
    
    @FXML
    private Button deckButton;

    @FXML
    void startCardEditor(MouseEvent event) {
    	//should enter the card control logic
    	changeView(View.CardEditor);
    }
    
    @FXML
    void startDeckEditor(MouseEvent event) {
        //enter game editor
    	
    }

    @FXML
    void startGame(MouseEvent event) {
    	//enter the game
    	changeView(View.Match);

    }


}
