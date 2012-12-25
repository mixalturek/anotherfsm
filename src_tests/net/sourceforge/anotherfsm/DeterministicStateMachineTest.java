package net.sourceforge.anotherfsm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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
		State state = new State("state", State.Type.FINAL);
		StateListenerImpl listener = new StateListenerImpl();
		StateListenerImpl listenerFsm = new StateListenerImpl();

		try {
			machine.start();
			fail("Should not be executed");
		} catch (FsmException e) {
			// Do nothing
		}

		try {
			machine.addState(state);
			state.addListener(listener);
			machine.addListener(listenerFsm);
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
			machine.setStartState(state);
		} catch (FsmException e) {
			fail("Should not be executed");
		}

		try {
			machine.start();
			machine.close();
		} catch (FsmException e) {
			fail("Should not be executed");
		} catch (IOException e) {
			fail("Should not be executed");
		}

		assertEquals(1, listener.enteredNum);
		assertEquals(1, listener.finalEnteredNum);
		assertEquals(0, listener.exitedNum);
		assertEquals(0, listener.finalExitedNum);

		assertEquals(1, listenerFsm.enteredNum);
		assertEquals(1, listenerFsm.finalEnteredNum);
		assertEquals(0, listenerFsm.exitedNum);
		assertEquals(0, listenerFsm.finalExitedNum);
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
	public final void testAddListenerStateTransitionListener() {
		StateMachine machine = new DeterministicStateMachine("fsm");
		final State startState = new State("state");
		final State finalState = new State("another", State.Type.FINAL);
		Transition transition = new Transition(startState, new TypeEventA(),
				finalState);
		Transition back = new Transition(finalState, new TypeEventB(),
				startState);
		Event processedEvent = null;

		final List<String> real = new LinkedList<String>();
		List<String> expected = new LinkedList<String>();

		try {
			machine.addState(startState);
			machine.addState(finalState);
			machine.addTransition(transition);
			machine.addTransition(back);
			machine.setStartState(startState);

			startState.addListener(new StateListenerImpl() {
				@Override
				public void onStateEnter(State previous, Event event,
						State current) {
					switch (enteredNum) {
					case 0:
						assertEquals(startState, previous);
						assertEquals(new StartEvent(), event);
						assertEquals(startState, current);
						real.add("startState.onStateEnter");
						break;

					case 1:
						assertEquals(finalState, previous);
						assertEquals(new TypeEventB(), event);
						assertEquals(startState, current);
						real.add("startState.onStateEnter");
						break;

					default:
						fail("Should not be executed");
						break;
					}

					super.onStateEnter(previous, event, current);
				}

				@Override
				public void onStateExit(State current, Event event, State next) {
					assertEquals(startState, current);
					assertEquals(new TypeEventA(), event);
					assertEquals(finalState, next);
					real.add("startState.onStateExit");

					super.onStateExit(current, event, next);
				}

				@Override
				public void onFinalStateEnter(State previous, Event event,
						State current) {
					fail("Should not be executed");
				}

				@Override
				public void onFinalStateExit(State current, Event event,
						State next) {
					fail("Should not be executed");
				}
			});

			finalState.addListener(new StateListenerImpl() {
				@Override
				public void onStateEnter(State previous, Event event,
						State current) {
					assertEquals(startState, previous);
					assertEquals(new TypeEventA(), event);
					assertEquals(finalState, current);
					real.add("finalState.onStateEnter");

					super.onStateEnter(previous, event, current);
				}

				@Override
				public void onStateExit(State current, Event event, State next) {
					assertEquals(finalState, current);
					assertEquals(new TypeEventB(), event);
					assertEquals(startState, next);
					real.add("finalState.onStateExit");

					super.onStateExit(current, event, next);
				}

				@Override
				public void onFinalStateEnter(State previous, Event event,
						State current) {
					assertEquals(startState, previous);
					assertEquals(new TypeEventA(), event);
					assertEquals(finalState, current);
					real.add("finalState.onFinalStateEnter");

					super.onFinalStateEnter(previous, event, current);
				}

				@Override
				public void onFinalStateExit(State current, Event event,
						State next) {
					assertEquals(finalState, current);
					assertEquals(new TypeEventB(), event);
					assertEquals(startState, next);
					real.add("finalState.onFinalStateExit");

					super.onFinalStateExit(current, event, next);
				}
			});

			machine.addListener(new StateListenerImpl() {
				@Override
				public void onStateEnter(State previous, Event event,
						State current) {
					switch (enteredNum) {
					case 0:
						assertEquals(startState, previous);
						assertEquals(new StartEvent(), event);
						assertEquals(startState, current);
						real.add("fsm.onStateEnter");
						break;

					case 1:
						assertEquals(startState, previous);
						assertEquals(new TypeEventA(), event);
						assertEquals(finalState, current);
						real.add("fsm.onStateEnter");
						break;

					case 2:
						assertEquals(finalState, previous);
						assertEquals(new TypeEventB(), event);
						assertEquals(startState, current);
						real.add("fsm.onStateEnter");
						break;

					default:
						fail("Should not be executed");
						break;
					}

					super.onStateEnter(previous, event, current);
				}

				@Override
				public void onStateExit(State current, Event event, State next) {
					switch (exitedNum) {
					case 0:
						assertEquals(startState, current);
						assertEquals(new TypeEventA(), event);
						assertEquals(finalState, next);
						real.add("fsm.onStateExit");
						break;

					case 1:
						assertEquals(finalState, current);
						assertEquals(new TypeEventB(), event);
						assertEquals(startState, next);
						real.add("fsm.onStateExit");
						break;

					default:
						fail("Should not be executed");
						break;
					}

					super.onStateExit(current, event, next);
				}

				@Override
				public void onFinalStateEnter(State previous, Event event,
						State current) {
					assertEquals(startState, previous);
					assertEquals(new TypeEventA(), event);
					assertEquals(finalState, current);
					real.add("fsm.onFinalStateEnter");

					super.onFinalStateEnter(previous, event, current);
				}

				@Override
				public void onFinalStateExit(State current, Event event,
						State next) {
					assertEquals(finalState, current);
					assertEquals(new TypeEventB(), event);
					assertEquals(startState, next);
					real.add("fsm.onFinalStateExit");

					super.onFinalStateExit(current, event, next);
				}
			});

			machine.addListener(new TransitionListenerImpl() {
				@Override
				public void onTransition(State source, Event event,
						State destination) {
					switch (transitionsNum) {
					case 0:
						assertEquals(startState, source);
						assertEquals(new TypeEventA(), event);
						assertEquals(finalState, destination);
						real.add("fsm.onTransition");
						break;

					case 1:
						assertEquals(finalState, source);
						assertEquals(new TypeEventB(), event);
						assertEquals(startState, destination);
						real.add("fsm.onTransition");
						break;

					default:
						fail("Should not be executed");
						break;
					}

					super.onTransition(source, event, destination);
				}
			});

			transition.addListener(new TransitionListenerImpl() {
				@Override
				public void onTransition(State source, Event event,
						State destination) {
					assertEquals(startState, source);
					assertEquals(new TypeEventA(), event);
					assertEquals(finalState, destination);
					real.add("transition.onTransition");

					super.onTransition(source, event, destination);
				}
			});

			back.addListener(new TransitionListenerImpl() {
				@Override
				public void onTransition(State source, Event event,
						State destination) {
					assertEquals(finalState, source);
					assertEquals(new TypeEventB(), event);
					assertEquals(startState, destination);
					real.add("back.onTransition");

					super.onTransition(source, event, destination);
				}
			});
		} catch (FsmException e) {
			fail("Should not be executed");
		}

		try {
			machine.start();
			expected.add("startState.onStateEnter");
			expected.add("fsm.onStateEnter");
			assertEquals(expected, real);
			expected.clear();
			real.clear();

			processedEvent = machine.process(new TypeEventA());
			assertEquals(new TypeEventA(), processedEvent);
			expected.add("startState.onStateExit");
			expected.add("fsm.onStateExit");
			expected.add("transition.onTransition");
			expected.add("fsm.onTransition");
			expected.add("finalState.onStateEnter");
			expected.add("finalState.onFinalStateEnter");
			expected.add("fsm.onStateEnter");
			expected.add("fsm.onFinalStateEnter");
			assertEquals(expected, real);
			expected.clear();
			real.clear();

			processedEvent = machine.process(new TypeEventB());
			assertEquals(new TypeEventB(), processedEvent);
			expected.add("finalState.onStateExit");
			expected.add("finalState.onFinalStateExit");
			expected.add("fsm.onStateExit");
			expected.add("fsm.onFinalStateExit");
			expected.add("back.onTransition");
			expected.add("fsm.onTransition");
			expected.add("startState.onStateEnter");
			expected.add("fsm.onStateEnter");
			assertEquals(expected, real);
			expected.clear();
			real.clear();

			machine.close();
			assertEquals(expected, real);
		} catch (FsmException e) {
			fail("Should not be executed");
		} catch (IOException e) {
			fail("Should not be executed");
		}
	}

	@Test
	public final void testAddProcessor() {
		StateMachine machine = new DeterministicStateMachine("fsm");
		State state = new State("state");
		State another = new State("another");
		Transition transition = new Transition(state, new TypeEventA(), another);
		Event processedEvent = null;

		try {
			machine.addState(state);
			machine.addState(another);
			machine.addTransition(transition);
			machine.setStartState(state);

			machine.addProcessor(TypeEventA.class, new Processor<TypeEventA>() {
				@Override
				public Event process(TypeEventA event) {
					return null;
				}
			});

			machine.addProcessor(TypeEventB.class, new Processor<TypeEventB>() {
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
			fail("Should not be executed");
		} catch (NullPointerException e) {
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

			machine.close();
		} catch (FsmException e) {
			fail("Should not be executed");
		} catch (IOException e) {
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
		Transition transition = new Transition(state, new TypeEventA(), another);
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
			fail("Should not be executed");
		} catch (NullPointerException e) {
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
			machine.close();
		} catch (FsmException e) {
			fail("Should not be executed");
		} catch (IOException e) {
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
