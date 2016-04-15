package application;

public class PersistantCard implements Card, Attackable {

	private Player owner;

	private int life;
	private int damage;
	private String name;
	private String ruleText;
	private int cost;

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
	public PersistantCard(String name, String ruleText,  Player owner, int cost, int life, int damage) {
		this.name = name;
		this.owner = owner;
		this.cost = cost;
		this.life = life;
		this.damage = damage;
		this.ruleText = ruleText;
	}

	/**
	 * Constructs a new card with blank fields. Suitable for use in an editor.
	 */
	public PersistantCard() {
		this("", "", null, 0, 0, 0);
	}

	@Override
	public void playCard() {
		owner.onSummon.fire(this);
		this.owner.getBattlefield().addEntity(this);
	}

	@Override
	public Player getOwner() {
		return owner;
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

	private Event<ValueChange> onDamaged;

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
		return owner.getResource() > cost;
	}

	@Override
	public String getDisplayText() {
		return ruleText;
	}

}
