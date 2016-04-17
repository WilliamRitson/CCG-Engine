package application;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * The Class Deck.
 * 
 * This class is used to represent a players deck of cards. It wraps an
 * array list to enforce the constraints of the game rules and trigger watchable
 * events.
 */
public class Deck {

	/** The on card draw. */
	public Event<Card> onCardDraw;

	/** The cards. */
	private ArrayList<Card> cards;

	/**
	 * Instantiates a new deck.
	 *
	 * @param newCards
	 *            - Cards to put into the new deck
	 */
	public Deck(Card... newCards) {
		cards = new ArrayList<Card>(60);
		onCardDraw = new Event<Card>();
		for (int i = 0; i < newCards.length; i++) {
			cards.add(newCards[i]);
		}
	}

	/**
	 * Draws a card and triggers the onCardDrawn event.
	 * 
	 * @return The card that was drawn. Null if there are no cards left in the
	 *         deck.
	 */
	public Card draw() {
		Card drawn = cards.remove(0);
		if (drawn != null) {
			onCardDraw.fire(drawn);
		}
		return drawn;
	}

	public ArrayList<Card> getCards() {
		return cards;
	}
	
	public String toString() {
		return "Deck: " + cards.stream()
			     .map((Card c) -> c.getName())
			     .collect(Collectors.joining(", ", "{", "}"));
	}

}
