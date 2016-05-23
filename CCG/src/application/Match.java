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
		Deck testDeck = new Deck(new Minion("Card 1", "A thing", 1, 1, 1),
				new Minion("Card 2", "Test Card", 0, 1, 3), new Minion("Card 3", "Tetzings", 8, 7, 9),
				new Minion("Card 4", "Lots of test", 1, 3, 5),
				new Minion("Card 5", "Testerzz", 1, 3, 6), new Minion("Card 6", "Demo", 1, 2, 1),
				new Minion("Card 7", "Card test", 1, 12, 7));
		localPlayer = new Player(new PlayerCharacter(), testDeck, this);
		localPlayer.drawCards(3);
		opponent = new Player(new PlayerCharacter(), new Deck(), this);
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
