package application;

public class Spell extends Card {

	public Spell(String name, String ruleText, int cost) {
		super(name, ruleText, cost);
	}

	public Spell(Spell toClone) {
		this(toClone.getName(), toClone.getRuleText(), toClone.getCost());
	}

}
