package application;

import java.util.ArrayList;

/**
 * The Class Player.
 * 
 * This class represents a player of the game. It binds together their hand,
 * Battlefield, deck and avatar.
 */
public class Player {
	
	/** The total amount of resources a player can have **/
	static final int resourceLimit = 10; 

	/** The player's attackable avatar. */
	private PlayerCharacter playerChar;

	

	/** The player's hand. */
	private ArrayList<Card> hand;

	/** The player's battlefield. */
	private Battlefield field;

	/** The player's deck. */
	private Deck deck;

	/** An event that is triggered when the player summons something */
	public Event<PersistantCard> onSummon;

	/** The player's current resource amount. */
	private int resource;

	/** The player's maximum resource amount. */
	private int maxResource;
	
	public PlayerCharacter getPlayerChar() {
		return playerChar;
	}

	public int getMaxResource() {
		return maxResource;
	}

	public void setMaxResource(int maxResource) {
		this.maxResource = maxResource;
	}

	public void setResource(int resource) {
		this.resource = resource;
	}

	/**
	 * Gets the battlefield.
	 *
	 * @return Returns - The players battlefield (where there cards are played
	 *         to)
	 */
	public Battlefield getBattlefield() {
		return field;
	}

	/**
	 * Starts the players turn. This causes their maximum resource count to
	 * increase and their current resource to be restored to max;
	 */
	public void startTurn() {
		if (maxResource < resourceLimit)
			maxResource++;
		resource = maxResource;
	}

	/**
	 * Constructs a new player.
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
		onSummon = new Event<PersistantCard>();
		field = new Battlefield();
		
		for(Card card: deck.getCards()) {
			card.setOwner(this);
		}

		playerChar.setOwner(this);
		playerChar.getDamageEvent().addWatcher((Event<ValueChange> event, ValueChange vc) -> {
			if (vc.getNewVal() <= 0) {
				die();
			}
		});
	}

	/**
	 * Triggers this player to die ending the game.
	 */
	private void die() {
		System.out.println("somone died");
	}

	/**
	 * Draws a card for this player.
	 */
	public void drawCard() {
		hand.add(deck.draw());
	}

	/**
	 * Draws some number of cards for this player.
	 *
	 * @param num
	 *            - The number of cards to draw
	 */
	public void drawCards(int num) {
		for (int i = 0; i < num; i += 1)
			drawCard();
	}

	/**
	 * Gets the player's hand.
	 *
	 * @return - A reference to the array list containing the cards in the
	 *         players hand
	 */
	public ArrayList<Card> getHand() {
		return hand;
	}

	/**
	 * Gets the player's current resource level.
	 *
	 * @return - The amount of resources the player currently has
	 */
	public int getResource() {
		return resource;
	}

}
