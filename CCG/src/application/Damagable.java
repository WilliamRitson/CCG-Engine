package application;

public interface Damagable {
	/**
	 * @return An event that fires whenever the entity takes damage. The event
	 *         produces a Value change which contains the difference in the
	 *         characters life.
	 */
	public Event<ValueChange> getDamageEvent();

	/**
	 * @return The characters remaining life
	 */
	public int getLife();

	/**
	 * @param amount
	 *            The amount of damage done to this character
	 * @param source
	 *            The source of damage. Should be null if the source cannot be
	 *            attacked
	 * @return The change in the characters life total resulting from the attack
	 */
	public ValueChange takeDamage(int amount, Attackable source);

}
