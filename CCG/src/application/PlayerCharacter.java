package application;

/**
 * The Class PlayerCharacter.
 * 
 * This represents a character avatar for a player which may be attacked.
 */
public class PlayerCharacter implements Attackable {

	/** The name. */
	String name;

	/** The character's current life. */
	private int life;

	/** The character's max life. */
	private int maxLife;

	/** An event that is triggered when this character is damaged. */
	private Event<ValueChange> onDamaged;

	private Player parent;

	public PlayerCharacter() {
		onDamaged = new Event<ValueChange>();
		life = 30;
		maxLife = 30;
	}

	public void setParent(Player newPlayer) {
		parent = newPlayer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see application.Attackable#defend(application.Attackable)
	 */
	@Override
	public ValueChange defend(Attackable attacker) {
		this.setLife(this.getLife() - attacker.getEffectiveDamage());
		return null;
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
	 * @see application.Damagable#getLife()
	 */
	public int getLife() {
		return life;
	}

	/**
	 * Sets the character's current life. The new value cannot be less than 0 or
	 * greater than the maximum life (or else it will be rounded up or down).
	 *
	 * @param life
	 *            - the new value for the character's life
	 */
	public void setLife(int life) {
		this.life = Math.min(Math.max(0, life), maxLife);
	}

	/**
	 * Gets the max life.
	 *
	 * @return the max life
	 */
	public int getMaxLife() {
		return maxLife;
	}

	/**
	 * Sets the character's max life. Cannot be less than 1. If the characters
	 * life is higher then the max, it will be reduced to it.
	 *
	 * @param maxLife
	 *            the new max life
	 */
	public void setMaxLife(int maxLife) {
		this.maxLife = Math.max(1, maxLife);
		this.life = Math.min(life, this.maxLife);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see application.Attackable#getEffectiveDamage()
	 */
	@Override
	public int getEffectiveDamage() {
		return 0;
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

	public Player getParent() {
		return parent;
	}

}
