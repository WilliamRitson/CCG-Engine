package application;

import java.util.ArrayList;

public class Battlefield {
	
	private ArrayList<PersistantCard> entities;
	
	public ArrayList<PersistantCard> getCards() {
		return entities;
	}
	
	public Battlefield() {
		entities = new ArrayList<PersistantCard>();
	}
	
	public void addEntity(PersistantCard entity) {
		entities.add(entity);
	}

	public boolean contains(PersistantCard card) {
		return entities.contains(card);	
	}


}
