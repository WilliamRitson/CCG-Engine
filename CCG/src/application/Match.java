package application;

import java.util.ArrayList;

/**
 * The Class Match.
 * 
 * This represents a single 1v1 instance of the game.
 */
public class Match {

	/** The local player. Eg the person using the application */
	Player localPlayer;

	/** The opponent. Eg an A.I or player on the network */
	Player opponent;

	/** Array that holds both of the players. */
	Player[] players;

	/**
	 * Instantiates a new match.
	 * 
	 * This version populates the game with test data
	 */
	public Match() {
		Collection c = new Collection();
		localPlayer = new Player(new PlayerCharacter(), c.getRandomDeck(), this);
		localPlayer.drawCards(3);
		opponent = new Player(new PlayerCharacter(), c.getRandomDeck(), this);
		opponent.drawCards(3);
		players = new Player[] { localPlayer, opponent };
		
		localPlayer.startTurn();
	}
	
	public void endTurn() {
		Player temp = opponent;
		opponent = localPlayer;
		localPlayer = temp;
		localPlayer.startTurn();
	}
	
	public ArrayList<Attackable> getAllTargets() {
		ArrayList<Attackable> targets = new ArrayList<Attackable>();
		targets.addAll(opponent.getBattlefield().getCards());
		targets.add(opponent.getPlayerChar());
		targets.addAll(localPlayer.getBattlefield().getCards());
		targets.add(localPlayer.getPlayerChar());
		return targets;
	}


}
