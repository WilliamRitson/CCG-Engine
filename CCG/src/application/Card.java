package application;

import java.io.File;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * The Interface Card.
 */
public abstract class Card {
	/** The cards unique instance id. Identifies a particular instance of the card */
	private String instanceId;
	
	/**  The unique id for all cards of the same type *. */
	private String cardId;
	
	/** The cards name. */
	private String name;
	
	/** The card's resource cost. */
	private IntegerProperty cost = new SimpleIntegerProperty() ;
	
	/**  The card text that explains its rules. */
	private String ruleText;
	
	/**  The player who owns the card. */
	private Player owner;
	
	/** The hero class which can use this card. */
	private String heroClass;
	
	/** The card's subtype. */
	private String subtype;
	
	/** Whether the card is collectable or not. */
	private boolean collectable;
	
	/** The set the card belongs to. */
	private String set;
	
	/** The rarity of the card. */
	private String rarity;

	/**
	 * Checks if the is collectible. Some cards such as tokens are not.
	 *
	 * @return true, if is collectible
	 */
	public boolean isCollectable() {
		return collectable;
	}

	/**
	 * Sets whether the card is collectible.
	 *
	 * @param collectable - whether the card is collectible or not
	 */
	@XmlElement
	public void setCollectable(boolean collectable) {
		this.collectable = collectable;
	}

	
	/**
	 * Gets the sets the card's set.
	 *
	 * @return the card's set
	 */
	public String getSet() {
		return set;
	}

	/**
	 * Sets the sets the.
	 *
	 * @param set the new sets the
	 */
	@XmlElement
	public void setSet(String set) {
		this.set = set;
	}

	/**
	 * Gets the card's rarity.
	 *
	 * @return the rarity (Common, Rare, Epic or Legendary)
	 */
	public String getRarity() {
		return rarity;
	}

	/**
	 * Sets the card's rarity..
	 *
	 * @param rarity the new the rarity (Common, Rare, Epic or Legendary)
	 */
	@XmlElement
	public void setRarity(String rarity) {
		this.rarity = rarity;
	}
	
	/**
	 * Instantiates a new card.
	 *
	 * @param name the name
	 * @param ruleText the rule text
	 * @param cost the cost
	 */
	public Card(String name, String ruleText, int cost) {
		this.name = name;
		this.cost.set(cost);
		this.ruleText = ruleText;
		this.rarity = "Common";
		this.set = "Classic";
		this.collectable = true;
		heroClass = "neutral";
		subtype = "none";
		this.instanceId = name + ":" + UUID.randomUUID();
	}
	
	/**
	 * Load a card from a xml file.
	 *
	 * @param source the source
	 * @return the card
	 */
	public static Card loadFromXML(File source) {
		return Minion.loadFromXML(source);
	}
	
	/**
	 * Save to a xml file.
	 *
	 * @param toSave the to save
	 * @param destination the destination
	 */
	public static void saveToXML(Card toSave, File destination) {
		if (toSave instanceof Minion)
			Minion.saveToXML((Minion) toSave, destination);
	}
	
	/**
	 * Gets the card's rule text.
	 *
	 * @return the card's rule text
	 */
	public String getRuleText() {
		return ruleText;
	}

	/**
	 * Sets the card's rule text.
	 *
	 * @param ruleText the new rule text
	 */
	@XmlElement
	public void setRuleText(String ruleText) {
		this.ruleText = ruleText;
	}

	/**
	 * Sets the card's cost.
	 *
	 * @param cost the card's new cost
	 */
	@XmlElement
	public void setCost(int cost) {
		this.cost.set(cost);
	}
	
	/**
	 * Gets the card's name.
	 *
	 * @return The name of the card
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the card's cost.
	 *
	 * @return The resource cost of the card
	 */
	public int getCost() {
		return cost.get();
	}
	
	public IntegerProperty getCostProperty() {
		return cost;
	}

	/**
	 * Checks if the card is playable.
	 *
	 * @return True if the player can play this card (they have enough
	 *         resources, and any other factors that must be taken into
	 *         consideration, such as a valid target)
	 */
	public boolean isPlayable() {
		return owner.getResource() >= cost.get();
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
	 * @param newOwner the new owner
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
		return instanceId;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the hero class.
	 *
	 * @param heroClass the new hero class
	 */
	@XmlElement
	public void setHeroClass(String heroClass) {
		this.heroClass = heroClass;
	}
	
	/**
	 * Gets the hero class.
	 *
	 * @return the hero class
	 */
	public String getHeroClass() {
		return heroClass;
	}

	/**
	 * Gets the card's subtype.
	 *
	 * @return the card's subtype
	 */
	@XmlElement
	public String getSubtype() {
		return subtype;
	}

	/**
	 * Sets the card's subtype.
	 *
	 * @param subtype the new subtype
	 */
	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

	/**
	 * Gets the card's global id.
	 *
	 * @return the cardId
	 */
	public String getCardId() {
		return cardId;
	}

	/**
	 * Sets the card's global id.
	 *
	 * @param cardId the new value of the CardID
	 */
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}; 
}
