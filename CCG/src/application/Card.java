package application;

public interface Card {

	/**
	 * @return The name of the card
	 */
	public String getName();

	/**
	 * @return The resource cost of the card
	 */
	public int getCost();

	/**
	 * @return True if the player can play this card (they have enough
	 *         resources, and any other factors that must be taken into
	 *         consideration, such as a valid target)
	 */
	public boolean isPlayable();

	/**
	 * @return Returns the owner of the card
	 */
	public Player getOwner();

	/**
	 * Plays the card.
	 */
	public void playCard();
	
	/**
	 * @return The text to be displayed on the card
	 */
	public String getDisplayText();

	public void setOwner(Player player);
	
	public String getID(); 

}
