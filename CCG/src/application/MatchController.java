package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class MatchController {

	Match model;

	@FXML
	HBox playerHand;

	@FXML
	void initialize() {
		model = new Match();
		addHand();
	}
	
	void addCardToHand(PersistantCard toAdd, HBox hand) {
		try {
			URL url = getClass().getResource("CardView.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(url);
			fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
			StackPane page = (StackPane) fxmlLoader.load(url.openStream());
			
			page.getStylesheets().add(getClass().getResource("card.css").toExternalForm());

			hand.getChildren().add(page);
			
			CardController controller = fxmlLoader.<CardController>getController();
			controller.setModel(toAdd);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void addHand() {
		ArrayList<Card> hand = model.localPlayer.getHand();
		for (int i = 0; i < hand.size(); i++ ) {
			addCardToHand((PersistantCard) hand.get(i), playerHand);
		}

	}

}
