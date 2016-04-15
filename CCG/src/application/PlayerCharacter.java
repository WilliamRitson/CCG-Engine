package application;

public class PlayerCharacter implements Attackable {
	
	String name;
	
	private int life = 30;
	private int maxLife = 30;

	@Override
	public ValueChange defend(Attackable attacker) {
		this.setLife(this.getLife() - attacker.getEffectiveDamage());
		return null;
	}
	
	private  Event<ValueChange> onDamaged = new Event<ValueChange>();

	@Override
	public ValueChange takeDamage(int amount, Attackable source) {
		ValueChange hpChange = new ValueChange(this.life);
		this.setLife(this.getLife() - amount);
		hpChange.setNewVal(this.life);
		onDamaged.fire(hpChange);
		return hpChange;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public int getMaxLife() {
		return maxLife;
	}

	public void setMaxLife(int maxLife) {
		this.maxLife = maxLife;
	}

	@Override
	public int getEffectiveDamage() {
		return 0;
	}

	@Override
	public Event<ValueChange> getDamageEvent() {
		return onDamaged;
	}

}
