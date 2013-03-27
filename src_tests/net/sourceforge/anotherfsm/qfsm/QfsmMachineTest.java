package net.sourceforge.anotherfsm.qfsm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class QfsmMachineTest {
	@Test
	public void testGetStateStringNull() {
		QfsmMachine machine = new QfsmMachine();

		try {
			machine.getState("test");
			fail("Should not be executed");
		} catch (QfsmException e) {
			assertEquals("List of states is not initialized", e.getMessage());
		}
	}

	@Test
	public void testGetStateStringNotFound() {
		QfsmMachine machine = new QfsmMachine();
		machine.setStates(new LinkedList<QfsmState>());

		try {
			machine.getState("test");
			fail("Should not be executed");
		} catch (QfsmException e) {
			assertEquals("State does not exist: test", e.getMessage());
		}
	}

	@Test
	public void testGetStateIntNull() {
		QfsmMachine machine = new QfsmMachine();

		try {
			machine.getState(0);
			fail("Should not be executed");
		} catch (QfsmException e) {
			assertEquals("List of states is not initialized", e.getMessage());
		}
	}

	@Test
	public void testGetStateIntNotFound() {
		QfsmMachine machine = new QfsmMachine();
		machine.setStates(new LinkedList<QfsmState>());

		try {
			machine.getState(0);
			fail("Should not be executed");
		} catch (QfsmException e) {
			assertEquals("State does not exist: 0", e.getMessage());
		}
	}

	@Test
	public void testGetTransitionNull() {
		QfsmMachine machine = new QfsmMachine();

		try {
			machine.getTransition(0, "event", 1);
			fail("Should not be executed");
		} catch (QfsmException e) {
			assertEquals("List of transitions is not initialized",
					e.getMessage());
		}
	}

	@Test
	public void testGetTransitionNotFound() {
		QfsmMachine machine = new QfsmMachine();
		machine.setTransitions(new LinkedList<QfsmTransition>());

		try {
			machine.getTransition(0, "event", 1);
			fail("Should not be executed");
		} catch (QfsmException e) {
			assertEquals("Transition does not exist: 0, event, 1",
					e.getMessage());
		}
	}

	@Test
	public void testGetTransitionNotFoundStart() {
		QfsmMachine machine = new QfsmMachine();
		List<QfsmTransition> transitions = new LinkedList<QfsmTransition>();
		QfsmTransition transition = new QfsmTransition();
		QfsmState state = new QfsmState();
		state.setStateId(1);
		transition.setDestinationState(state);
		transition.setInputEvent("event");
		transitions.add(transition);
		machine.setTransitions(transitions);

		try {
			machine.getTransition(0, "event", 1);
			fail("Should not be executed");
		} catch (QfsmException e) {
			assertEquals("Transition does not exist: 0, event, 1",
					e.getMessage());
		}
	}

	@Test
	public void testGetTransitionNotFoundEvent() {
		QfsmMachine machine = new QfsmMachine();
		List<QfsmTransition> transitions = new LinkedList<QfsmTransition>();
		QfsmTransition transition = new QfsmTransition();
		QfsmState stateStart = new QfsmState();
		stateStart.setStateId(0);
		transition.setSourceState(stateStart);
		QfsmState stateEnd = new QfsmState();
		stateEnd.setStateId(1);
		transition.setDestinationState(stateEnd);
		transitions.add(transition);
		machine.setTransitions(transitions);

		try {
			machine.getTransition(0, "event", 1);
			fail("Should not be executed");
		} catch (QfsmException e) {
			assertEquals("Transition does not exist: 0, event, 1",
					e.getMessage());
		}
	}

	@Test
	public void testGetTransitionNotFoundEnd() {
		QfsmMachine machine = new QfsmMachine();
		List<QfsmTransition> transitions = new LinkedList<QfsmTransition>();
		QfsmTransition transition = new QfsmTransition();
		QfsmState state = new QfsmState();
		state.setStateId(0);
		transition.setSourceState(state);
		transition.setInputEvent("event");
		transitions.add(transition);
		machine.setTransitions(transitions);

		try {
			machine.getTransition(0, "event", 1);
			fail("Should not be executed");
		} catch (QfsmException e) {
			assertEquals("Transition does not exist: 0, event, 1",
					e.getMessage());
		}
	}

	@Test
	public void testEvaluateStates() {
		QfsmMachine machine = new QfsmMachine();
		List<QfsmState> states = new LinkedList<QfsmState>();
		QfsmState state = new QfsmState();
		state.setStateId(0);
		states.add(state);
		machine.setStates(states);
		machine.setStartStateId(0);

		try {
			machine.evaluateStates();
			fail("Should not be executed");
		} catch (QfsmException e) {
			assertEquals("List of transitions is not initialized",
					e.getMessage());
		}
	}

	@Test
	public void testSetStartState() {
		QfsmMachine machine = new QfsmMachine();
		QfsmState state = new QfsmState();
		state.setName("test");
		machine.setStartState(state);
		assertEquals("test", machine.getStartState().getName());
	}

	@Test
	public void testMachineType() {
		try {
			QfsmMachine.MachineType.convert(-1);
		} catch (QfsmException e) {
			assertEquals("Unsupported machine type: -1", e.getMessage());
		}

		try {
			QfsmMachine.MachineType.convert(3);
		} catch (QfsmException e) {
			assertEquals("Unsupported machine type: 3", e.getMessage());
		}
	}

	@Test
	public void testArrowType() {
		try {
			QfsmMachine.ArrowType.convert(-1);
		} catch (QfsmException e) {
			assertEquals("Unsupported arrow type: -1", e.getMessage());
		}

		try {
			QfsmMachine.ArrowType.convert(5);
		} catch (QfsmException e) {
			assertEquals("Unsupported arrow type: 5", e.getMessage());
		}
	}
}
