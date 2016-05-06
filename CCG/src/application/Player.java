package application;

import java.util.ArrayList;

/**
 * The Class Player.
 * 
 * This class represents a player of the game. It binds together their hand,
 * Battlefield, deck and avatar.
 */
public class Player {

	/** The total amount of resources a player can have *. */
	static final int resourceLimit = 10;

	/** The player's attackable avatar. */
	private PlayerCharacter playerChar;

	/** The player's hand. */
	private ArrayList<Card> hand;

	/** The player's battlefield. */
	private Battlefield field;

	/** The player's deck. */
	private Deck deck;

	/** An event that is triggered when the player summons something. */
	private Event<PersistantCard> onSummon;

	/** The player's current resource amount. */
	private int resource;

	/** The player's maximum resource amount. */
	private int maxResource;

	/** The current match. */
	private Match currentMatch;

	/**
	 * Gets the player char.
	 *
	 * @return the player char
	 */
	public PlayerCharacter getPlayerChar() {
		return playerChar;
	}

	/**
	 * Gets the player's resource max.
	 *
	 * @return the max resource
	 */
	public int getMaxResource() {
		return maxResource;
	}

	/**
	 * Sets the max resources. This does not limit the number of resources a
	 * player has, but rather decides what it will be reset to each turn. Eg a
	 * player can have 11/10 resources, but the next turn it will be reset to
	 * 10/10.
	 *
	 * @param maxResource
	 *            the new resource maximum
	 */
	public void setMaxResource(int maxResource) {
		this.maxResource = Math.max(Math.min(maxResource, resourceLimit), 0);
	}

	/**
	 * Sets the player's resources.
	 *
	 * @param resource
	 *            the player's new resource value
	 */
	public void setResource(int resource) {
		this.resource = Math.max(0, resource);
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
	 * Gets the player's opponent.
	 *
	 * @return the opponent
	 */
	public Player getOpponent() {
		for (Player player : currentMatch.players) {
			if (player != this)
				return player;
		}
		return null;
	}

	/**
	 * Gets this player's opponent's attackable targets.
	 *
	 * @return the opponent targets
	 */
	public ArrayList<Attackable> getOpponentTargets() {
		return getOpponent().getTargets();
	}

	/**
	 * Gets the valid attackable targets owned by this character.
	 *
	 * @return the targets
	 */
	public ArrayList<Attackable> getTargets() {
		ArrayList<Attackable> targets = new ArrayList<Attackable>(this.getBattlefield().getCards().size() + 1);
		targets.addAll(getBattlefield().getCards());
		targets.add(playerChar);
		return targets;
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
	 * @param match
	 *            the match
	 */
	public Player(PlayerCharacter newChar, Deck newDeck, Match match) {
		playerChar = newChar;
		deck = newDeck;
		hand = new ArrayList<Card>(10);
		onSummon = new Event<PersistantCard>();
		field = new Battlefield();
		currentMatch = match;

		for (Card card : deck.getCards()) {
			card.setOwner(this);
		}

		playerChar.setParent(this);
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

	/**
	 * @return An event that is triggered whenever this player summons a minion
	 */
	public Event<PersistantCard> getOnSummon() {
		return onSummon;
	}


}
