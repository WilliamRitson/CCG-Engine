package application;

public interface Attackable extends Damagable {
	/**
	 * @return The amount of damage this character deals when attacking.
	 */
	public int getEffectiveDamage();

	/**
	 * @param attacker
	 *            - The character attacking this entity
	 * @return The change in this entities life resulting form the attack.
	 */
	public ValueChange defend(Attackable attacker);
}
