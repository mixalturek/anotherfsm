package net.sourceforge.anotherfsm;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class TimeoutStateMachineTest {
	@Test
	public final void testAddTransition() {
		StateMachine machine = new TimeoutStateMachine("fsm");
		State state = new State("state");

		try {
			// OK
			machine.addState(state);
			machine.addTransition(new Transition(state, new TimeoutEventImpl(
					42, TimeoutEvent.Type.RESTART_TIMEOUT_ON_LOOP), state));
		} catch (FsmException e) {
			fail("Should not be executed");
		}

		try {
			// The same timeout
			machine.addTransition(new Transition(state, new TimeoutEventImpl(
					42, TimeoutEvent.Type.RESTART_TIMEOUT_ON_LOOP), state));
			fail("Should not be executed");
		} catch (FsmException e) {
			assertTrue(e.getMessage().contains("Transition already defined"));
		}

		try {
			// Different timeout
			machine.addTransition(new Transition(state, new TimeoutEventImpl(
					10, TimeoutEvent.Type.DONT_RESTART_TIMEOUT_ON_LOOP), state));
			fail("Should not be executed");
		} catch (FsmException e) {
			assertTrue(e.getMessage().contains("Transition already defined"));
		}

		try {
			// Different timeout class
			machine.addTransition(new Transition(state,
					new NonstandardTimeoutEvent(), state));
			fail("Should not be executed");
		} catch (FsmException e) {
			assertTrue(e.getMessage().contains("Transition already defined"));
		}
	}
}
