package application;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PlayerCharacterTest {

	PlayerCharacter character;
	Player owner;
	@Before
	public void setUp() throws Exception {
		character = new PlayerCharacter();
		owner = new Player(character, new Deck(), null);
	}

	@Test
	public final void testSetOwner() {
		character.setParent(owner);
		assertEquals(character.getParent(), owner);
	}

	@Test
	public final void testDefend() {
		fail("Not yet implemented");
	}

	@Test
	public final void testTakeDamage() {
		fail("Not yet implemented");
	}

	@Test
	public final void testSetLife() {
		character.setLife(15);
		assertEquals(15, character.getLife());
		character.setLife(31);
		assertEquals(30, character.getLife());
		character.setLife(-1);
		assertEquals(0, character.getLife());
		character.setLife(0);
		assertEquals(0, character.getLife());
		character.setMaxLife(40);
		character.setLife(53);
		assertEquals(40, character.getLife());
	}

	@Test
	public final void testGetMaxLife() {
		assertEquals(30, character.getMaxLife());
		character.setMaxLife(8);
		assertEquals(8, character.getMaxLife());
		character.setMaxLife(0);
		assertEquals(1, character.getMaxLife());
		character.setMaxLife(-4);
		assertEquals(1, character.getMaxLife());
		assertEquals(1, character.getLife());
		character.setMaxLife(15);
		assertEquals(1, character.getLife());
	}


	@Test
	public final void testGetEffectiveDamage() {
		fail("Not yet implemented");
	}

	int old, diff, after;
	@Test
	public final void testGetDamageEvent() {
		int initialLife = character.getLife();
		int dmg = 5;
		
		character.getDamageEvent().addWatcher((Event<ValueChange> e, ValueChange vc) -> {
			old = vc.getOldVal();
			after = vc.getNewVal();
		});
		
		character.takeDamage(dmg, null);
		
		assertEquals(initialLife, old);
		assertEquals(initialLife - dmg, after);;
	}

	@Test
	public final void testGetParent() {
		fail("Not yet implemented");
	}

}
