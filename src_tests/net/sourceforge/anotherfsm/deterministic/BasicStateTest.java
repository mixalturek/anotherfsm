package net.sourceforge.anotherfsm.deterministic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.sourceforge.anotherfsm.api.State;
import net.sourceforge.anotherfsm.testimpl.StateListenerImpl;

import org.junit.Test;

public class BasicStateTest {
	@Test
	public final void testAddListener() {
		State state = new BasicState("state");
		State stateFinal = new BasicState("state", true);
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
	public final void testRemoveListener() {
		State state = new BasicState("state");
		State stateFinal = new BasicState("state", true);
		StateListenerImpl listener = new StateListenerImpl();

		state.addListener(listener);
		stateFinal.addListener(listener);
		state.removeListener(listener);
		stateFinal.removeListener(listener);

		assertEquals(listener.enteredNum, 0);
		assertEquals(listener.exitedNum, 0);
		assertEquals(listener.finalEnteredNum, 0);
		assertEquals(listener.finalExitedNum, 0);

		state.notifyEnter(null, null, null);

		assertEquals(listener.enteredNum, 0);
		assertEquals(listener.exitedNum, 0);
		assertEquals(listener.finalEnteredNum, 0);
		assertEquals(listener.finalExitedNum, 0);

		state.notifyExit(null, null, null);

		assertEquals(listener.enteredNum, 0);
		assertEquals(listener.exitedNum, 0);
		assertEquals(listener.finalEnteredNum, 0);
		assertEquals(listener.finalExitedNum, 0);

		stateFinal.notifyEnter(null, null, null);

		assertEquals(listener.enteredNum, 0);
		assertEquals(listener.exitedNum, 0);
		assertEquals(listener.finalEnteredNum, 0);
		assertEquals(listener.finalExitedNum, 0);

		stateFinal.notifyExit(null, null, null);

		assertEquals(listener.enteredNum, 0);
		assertEquals(listener.exitedNum, 0);
		assertEquals(listener.finalEnteredNum, 0);
		assertEquals(listener.finalExitedNum, 0);
	}

	@Test
	public final void testGetName() {
		State state = new BasicState("state");
		assertEquals("state", state.getName());

		State stateNonFinal = new BasicState("state", false);
		assertEquals("state", stateNonFinal.getName());

		State stateFinal = new BasicState("state", true);
		assertEquals("state", stateFinal.getName());
	}

	@Test
	public final void testIsFinalState() {
		State stateDefault = new BasicState("state");
		assertFalse(stateDefault.isFinalState());

		State stateNonFinal = new BasicState("state", false);
		assertFalse(stateNonFinal.isFinalState());

		State stateFinal = new BasicState("state", true);
		assertTrue(stateFinal.isFinalState());
	}

	@Test
	public final void testHashCode() {
		State state1 = new BasicState("same");
		State state2 = new BasicState("same");
		State state3 = new BasicState("different");

		assertEquals(state1.hashCode(), state2.hashCode());
		assertTrue(state1.hashCode() != state3.hashCode());
		assertTrue(state2.hashCode() != state3.hashCode());

		State state1Final = new BasicState("same", true);
		State state2Final = new BasicState("same", true);
		State state3Final = new BasicState("different", true);

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
		State state1 = new BasicState("same");
		State state2 = new BasicState("same");
		State state3 = new BasicState("different");

		assertEquals(state1, state2);
		assertEquals(state2, state1);
		assertFalse(state1.equals(state3));
		assertFalse(state2.equals(state3));
		assertFalse(state3.equals(state1));
		assertFalse(state3.equals(state2));

		State state1Final = new BasicState("same", true);
		State state2Final = new BasicState("same", true);
		State state3Final = new BasicState("different", true);

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
		State state = new BasicState("state");
		assertEquals("state", state.toString());

		State stateNonFinal = new BasicState("state", false);
		assertEquals("state", stateNonFinal.toString());

		State stateFinal = new BasicState("state", true);
		assertEquals("state", stateFinal.toString());
	}
}
