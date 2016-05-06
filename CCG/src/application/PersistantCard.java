package application;

import java.io.Serializable;
import java.util.UUID;

/**
 * The Class PersistantCard.
 * 
 * This class represents a playable card such as a minion
 */
public class PersistantCard implements Card, Attackable, Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4307839066400531082L;

	/** The card's owner. */
	private Player owner;

	/** The card's current life. */
	private int life;

	/** The attack damage. */
	private int damage;

	/** The card's displayed name. */
	private String name;

	/** The card's rule text. */
	private String ruleText;

	/** The card's resource cost. */
	private int cost;

	/** Whether the card is in play. */
	private boolean inPlay;

	/**  An event that is triggered when the card is damaged. */
	private Event<ValueChange> onDamaged;

	/** The cards unique id. */
	private String id;

	/**
	 * Constructs a new card.
	 *
	 * @param name
	 *            - Name of the card to be displayed to the player
	 * @param ruleText
	 *            - The card's rule text
	 * @param cost
	 *            - The resource cost of the card
	 * @param life
	 *            - The amount of life the card has when played
	 * @param damage
	 *            - The damage the card does when played
	 */
	public PersistantCard(String name, String ruleText, int cost, int life, int damage) {
		this.name = name;
		this.cost = cost;
		this.life = life;
		this.damage = damage;
		this.ruleText = ruleText;
		this.id = name + ":" + UUID.randomUUID();

		onDamaged = new Event<ValueChange>();
	}

	/**
	 * Constructs a new card with blank fields. Suitable for use in an editor.
	 */
	public PersistantCard() {
		this("", "", 0, 0, 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see application.Card#playCard()
	 */
	@Override
	public void playCard() {
		System.out.println("Play " + this.name + " owned by " + owner);
		this.owner.getHand().remove(this);
		this.owner.getBattlefield().addEntity(this);
		this.inPlay = true;
		owner.getOnSummon().fire(this);
		owner.setResource(owner.getResource() - this.cost);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see application.Card#getOwner()
	 */
	@Override
	public Player getOwner() {
		return owner;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see application.Card#setOwner(application.Player)
	 */
	public void setOwner(Player newOwner) {
		owner = newOwner;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see application.Damagable#getDamageEvent()
	 */
	@Override
	public Event<ValueChange> getDamageEvent() {
		return onDamaged;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see application.Damagable#getLife()
	 */
	@Override
	public int getLife() {
		return life;
	}

	/**
	 * Sets the card's current life.
	 *
	 * @param life
	 *            - The new amount of life for the card to have
	 */
	private void setLife(int life) {
		this.life = life;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see application.Damagable#takeDamage(int, application.Attackable)
	 */
	@Override
	public ValueChange takeDamage(int amount, Attackable source) {
		ValueChange hpChange = new ValueChange(this.life);
		this.setLife(this.getLife() - amount);
		hpChange.setNewVal(this.life);
		onDamaged.fire(hpChange);
		return hpChange;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see application.Attackable#getEffectiveDamage()
	 */
	@Override
	public int getEffectiveDamage() {
		return damage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see application.Attackable#defend(application.Attackable)
	 */
	@Override
	public ValueChange defend(Attackable attacker) {
		attacker.takeDamage(getEffectiveDamage(), this);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see application.Card#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see application.Card#getCost()
	 */
	@Override
	public int getCost() {
		return cost;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see application.Card#isPlayable()
	 */
	@Override
	public boolean isPlayable() {
		return owner.getResource() >= cost;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see application.Card#getDisplayText()
	 */
	@Override
	public String getDisplayText() {
		return ruleText;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see application.Card#getID()
	 */
	@Override
	public String getID() {
		return id;
	}

	/**
	 * Checks if the card is in play.
	 *
	 * @return true, if is in play
	 */
	public boolean isInPlay() {
		return inPlay;
	}

}
