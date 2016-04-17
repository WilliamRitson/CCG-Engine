package application;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

public class ValueChangeTest {
	
	Random generator = new Random(0);

	@Test
	public void testGetDiff() {
		ValueChange vc = new ValueChange(0, 10);
		assertEquals(10, vc.getDiff());

		int rand, rand2;
		for (int i = 0; i < 100; i += 1) {
			rand = generator.nextInt();
			assertEquals(0, (new ValueChange(rand)).getDiff());
		}
				
		for (int i = 0; i < 100; i += 1) {
			rand = generator.nextInt();
			vc.setNewVal(rand);
			assertEquals(rand, vc.getDiff());
		}
		
		for (int i = 0; i < 100; i += 1) {
			rand = generator.nextInt();
			rand2= generator.nextInt();
			vc = new ValueChange(rand, rand2);
			assertEquals(rand2 - rand, vc.getDiff());
		}
	}

	@Test
	public void testSetNewVal() {
		int rand = generator.nextInt();
		ValueChange vc = new ValueChange(generator.nextInt(), 0);
		vc.setNewVal(rand);
		assertEquals(rand, vc.getNewVal());
	}

	@Test
	public void testGetNewVal() {
		int rand = generator.nextInt();
		ValueChange vc = new ValueChange(generator.nextInt(), rand);
		assertEquals(rand, vc.getNewVal());
	}

}
