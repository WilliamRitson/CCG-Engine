package application;

import java.util.ArrayList;

public class Player {
	private PlayerCharacter playerChar;
	private ArrayList<Card> hand;
	private Battlefield field;
	private Deck deck;

	public Event<PersistantCard> onSummon;
	private int resource;

	/**
	 * @return Returns - The players battlefield (where there cards are played
	 *         to)
	 */
	public Battlefield getBattlefield() {
		return field;
	}

	/**
	 * Starts the players turn.
	 */
	public void startTurn() {
		resource++;
	}

	/**
	 * Constructs a new player
	 * 
	 * @param newChar
	 *            - The character who will represent the player and be
	 *            attackable
	 * @param newDeck
	 *            - The players deck
	 */
	public Player(PlayerCharacter newChar, Deck newDeck) {
		playerChar = newChar;
		deck = newDeck;
		hand = new ArrayList<Card>(10);

		playerChar.getDamageEvent().addWatcher((Event<ValueChange> event, ValueChange vc) -> {
			if (vc.getNewVal() <= 0) {
				die();
			}
		});
	}

	/**
	 * Triggers this player to die ending the game
	 */
	private void die() {
		System.out.println("somone died");
	}

	/**
	 * Draws a card for this player
	 */
	public void drawCard() {
		hand.add(deck.draw());
	}

	/**
	 * Draws some number of cards for this player
	 * 
	 * @param num
	 *            - The number of cards to draw
	 */
	public void drawCards(int num) {
		for (int i = 0; i < num; i += 1)
			drawCard();
	}

	/**
	 * @return - A reference to the array list containing the cards in the
	 *         players hand
	 */
	public ArrayList<Card> getHand() {
		return hand;
	}

	/**
	 * @return - The amount of resources the player currently has
	 */
	public int getResource() {
		return resource;
	}

}
