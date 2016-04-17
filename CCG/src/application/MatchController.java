package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class MatchController {

	Match model;

	@FXML
	HBox playerHand;

	@FXML
	HBox opponentField;
	
	@FXML
	HBox localPlayerField;

	@FXML
	public void cardOver(DragEvent event) {
		Dragboard db = event.getDragboard();

		String cardName = db.getString();
		Card playedCard = null;
		System.out.println("name " + cardName);

		for (Card c : model.localPlayer.getHand()) {
			if (cardName == c.getName())
				playedCard = c;
		}
		//System.out.println("card " + playedCard);

		if (playedCard == null)
			return;
		// ((Object) field).setFill(Color.GREEN);
		// playedCard.playCard();

		// event.ac
		event.acceptTransferModes(TransferMode.MOVE);
		event.consume();
	}

	@FXML
	void initialize() {
		model = new Match();
		localPlayerField.setOnDragOver(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				System.out.println("do " + event);
				cardOver(event);
			}
		});
		localPlayerField.setOnDragDropped(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {

				Dragboard db = event.getDragboard();

				String cardName = db.getString();
				Card playedCard = null;
				System.out.println("name " + cardName);

				for (Card c : model.localPlayer.getHand()) {
					if (cardName == c.getName())
						playedCard = c;
				}
				System.out.println("setOnDragDropped card " + playedCard);

				if (playedCard == null)
					return;
				//update();

				playedCard.playCard();
				update();
				event.setDropCompleted(true);
				event.consume();
			}
		});
		update();
	}

	void update() {
		System.out.printf("Update %d in hand %d in play \n", model.localPlayer.getHand().size(),
				model.localPlayer.getBattlefield().getCards().size());
		
		 System.out.println("Hand:" + model.localPlayer.getHand().stream()
				 .map((Card c) -> c.getName())
				 .collect(Collectors.joining(", ", "{", "}")));
		 
		 System.out.println("Play:" + model.localPlayer.getBattlefield().getCards().stream()
				 .map((Card c) -> c.getName())
				 .collect(Collectors.joining(", ", "{", "}")));
		 
		playerHand.getChildren().clear();
		addCards(model.localPlayer.getHand(), playerHand);
		localPlayerField.getChildren().clear();
		addCards(localPlayerField, model.localPlayer.getBattlefield().getCards());
	}

	void addCards(Pane target, ArrayList<PersistantCard> cards) {
		for (PersistantCard card : cards) {
			addCardTo(card, target);
		}
	}

	void addCards(ArrayList<Card> cards, Pane target) {
		for (Card card : cards) {
			addCardTo(card, target);
		}
	}

	void addCardTo(Card toAdd, Pane target) {
		try {
			URL url = getClass().getResource("CardView.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(url);
			fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
			StackPane page = (StackPane) fxmlLoader.load(url.openStream());

			page.getStylesheets().add(getClass().getResource("card.css").toExternalForm());

			target.getChildren().add(page);

			CardController controller = fxmlLoader.<CardController> getController();
			controller.setModel(toAdd);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
