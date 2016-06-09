package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
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
public class MatchController extends ViewController{

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
	HBox playerField;
	
	@FXML
	Button endTurn;	

	/**
	 * Initialize the controller. This method is automatically called when the
	 * FXML file is built. It constructs a new model and adds events to the UI
	 */
	@FXML
	void initialize() {
		model = new Match();
		
		// Cards are always added to the player hand due to the current hot seat setup. This needs to change latter
		model.localPlayer.getDeck().onCardDraw.addWatcher((Event<Card> event, Card drawnCard) -> {
			addCardTo(drawnCard, playerHand);
		});
		model.opponent.getDeck().onCardDraw.addWatcher((Event<Card> event, Card drawnCard) -> {
			addCardTo(drawnCard, playerHand);
		});
		
		// Refactor this to make it happen from FXML
		playerField.setOnDragOver(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				cardOver(event);
			}
		});
		playerField.setOnDragDropped(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				cardPlayed(event);
			}
		});
		initilizeUI();
	}
	
	private void swapLists(HBox one, HBox two) {
		int oneSize = one.getChildren().size();
		int twoSize = two.getChildren().size();
		for (int i = 0; i < twoSize; i++) {
			moveCard(0, two, one);
		}
		for (int i = 0; i < oneSize; i++) {
			moveCard(0, one, two);
		}
	}
	
	private void flipUI() {
		swapLists(playerHand, opponentHand);
		swapLists(playerField, opponentField);		
	}
	
	@FXML 
	public void endTurn(MouseEvent event) {
		flipUI();
		model.endTurn();
		//update();
		
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
	
	int getCardIndex(ArrayList<Card> cards, String id) {
		for (int i = 0; i < cards.size(); i++) {
			if (cards.get(i).getID().equals(id))
				return i;
		}
		return -1;
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
		String cardId = event.getDragboard().getString();
		int index = getCardIndex(model.localPlayer.getHand(), cardId);
		if (index == -1)
			return;
		
		Card playedCard  = model.localPlayer.getHand().get(index);;
		playedCard.playCard();
		moveCard(index + 1, playerHand, playerField);
		event.setDropCompleted(true);
		event.consume();
	}
	
	void moveCard(int index, HBox from, HBox to) {
		to.getChildren().add(from.getChildren().remove(index));		
	}

	/**
	 * This method initially places all the UI elements such as cards and player avatars
	 */
	void initilizeUI() {
		System.out.printf("Update %d in hand %d in play \n", model.localPlayer.getHand().size(),
				model.localPlayer.getBattlefield().getCards().size());

		System.out.println("Hand:" + model.localPlayer.getHand().stream().map((Card c) -> c.getName())
				.collect(Collectors.joining(", ", "{", "}")));

		System.out.println("Play:" + model.localPlayer.getBattlefield().getCards().stream().map((Card c) -> c.getName())
				.collect(Collectors.joining(", ", "{", "}")));

		// Player hand
		playerHand.getChildren().clear();
		addPlayerCharacter(model.localPlayer.getPlayerChar(), playerHand);
		addCards(model.localPlayer.getHand(), playerHand);
		
		// Player Field
		playerField.getChildren().clear();
		addCards(playerField, model.localPlayer.getBattlefield().getCards());
		
		// Opponent Hand
		opponentHand.getChildren().clear();
		addPlayerCharacter(model.opponent.getPlayerChar(), opponentHand);
		addCards(model.opponent.getHand(), opponentHand);
		
		// Opponent Field
		opponentField.getChildren().clear();
		addCards(opponentField, model.localPlayer.getBattlefield().getCards());
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
	 *            the UI element to append the card to
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
