package application;

public class Weapon extends Card {

	public Weapon(String name, String ruleText, int cost) {
		super(name, ruleText, cost);
	}

	public Weapon(Weapon toClone) {
		this(toClone.getName(), toClone.getRuleText(), toClone.getCost());
	}

}
