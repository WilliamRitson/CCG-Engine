package application;

/**
 * The Interface Card.
 */
public interface Card {

	/**
	 * Gets the name.
	 *
	 * @return The name of the card
	 */
	public String getName();

	/**
	 * Gets the cost.
	 *
	 * @return The resource cost of the card
	 */
	public int getCost();

	/**
	 * Checks if is playable.
	 *
	 * @return True if the player can play this card (they have enough
	 *         resources, and any other factors that must be taken into
	 *         consideration, such as a valid target)
	 */
	public boolean isPlayable();

	/**
	 * Gets the owner.
	 *
	 * @return Returns the owner of the card
	 */
	public Player getOwner();

	/**
	 * Plays the card.
	 */
	public void playCard();
	
	/**
	 * Gets the display text.
	 *
	 * @return The text to be displayed on the card
	 */
	public String getDisplayText();

	/**
	 * Sets the player who owns this card.
	 *
	 * @param player the new owner
	 */
	public void setOwner(Player player);
	
	/**
	 * Get a unique ID for the card. Card IDs are in the format CardName:Random Unique ID.
	 *
	 * @return the id
	 */
	public String getID(); 

}
