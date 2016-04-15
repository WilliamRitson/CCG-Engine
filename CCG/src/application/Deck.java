package application;

import java.util.ArrayList;

public class Deck {
	public Event<Card> onCardDraw;

	private ArrayList<Card> cards;

	/**
	 * @param newCards - Cards to put into the new deck
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

}
