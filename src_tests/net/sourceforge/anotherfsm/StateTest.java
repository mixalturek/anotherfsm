package net.sourceforge.anotherfsm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StateTest {
	@Test
	public final void testAddListener() {
		State state = new State("state");
		State stateFinal = new State("state", true);
		StateListenerImpl listener = new StateListenerImpl();

		state.addListener(listener);
		stateFinal.addListener(listener);

		assertEquals(listener.enteredNum, 0);
		assertEquals(listener.exitedNum, 0);
		assertEquals(listener.finalEnteredNum, 0);
		assertEquals(listener.finalExitedNum, 0);

		state.notifyEnter(null, null, null);

		assertEquals(listener.enteredNum, 1);
		assertEquals(listener.exitedNum, 0);
		assertEquals(listener.finalEnteredNum, 0);
		assertEquals(listener.finalExitedNum, 0);

		state.notifyExit(null, null, null);

		assertEquals(listener.enteredNum, 1);
		assertEquals(listener.exitedNum, 1);
		assertEquals(listener.finalEnteredNum, 0);
		assertEquals(listener.finalExitedNum, 0);

		stateFinal.notifyEnter(null, null, null);

		assertEquals(listener.enteredNum, 2);
		assertEquals(listener.exitedNum, 1);
		assertEquals(listener.finalEnteredNum, 1);
		assertEquals(listener.finalExitedNum, 0);

		stateFinal.notifyExit(null, null, null);

		assertEquals(listener.enteredNum, 2);
		assertEquals(listener.exitedNum, 2);
		assertEquals(listener.finalEnteredNum, 1);
		assertEquals(listener.finalExitedNum, 1);
	}

	@Test
	public final void testGetName() {
		State state = new State("state");
		assertEquals("state", state.getName());

		State stateNonFinal = new State("state", false);
		assertEquals("state", stateNonFinal.getName());

		State stateFinal = new State("state", true);
		assertEquals("state", stateFinal.getName());
	}

	@Test
	public final void testIsFinalState() {
		State stateDefault = new State("state");
		assertFalse(stateDefault.isFinalState());

		State stateNonFinal = new State("state", false);
		assertFalse(stateNonFinal.isFinalState());

		State stateFinal = new State("state", true);
		assertTrue(stateFinal.isFinalState());
	}

	@Test
	public final void testHashCode() {
		State state1 = new State("same");
		State state2 = new State("same");
		State state3 = new State("different");

		assertEquals(state1.hashCode(), state2.hashCode());
		assertTrue(state1.hashCode() != state3.hashCode());
		assertTrue(state2.hashCode() != state3.hashCode());

		State state1Final = new State("same", true);
		State state2Final = new State("same", true);
		State state3Final = new State("different", true);

		assertEquals(state1Final.hashCode(), state2Final.hashCode());
		assertTrue(state1Final.hashCode() != state3Final.hashCode());
		assertTrue(state2Final.hashCode() != state3Final.hashCode());

		// Only name of the state is used for creating hash code.
		assertEquals(state1.hashCode(), state1Final.hashCode());
		assertEquals(state2.hashCode(), state2Final.hashCode());
		assertEquals(state3.hashCode(), state3Final.hashCode());
	}

	@Test
	public final void testEqualsObject() {
		State state1 = new State("same");
		State state2 = new State("same");
		State state3 = new State("different");

		assertEquals(state1, state2);
		assertEquals(state2, state1);
		assertFalse(state1.equals(state3));
		assertFalse(state2.equals(state3));
		assertFalse(state3.equals(state1));
		assertFalse(state3.equals(state2));

		State state1Final = new State("same", true);
		State state2Final = new State("same", true);
		State state3Final = new State("different", true);

		assertEquals(state1, state1);
		assertEquals(state1Final, state1Final);

		// Only name of the states is used during the comparison.
		assertEquals(state1, state1Final);
		assertEquals(state2, state2Final);
		assertEquals(state3, state3Final);
		assertEquals(state1Final, state1);
		assertEquals(state2Final, state2);
		assertEquals(state3Final, state3);
	}

	@Test
	public final void testToString() {
		State state = new State("state");
		assertEquals("state", state.toString());

		State stateNonFinal = new State("state", false);
		assertEquals("state", stateNonFinal.toString());

		State stateFinal = new State("state", true);
		assertEquals("state", stateFinal.toString());
	}
}
