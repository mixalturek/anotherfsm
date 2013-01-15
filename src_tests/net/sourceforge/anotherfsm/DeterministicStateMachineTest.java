/*
 *  Copyright 2013 Michal Turek, Another FSM
 *
 *      http://anotherfsm.sourceforge.net/
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package net.sourceforge.anotherfsm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import net.sourceforge.anotherfsm.logger.NoLoggerFactory;

import org.junit.BeforeClass;
import org.junit.Test;

public class DeterministicStateMachineTest {
	/**
	 * Generate state machine object. Redefine this method in subclasses to use
	 * these tests for them too.
	 * 
	 * @return the state machine
	 */
	protected StateMachine genStateMachine() {
		return new DeterministicStateMachine("fsm");
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		AnotherFsm.setLoggerFactory(new NoLoggerFactory());
	}

	@Test
	public final void testGetName() {
		StateMachine machine = genStateMachine();
		assertEquals("fsm", machine.getName());

		machine.close();
	}

	@Test
	public final void testSetStartState() {
		StateMachine machine = genStateMachine();
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

		machine.close();
	}

	@Test
	public final void testStart() {
		StateMachine machine = genStateMachine();
		State state = new State("state", State.Type.FINAL);
		StateListenerImpl listener = new StateListenerImpl(
				StateListener.Type.LOOP_PROCESS);
		StateListenerImpl listenerFsm = new StateListenerImpl(
				StateListener.Type.LOOP_PROCESS);

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
		} catch (FsmException e) {
			fail("Should not be executed");
		}

		assertEquals(1, listener.enteredNum);
		assertEquals(0, listener.exitedNum);

		assertEquals(1, listenerFsm.enteredNum);
		assertEquals(0, listenerFsm.exitedNum);

		machine.close();
	}

	@Test
	public final void testStop() {
		// Does nothing
	}

	@Test
	public final void testAddState() {
		StateMachine machine = genStateMachine();
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

		machine.close();
	}

	@Test
	public final void testAddTransition() {
		StateMachine machine = genStateMachine();
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

		machine.close();
	}

	@Test
	public final void testAddListenerStateTransitionListener() {
		StateMachine machine = genStateMachine();
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

			startState.addListener(new StateListenerImpl(
					StateListener.Type.LOOP_PROCESS) {
				@Override
				public void onStateEnter(State previous, Event event,
						State current) {
					switch (enteredNum) {
					case 0:
						assertEquals(
								new State(StateMachine.INITIAL_STATE_NAME),
								previous);
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
			});

			finalState.addListener(new StateListenerImpl(
					StateListener.Type.LOOP_PROCESS) {
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
			});

			machine.addListener(new StateListenerImpl(
					StateListener.Type.LOOP_PROCESS) {
				@Override
				public void onStateEnter(State previous, Event event,
						State current) {
					switch (enteredNum) {
					case 0:
						assertEquals(
								new State(StateMachine.INITIAL_STATE_NAME),
								previous);
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
			expected.add("fsm.onStateEnter");
			assertEquals(expected, real);
			expected.clear();
			real.clear();

			processedEvent = machine.process(new TypeEventB());
			assertEquals(new TypeEventB(), processedEvent);
			expected.add("finalState.onStateExit");
			expected.add("fsm.onStateExit");
			expected.add("back.onTransition");
			expected.add("fsm.onTransition");
			expected.add("startState.onStateEnter");
			expected.add("fsm.onStateEnter");
			assertEquals(expected, real);
			expected.clear();
			real.clear();
		} catch (FsmException e) {
			fail("Should not be executed");
		}

		machine.close();
	}

	@Test
	public final void testAddProcessor() {
		StateMachine machine = genStateMachine();
		State state = new State("state");
		State another = new State("another");
		Transition transition = new Transition(state, new TypeEventA(), another);
		Event processedEvent = null;

		TypePreprocessor preprocessor = new TypePreprocessor("preprocessor");

		try {
			machine.addPreprocessor(preprocessor);
			machine.addState(state);
			machine.addState(another);
			machine.addTransition(transition);
			machine.setStartState(state);

			preprocessor.addProcessor(TypeEventA.class,
					new Preprocessor.Processor<TypeEventA>() {
						@Override
						public Event process(TypeEventA event) {
							return null;
						}
					});

			preprocessor.addProcessor(TypeEventB.class,
					new Preprocessor.Processor<TypeEventB>() {
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
		} catch (FsmException e) {
			fail("Should not be executed");
		}

		machine.close();
	}

	@Test
	public final void testGetActiveState() {
		StateMachine machine = genStateMachine();
		assertNull(machine.getActiveState());

		try {
			machine.setStartState(new State("state"));
		} catch (FsmException e) {
			fail("Should not be executed");
		}

		assertEquals(new State("state"), machine.getActiveState());

		machine.close();
	}

	@Test
	public final void testGetActiveStates() {
		StateMachine machine = genStateMachine();
		assertEquals(new HashSet<State>(), machine.getActiveStates());

		try {
			machine.setStartState(new State("state"));
		} catch (FsmException e) {
			fail("Should not be executed");
		}

		Set<State> states = new HashSet<State>();
		states.add(new State("state"));
		assertEquals(states, machine.getActiveStates());

		machine.close();
	}

	@Test
	public final void testProcess() {
		StateMachine machine = genStateMachine();
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
		} catch (FsmException e) {
			fail("Should not be executed");
		}

		assertEquals(new TypeEventB(), processedEvent);
		assertEquals(state, machine.getActiveState());

		machine.close();
	}

	@Test
	public final void testGetTransition() {
		// This is a test of DeterministicStateMachine implementation, not the
		// general StateMachine interface
		DeterministicStateMachine machine = new DeterministicStateMachine("fsm");
		Transition transition = null;

		try {
			machine.getTransition(new State("state"), NullEvent.instance);
			fail("Should not be executed");
		} catch (NullPointerException e) {
			// Do nothing
		}

		try {
			machine.addTransition(new Transition(new State("state"),
					new TypeEventA(), new State("state")));

			machine.addTransition(new Transition(new State("state"),
					OtherEvent.instance, new State("state")));

			machine.addTransition(new Transition(new State("state"),
					new TimeoutEvent(42, TimeoutEvent.Type.LOOP_RESTART),
					new State("state")));

			machine.addTransition(new Transition(new State("state"),
					new TimeoutEvent(42, TimeoutEvent.Type.LOOP_NO_RESTART),
					new State("state")));

			transition = machine.getTransition(new State("state"),
					new TypeEventB());
			assertNull(transition);

			transition = machine.getTransition(new State("state"),
					new TypeEventA());
			assertNotNull(transition);
			assertTrue(transition.getEvent() instanceof TypeEventA);

			transition = machine.getTransition(new State("state"),
					new OtherEvent(new TypeEventA()));
			assertNotNull(transition);
			assertTrue(transition.getEvent() instanceof OtherEvent);

			transition = machine.getTransition(new State("state"),
					TimeoutEvent.instance_LOOP_RESTART);
			assertNotNull(transition);
			assertTrue(transition.getEvent() instanceof TimeoutEvent);
			assertEquals(TimeoutEvent.Type.LOOP_RESTART,
					((TimeoutEvent) transition.getEvent()).getType());

			transition = machine.getTransition(new State("state"),
					TimeoutEvent.instance_LOOP_NO_RESTART);
			assertNotNull(transition);
			assertTrue(transition.getEvent() instanceof TimeoutEvent);
			assertEquals(TimeoutEvent.Type.LOOP_NO_RESTART,
					((TimeoutEvent) transition.getEvent()).getType());
		} catch (FsmException e) {
			fail("Should not be executed");
		}

		machine.close();
	}

	@Test
	public final void testPreprocessorsOrder() {
		StateMachine machine = genStateMachine();
		State state = new State("state");
		State another = new State("another");
		Transition transition = new Transition(state, new TypeEventC(), another);
		Transition transitionBack = new Transition(another, new TypeEventC(),
				state);
		Event processedEvent = null;

		TypePreprocessor typePreprocessor = new TypePreprocessor("type");
		EqualsPreprocessor equalsPreprocessor = new EqualsPreprocessor("equals");

		try {
			machine.addPreprocessor(typePreprocessor);
			machine.addPreprocessor(equalsPreprocessor);
			machine.addState(state);
			machine.addState(another);
			machine.addTransition(transition);
			machine.addTransition(transitionBack);
			machine.setStartState(state);

			typePreprocessor.addProcessor(TypeEventA.class,
					new Preprocessor.Processor<TypeEventA>() {
						@Override
						public Event process(TypeEventA event) {
							return null;
						}
					});

			typePreprocessor.addProcessor(TypeEventB.class,
					new Preprocessor.Processor<TypeEventB>() {
						@Override
						public Event process(TypeEventB event) {
							return new TypeEventA();
						}
					});

			equalsPreprocessor.addProcessor(new TypeEventA(),
					new Preprocessor.Processor<TypeEventA>() {
						@Override
						public Event process(TypeEventA event) {
							return new TypeEventC();
						}
					});

			equalsPreprocessor.addProcessor(new TypeEventB(),
					new Preprocessor.Processor<TypeEventB>() {
						@Override
						public Event process(TypeEventB event) {
							fail("Should not be executed");
							return null;
						}
					});

			machine.start();

			// TypePreprocessor(TypeEventA) -> null
			processedEvent = machine.process(new TypeEventA());
			assertNull(processedEvent);
			assertEquals(state, machine.getActiveState());

			// TypePreprocessor(TypeEventB) -> TypeEventA
			// EqualsPreprocessor(TypeEventA) -> TypeEventC
			processedEvent = machine.process(new TypeEventB());
			assertEquals(new TypeEventC(), processedEvent);
			assertEquals(another, machine.getActiveState());

			// No preprocessor
			processedEvent = machine.process(new TypeEventC());
			assertEquals(new TypeEventC(), processedEvent);
			assertEquals(state, machine.getActiveState());
		} catch (FsmException e) {
			fail("Should not be executed");
		}

		machine.close();
	}

	@Test
	public final void testContainerEvent() {
		StateMachine machine = genStateMachine();
		State state = new State("state");
		State another = new State("another");
		Transition transitionA = new Transition(state,
				new ContainerEvent<Character>('A'), another);
		Transition transitionB = new Transition(state,
				new ContainerEvent<Character>('B'), another);
		Transition transitionOther = new Transition(state, OtherEvent.instance,
				state);

		Event processedEvent = null;

		try {
			machine.addState(state);
			machine.addState(another);
			machine.addTransition(transitionA);
			machine.addTransition(transitionB);
			machine.addTransition(transitionOther);
			machine.setStartState(state);
			machine.start();
		} catch (FsmException e) {
			fail("Should not be executed");
		}

		try {
			processedEvent = machine
					.process(new ContainerEvent<Character>('W'));
			assertEquals(OtherEvent.instance, processedEvent);
			assertEquals(new ContainerEvent<Character>('W'),
					((OtherEvent) processedEvent).getSourceEvent());
			assertEquals(state, machine.getActiveState());

			processedEvent = machine
					.process(new ContainerEvent<Character>('A'));
			assertEquals(new ContainerEvent<Character>('A'), processedEvent);
			assertEquals(another, machine.getActiveState());
		} catch (FsmException e) {
			fail("Should not be executed");
		}

		machine.close();
	}

	@Test
	public final void testOtherEvent() {
		StateMachine machine = genStateMachine();
		final State state = new State("state");
		final State another = new State("another");
		Transition loop = new Transition(state, new TypeEventA(), state);
		Transition transition = new Transition(state, OtherEvent.instance,
				another);
		Transition back = new Transition(another, new TypeEventA(), state);

		TransitionListenerImpl listener = new TransitionListenerImpl() {
			@Override
			public void onTransition(State source, Event event,
					State destination) {
				assertEquals(state, source);
				assertEquals(OtherEvent.instance, event);
				assertEquals(new TypeEventB(),
						((OtherEvent) event).getSourceEvent());
				assertEquals(another, destination);

				super.onTransition(source, event, destination);
			}
		};

		Event processedEvent = null;

		try {
			machine.addState(state);
			machine.addState(another);
			machine.addTransition(loop);
			machine.addTransition(transition);
			machine.addTransition(back);
			machine.setStartState(state);
			machine.start();

			transition.addListener(listener);

			processedEvent = machine.process(new TypeEventA());
			assertEquals(new TypeEventA(), processedEvent);

			processedEvent = machine.process(new TypeEventB());
			assertEquals(1, listener.transitionsNum);
			assertEquals(OtherEvent.instance, processedEvent);
			assertEquals(new TypeEventB(),
					((OtherEvent) processedEvent).getSourceEvent());
		} catch (FsmException e) {
			fail("Should not be executed");
		}

		try {
			assertEquals(another, machine.getActiveState());
			processedEvent = machine.process(new TypeEventB());
			fail("Should not be executed");
		} catch (FsmException e) {
			// No such transition
			// Do nothing
		}

		machine.close();
	}
}
