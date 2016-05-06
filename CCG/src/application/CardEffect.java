package application;

import java.util.ArrayList;

public abstract class CardEffect {

	public abstract void matchStart(Match match);

	public abstract boolean isTargeted();

	public abstract ArrayList<Attackable> getValidTargets(ArrayList<Attackable> targets);

	public abstract boolean playOnTarget(Attackable target);
	
	public abstract void play(Player owner, Match match);

}
