package application;

public class Match {
	Player localPlayer;
	Player opponent;
	Player[] players;
	
	public Match () {
		Deck testDeck = new Deck(
			new PersistantCard("Card 1", "A thing", localPlayer, 1, 1, 1),
			new PersistantCard("Card 2", "Test Card", localPlayer, 0, 1, 3),
			new PersistantCard("Card 3", "Tetzings", localPlayer, 8, 7, 9),
			new PersistantCard("Card 4", "Lots of test", localPlayer, 1, 3, 5),
			new PersistantCard("Card 5", "Testerzz", localPlayer, 1, 3, 6),
			new PersistantCard("Card 6", "Demo", localPlayer, 1, 2, 1),
			new PersistantCard("Card 7", "Card test", localPlayer, 1, 12, 7)
		);
		localPlayer = new Player(new PlayerCharacter(), testDeck);
		localPlayer.drawCards(1);
		opponent = new Player(new PlayerCharacter(), new Deck());
	}

}
