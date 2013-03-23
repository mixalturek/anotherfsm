package net.sourceforge.anotherfsm;

import org.junit.Test;

public class TransitionAdapterTest {
	@Test
	public void testOnTransition() {
		TransitionAdapter adapter = new TransitionAdapter();
		adapter.onTransition(new State("state"), new TypeEventA(), new State(
				"state"));
	}
}
