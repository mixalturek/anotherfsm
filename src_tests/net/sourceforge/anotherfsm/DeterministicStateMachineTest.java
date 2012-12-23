package net.sourceforge.anotherfsm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.BasicConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

public class DeterministicStateMachineTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		BasicConfigurator.configure();
	}

	@Test
	public final void testDeterministicStateMachineString() {
		class DeterministicStateMachineTmp extends DeterministicStateMachine {
			public DeterministicStateMachineTmp(String name) {
				super(name);
				assertEquals(this.getClass() + "-" + name, logger.getName());
			}
		}

		new DeterministicStateMachineTmp("fsm");
	}

	@Test
	public final void testGetName() {
		StateMachine machine = new DeterministicStateMachine("fsm");
		assertEquals("fsm", machine.getName());
	}

	@Test
	public final void testSetStartState() {
		StateMachine machine = new DeterministicStateMachine("fsm");
		assertNull(machine.getActiveState());

		try {
			machine.setStartState(new State("state"));
		} catch (FsmException e) {
			fail("Should not be executed");
		}

		assertEquals(new State("state"), machine.getActiveState());

		try {
			machine.setStartState(new State("state"));
			fail("Should not be executed");
		} catch (FsmException e) {
			assertEquals(new State("state"), machine.getActiveState());
		}

		try {
			machine.setStartState(new State("different state"));
			fail("Should not be executed");
		} catch (FsmException e) {
			assertEquals(new State("state"), machine.getActiveState());
		}
	}

	@Test
	public final void testStart() {
		StateMachine machine = new DeterministicStateMachine("fsm");
		try {
			machine.start();
			fail("Should not be executed");
		} catch (FsmException e) {
			// Do nothing
		}

		try {
			machine.addState(new State("state"));
		} catch (FsmException e) {
			fail("Should not be executed");
		}

		try {
			machine.start();
			fail("Should not be executed");
		} catch (FsmException e) {
			// Do nothing
		}

		try {
			machine.process(new TypeEventA());
			fail("Should not be executed");
		} catch (FsmException e) {
			// Do nothing
		}

		try {
			machine.setStartState(new State("state"));
		} catch (FsmException e) {
			fail("Should not be executed");
		}

		try {
			machine.start();
		} catch (FsmException e) {
			fail("Should not be executed");
		}
	}

	@Test
	public final void testStop() {
		// Does nothing
	}

	@Test
	public final void testAddState() {
		StateMachine machine = new DeterministicStateMachine("fsm");
		assertEquals(0, machine.getStates().size());

		try {
			machine.addState(new State("state"));
		} catch (FsmException e) {
			fail("Should not be executed");
		}

		assertEquals(1, machine.getStates().size());

		try {
			machine.addState(new State("state"));
			fail("Should not be executed");
		} catch (FsmException e) {
			// Do nothing
		}

		assertEquals(1, machine.getStates().size());

		try {
			machine.addState(new State("another state"));
		} catch (FsmException e) {
			fail("Should not be executed");
		}

		assertEquals(2, machine.getStates().size());
	}

	@Test
	public final void testAddTransition() {
		StateMachine machine = new DeterministicStateMachine("fsm");
		assertEquals(0, machine.getStates().size());
		assertEquals(0, machine.getTransitions().size());

		try {
			machine.addTransition(new Transition(new State("state"),
					new TypeEventA(), new State("state")));
		} catch (FsmException e) {
			fail("Should not be executed");
		}

		assertEquals(1, machine.getStates().size()); // New state
		assertEquals(1, machine.getTransitions().size()); // New transition

		try {
			// The same one
			machine.addTransition(new Transition(new State("state"),
					new TypeEventA(), new State("state")));
			fail("Should not be executed");
		} catch (FsmException e) {
			// Do nothing
		}

		assertEquals(1, machine.getStates().size()); // Nothing changed
		assertEquals(1, machine.getTransitions().size());

		try {
			// Non deterministic
			machine.addTransition(new Transition(new State("state"),
					new TypeEventA(), new State("another state")));
			fail("Should not be executed");
		} catch (FsmException e) {
			// Do nothing
		}

		assertEquals(1, machine.getStates().size()); // Nothing changed
		assertEquals(1, machine.getTransitions().size());

		try {
			// Different event
			machine.addTransition(new Transition(new State("state"),
					new TypeEventB(), new State("state")));
		} catch (FsmException e) {
			fail("Should not be executed");
		}

		assertEquals(1, machine.getStates().size());
		assertEquals(2, machine.getTransitions().size()); // New transition

		try {
			// Different source state
			machine.addTransition(new Transition(new State("another state"),
					new TypeEventA(), new State("state")));
		} catch (FsmException e) {
			fail("Should not be executed");
		}

		assertEquals(2, machine.getStates().size()); // New state
		assertEquals(3, machine.getTransitions().size()); // New transition

		try {
			// Different source state
			machine.addTransition(new Transition(new State("another state"),
					new TypeEventB(), new State("state")));
		} catch (FsmException e) {
			fail("Should not be executed");
		}

		assertEquals(2, machine.getStates().size());
		assertEquals(4, machine.getTransitions().size()); // New transition
	}

	@Test
	public final void testAddListenerStateListener() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testAddListenerTransitionListener() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testAddProcessor() {
		StateMachine machine = new DeterministicStateMachine("fsm");
		State state = new State("state");
		State another = new State("another");
		Transition transition = new Transition(state, new TypeEventA(),
				another);
		// Transition back = new Transition(another, new TypeEventImpl2(),
		// state);
		Event processedEvent = null;

		try {
			machine.addState(state);
			machine.addState(another);
			machine.addTransition(transition);
			machine.setStartState(state);

			machine.addProcessor(TypeEventA.class,
					new Processor<TypeEventA>() {
						@Override
						public Event process(TypeEventA event) {
							return null;
						}
					});

			machine.addProcessor(TypeEventB.class,
					new Processor<TypeEventB>() {
						@Override
						public Event process(TypeEventB event) {
							return new TypeEventA();
						}
					});

			machine.start();
		} catch (FsmException e) {
			fail("Should not be executed");
		}

		assertNull(processedEvent);
		assertEquals(state, machine.getActiveState());

		try {
			// Null event
			processedEvent = machine.process(null);
			fail("Should not be executed");
		} catch (FsmException e) {
			// Do nothing
		}

		assertNull(processedEvent);
		assertEquals(state, machine.getActiveState());

		try {
			processedEvent = machine.process(new TypeEventA());

			assertNull(processedEvent);
			assertEquals(state, machine.getActiveState());

			processedEvent = machine.process(new TypeEventA());

			assertNull(processedEvent);
			assertEquals(state, machine.getActiveState());

			processedEvent = machine.process(new TypeEventB());

			assertEquals(new TypeEventA(), processedEvent);
			assertEquals(another, machine.getActiveState());
		} catch (FsmException e) {
			fail("Should not be executed");
		}
	}

	@Test
	public final void testGetActiveState() {
		StateMachine machine = new DeterministicStateMachine("fsm");
		assertNull(machine.getActiveState());

		try {
			machine.setStartState(new State("state"));
		} catch (FsmException e) {
			fail("Should not be executed");
		}

		assertEquals(new State("state"), machine.getActiveState());
	}

	@Test
	public final void testGetActiveStates() {
		StateMachine machine = new DeterministicStateMachine("fsm");
		assertEquals(new HashSet<State>(), machine.getActiveStates());

		try {
			machine.setStartState(new State("state"));
		} catch (FsmException e) {
			fail("Should not be executed");
		}

		Set<State> states = new HashSet<State>();
		states.add(new State("state"));
		assertEquals(states, machine.getActiveStates());
	}

	@Test
	public final void testProcess() {
		StateMachine machine = new DeterministicStateMachine("fsm");
		State state = new State("state");
		State another = new State("another");
		Transition transition = new Transition(state, new TypeEventA(),
				another);
		Transition back = new Transition(another, new TypeEventB(), state);
		Event processedEvent = null;

		try {
			machine.addState(state);
			machine.addState(another);
			machine.addTransition(transition);
			machine.addTransition(back);
			machine.setStartState(state);
			machine.start();
		} catch (FsmException e) {
			fail("Should not be executed");
		}

		try {
			// Null event
			processedEvent = machine.process(null);
			fail("Should not be executed");
		} catch (FsmException e) {
			// Do nothing
		}

		assertNull(processedEvent);
		assertEquals(state, machine.getActiveState());

		try {
			// No such transition
			processedEvent = machine.process(new TypeEventB());
			fail("Should not be executed");
		} catch (FsmException e) {
			// Do nothing
		}

		assertNull(processedEvent);
		assertEquals(state, machine.getActiveState());

		try {
			// Ok
			processedEvent = machine.process(new TypeEventA());
		} catch (FsmException e) {
			fail("Should not be executed");
		}

		assertEquals(new TypeEventA(), processedEvent);
		assertEquals(another, machine.getActiveState());

		try {
			// Ok
			processedEvent = machine.process(new TypeEventB());
		} catch (FsmException e) {
			fail("Should not be executed");
		}

		assertEquals(new TypeEventB(), processedEvent);
		assertEquals(state, machine.getActiveState());
	}

	@Test
	public final void testToString() {
		StateMachine machine = new DeterministicStateMachine("fsm");
		assertEquals(machine.getClass().getSimpleName() + "(fsm)",
				machine.toString());
	}
}
