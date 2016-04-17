package application;

import java.io.Serializable;
import java.util.UUID;

public class PersistantCard implements Card, Attackable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4307839066400531082L;

	private Player owner;

	private int life;
	private int damage;
	private String name;
	private String ruleText;
	private int cost;
	
	private Event<ValueChange> onDamaged;

	private String id;


	/**
	 * Constructs a new card
	 * @param name
	 *            - Name of the card to be displayed to the player
	 * @param owner
	 *            - Player who owns the card
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

	@Override
	public void playCard() {
		System.out.println("Play " + this.name + " owned by " + owner);
		this.owner.getHand().remove(this);
		this.owner.getBattlefield().addEntity(this);
		owner.onSummon.fire(this);
		owner.setResource(owner.getResource() - this.cost);
	}

	@Override
	public Player getOwner() {
		return owner;
	}
	
	public void setOwner(Player newOwner) {
		owner = newOwner;
	}

	@Override
	public Event<ValueChange> getDamageEvent() {
		return onDamaged;
	}

	@Override
	public int getLife() {
		return life;
	}

	/**
	 * @param life - The new amount of life for the card to have
	 */
	private void setLife(int life) {
		this.life = life;
	}


	@Override
	public ValueChange takeDamage(int amount, Attackable source) {
		ValueChange hpChange = new ValueChange(this.life);
		this.setLife(this.getLife() - amount);
		hpChange.setNewVal(this.life);
		onDamaged.fire(hpChange);
		return hpChange;
	}

	@Override
	public int getEffectiveDamage() {
		return damage;
	}

	@Override
	public ValueChange defend(Attackable attacker) {
		attacker.takeDamage(getEffectiveDamage(), this);
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getCost() {
		return cost;
	}

	@Override
	public boolean isPlayable() {
		return owner.getResource() >= cost;
	}

	@Override
	public String getDisplayText() {
		return ruleText;
	}

	@Override
	public String getID() {
		return id;
	}

}
