package application;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Collection {
	
	ArrayList<Card> allCards;
	private static final File rootDirectory = new File("cards");
	
	public void loadAllCards() {
		allCards = new ArrayList<Card>();
		getDirectoryFiles(rootDirectory);
	}
	
	private void getDirectoryFiles(File currentDirectory) {
		File[] list = currentDirectory.listFiles();
		for (int i = 0; i < list.length; i += 1) {
			if (list[i].isDirectory()) {
				getDirectoryFiles(list[i]);
			} else {
				allCards.add(Card.loadFromXML(list[i]));
			}
		}
	}	
	
	Deck getRandomDeck() {
		Deck newDeck = new Deck();
		//Collections.shuffle(allCards);
		for (int i = 0; i < Deck.maxSize / 2; i += 1) {
			//newDeck.addCard(allCards.get(i), 2);
			newDeck.addCard(randomCard());
		}
		return newDeck;
	}
	
	Card randomCard() {
		Random r = new Random();	
		return new Minion("R: " + r.nextInt(99), " ", r.nextInt(10), 1 + r.nextInt(9), r.nextInt(10));
	}


}
