package net.sourceforge.anotherfsm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class CharacterEventTest {
	@Test
	public final void testGetCharacter() {
		assertEquals('A', new CharacterEvent('A').getCharacter());
		assertEquals('B', new CharacterEvent('B').getCharacter());
	}

	@Test
	public final void testEqualsObject() {
		assertEquals(new CharacterEvent('A'), new CharacterEvent('A'));
		assertFalse(new CharacterEvent('A').equals(new CharacterEvent('B')));
	}

	@Test
	public final void testToString() {
		assertEquals("CharacterEvent(A)", new CharacterEvent('A').toString());
	}
}
