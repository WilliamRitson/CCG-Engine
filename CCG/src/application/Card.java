package application;

import java.io.File;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;

/**
 * The Interface Card.
 */
public abstract class Card {
	/** The cards unique id. */
	private String id;
	
	/** The cards name. */
	private String name;
	
	/** The card's resource cost. */
	private int cost;
	
	/** The card text that explains its rules */
	private String ruleText;
	
	/** The player who owns the card */
	private Player owner;
	
	public Card(String name, String ruleText, int cost) {
		this.name = name;
		this.cost = cost;
		this.ruleText = ruleText;
		this.id = name + ":" + UUID.randomUUID();
	}
	
	public static Card loadFromXML(File source) {
		return Minion.loadFromXML(source);
	}
	
	public static void saveToXML(Card toSave, File destination) {
		if (toSave instanceof Minion)
			Minion.saveToXML((Minion) toSave, destination);
	}

	
	public String getRuleText() {
		return ruleText;
	}

	@XmlElement
	public void setRuleText(String ruleText) {
		this.ruleText = ruleText;
	}

	@XmlElement
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return The name of the card
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the cost.
	 *
	 * @return The resource cost of the card
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * Checks if is playable.
	 *
	 * @return True if the player can play this card (they have enough
	 *         resources, and any other factors that must be taken into
	 *         consideration, such as a valid target)
	 */
	public boolean isPlayable() {
		return owner.getResource() >= cost;
	}

	/**
	 * Gets the owner.
	 *
	 * @return Returns the owner of the card
	 */
	public Player getOwner() {
		return owner;
	}

	/**
	 * Plays the card.
	 */
	public void playCard() {
		getOwner().getHand().remove(this);
		getOwner().setResource(getOwner().getResource() - getCost());
	};
	
	/**
	 * Gets the display text.
	 *
	 * @return The text to be displayed on the card
	 */
	public String getDisplayText() {
		return ruleText;
	};

	/**
	 * Sets the player who owns this card.
	 *
	 * @param player the new owner
	 */
	public void setOwner(Player newOwner) {
		owner = newOwner;
	}
	
	/**
	 * Get a unique ID for the card. Card IDs are in the format CardName:Random Unique ID.
	 *
	 * @return the id
	 */
	public  String getID() {
		return id;
	}

	@XmlElement
	public void setName(String name) {
		this.name = name;
	}; 

}
