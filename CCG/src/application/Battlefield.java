package application;

import java.util.ArrayList;

/**
 * The Class Battlefield.
 * 
 * This class models the part of the bored controlled by a single player.
 */
public class Battlefield {
	
	/** The cards on the field. */
	private ArrayList<PersistantCard> entities;
	
	/**
	 * Gets an array list containing the cards on the battlefield.
	 *
	 * @return the cards
	 */
	public ArrayList<PersistantCard> getCards() {
		return entities;
	}
	
	/**
	 * Instantiates a new empty battlefield.
	 */
	public Battlefield() {
		entities = new ArrayList<PersistantCard>();
	}
	
	/**
	 * Adds an entity to the field.
	 *
	 * @param entity the entity to add
	 */
	public void addEntity(PersistantCard entity) {
		entities.add(entity);
	}

	/**
	 * Checks if the battlefield contains a certain card.
	 *
	 * @param card the card
	 * @return returns true if the card is present
	 */
	public boolean contains(PersistantCard card) {
		return entities.contains(card);	
	}
	
	/**
	 * Checks if the battlefield contains a  card with the specified ID.
	 *
	 * @param cardId the id to check for
	 * @return true, if a card with the id is present
	 */
	public boolean containsCardWithID(String cardId) {
		for(Card c: entities) {
			if (c.getID() == cardId)
				return true;
		}
		return false;
	}


}
