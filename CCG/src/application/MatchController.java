package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.Button;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * The Class MatchController.
 * 
 * This class is used to control the Match UI which governs a single game.
 */
public class MatchController {

	/**
	 * The model of the Match. This object stores all the game logic behind the
	 * match
	 */
	Match model;

	/** A UI element used to display the players hand */
	@FXML
	HBox playerHand;
	
	@FXML
	HBox opponentHand;
	
	/** The UI for the opponent's field. */
	@FXML
	HBox opponentField;

	/** The UI for the local player's field. */
	@FXML
	HBox localPlayerField;
	
	@FXML
	Button endTurn;	

	/**
	 * Initialize the controller. This method is automatically called when the
	 * FXML file is built. It constructs a new model and adds events to the UI
	 */
	@FXML
	void initialize() {
		model = new Match();
		localPlayerField.setOnDragOver(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				cardOver(event);
			}
		});
		localPlayerField.setOnDragDropped(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				cardPlayed(event);
			}
		});
		update();
	}
	
	@FXML 
	public void endTurn(MouseEvent event) {
		System.out.println("end turn");
		model.endTurn();
		update();
	}
	
	/**
	 * This is triggered when a card is dragged over the local players field (eg
	 * to play it).
	 *
	 * @param event
	 *            the event
	 */
	private void cardOver(DragEvent event) {
		Dragboard db = event.getDragboard();
		String cardID = db.getString();
		Card playedCard = null;

		for (Card c : model.localPlayer.getHand()) {
			if (cardID == c.getID())
				playedCard = c;
		}
		if (playedCard == null)
			return;

		event.acceptTransferModes(TransferMode.MOVE);
		event.consume();
	}

	/**
	 * This handler is triggered when the local player finishes dragging and
	 * dropping a card onto the field. It handles the actual logic for playing
	 * the card.
	 *
	 * @param event
	 *            the event
	 */
	private void cardPlayed(DragEvent event) {
		Dragboard db = event.getDragboard();

		String cardID = db.getString();
		Card playedCard = null;

		for (Card c : model.localPlayer.getHand()) {
			System.out.println(cardID + " == " + c.getID());

			if (cardID == c.getID())
				playedCard = c;
		}

		if (playedCard == null)
			return;

		playedCard.playCard();
		update();
		event.setDropCompleted(true);
		event.consume();
	}

	/**
	 * This method updates the UI with the current cards.
	 */
	void update() {
		System.out.printf("Update %d in hand %d in play \n", model.localPlayer.getHand().size(),
				model.localPlayer.getBattlefield().getCards().size());

		System.out.println("Hand:" + model.localPlayer.getHand().stream().map((Card c) -> c.getName())
				.collect(Collectors.joining(", ", "{", "}")));

		System.out.println("Play:" + model.localPlayer.getBattlefield().getCards().stream().map((Card c) -> c.getName())
				.collect(Collectors.joining(", ", "{", "}")));

		playerHand.getChildren().clear();
		addPlayerCharacter(model.localPlayer.getPlayerChar(), playerHand);
		addCards(model.localPlayer.getHand(), playerHand);
		localPlayerField.getChildren().clear();
		addCards(localPlayerField, model.localPlayer.getBattlefield().getCards());
		
		opponentHand.getChildren().clear();
		addPlayerCharacter(model.opponent.getPlayerChar(), opponentHand);
	}

	/**
	 * Adds multiple cards to a certain area of the UI
	 *
	 * @param target
	 *            the part of the UI to append the cards to
	 * @param cards
	 *            the cards to append
	 */
	void addCards(Pane target, ArrayList<Minion> cards) {
		for (Minion card : cards) {
			addCardTo(card, target);
		}
	}

	/**
	 * Adds multiple cards to a certain area of the UI
	 *
	 * @param target
	 *            the part of the UI to append the cards to
	 * @param cards
	 *            the cards to append
	 */
	void addCards(ArrayList<Card> cards, Pane target) {
		for (Card card : cards) {
			addCardTo(card, target);
		}
	}

	/**
	 * Adds a card to a part of the UI.
	 *
	 * @param toAdd
	 *            the card to add
	 * @param target
	 *            the UI element ot append the card to
	 */
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

	void addPlayerCharacter(PlayerCharacter toAdd, Pane target) {
		try {
			URL url = getClass().getResource("PlayerCharView.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(url);
			fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
			Pane page = (Pane) fxmlLoader.load(url.openStream());

			target.getChildren().add(page);

			PlayerCharacterController controller = fxmlLoader.<PlayerCharacterController> getController();
			controller.setModel(toAdd);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
