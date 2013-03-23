package net.sourceforge.anotherfsm;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NullEventTest {
	@Test
	public void testToString() {
		assertEquals("NullEvent", NullEvent.instance.toString());
	}
}
