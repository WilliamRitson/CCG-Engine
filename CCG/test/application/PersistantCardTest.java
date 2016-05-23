package application;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PersistantCardTest {
	Player owner;
	Minion card;
	int cost = 10;
	int life = 11;
	int damage = 12;
	
	@Before
	public void setUp() throws Exception {
		owner = new Player(new PlayerCharacter(), new Deck(), null);

		card = new Minion("name", "rules",  cost, life, damage);
		card.setOwner(owner);
	}

	@Test
	public final void testPlayCard() {
		card.playCard();
		assertTrue(card.getOwner().getBattlefield().contains(card));
		assertEquals(-1, card.getOwner().getHand().indexOf(card));
	}

	@Test
	public final void testGetOwner() {
		assertEquals(owner, card.getOwner());
	}

	int old, diff, after;
	@Test
	public final void testGetDamageEvent() {
		int initialLife = card.getLife();
		int dmg = 5;
		
		card.getDamageEvent().addWatcher((Event<ValueChange> e, ValueChange vc) -> {
			old = vc.getOldVal();
			after = vc.getNewVal();
		});
		
		card.takeDamage(dmg, null);
		
		assertEquals(initialLife, old);
		assertEquals(initialLife - dmg, after);
	}

	@Test
	public final void testGetLife() {
		assertEquals(life, card.getLife());
	}

	@Test
	public final void testTakeDamage() {
		ValueChange vc = card.takeDamage(5, null);
		assertEquals(life, vc.getOldVal());
		assertEquals(-5, vc.getDiff());
	}

	@Test
	public final void testGetEffectiveDamage() {
		assertEquals(damage, card.getEffectiveDamage());
	}

	@Test
	public final void testDefend() {
		Minion card2 = new Minion("name2", "rules", cost, life, damage);
		card.defend(card2);
		assertEquals(life - damage, card2.getLife());
	}

	@Test
	public final void testGetName() {
		assertEquals("name", card.getName());
	}

	@Test
	public final void testGetCost() {
		assertEquals(10, card.getCost());
	}

	@Test
	public final void testIsPlayable() {
		owner.setResource(card.getCost());
		assertTrue(card.isPlayable());
		owner.setResource(card.getCost() - 1);

		assertFalse(card.isPlayable());
	}

	@Test
	public final void testGetDisplayText() {
		assertEquals("rules", card.getDisplayText());
	}

}
