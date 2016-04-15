package application;

public interface Attackable extends Damagable {
	public int getEffectiveDamage();
	public ValueChange defend(Attackable attacker);
}
