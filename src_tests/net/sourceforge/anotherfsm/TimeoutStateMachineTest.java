/*
 *  Copyright 2013 Michal Turek, AnotherFSM
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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class TimeoutStateMachineTest extends DeterministicStateMachineTest {
	/** The default timeout for the tests. */
	private static final long TIMEOUT = 10;

	@Override
	protected StateMachine genStateMachine() {
		return new TimeoutStateMachine("fsm");
	}

	@Test
	public final void testAddTransitionTimeoutEvent() {
		StateMachine machine = new TimeoutStateMachine("fsm");
		State state = new State("state");

		try {
			machine.addState(state);
			machine.addTransition(new Transition(state, new TimeoutEvent(42,
					TimeoutEvent.Type.LOOP_RESTART), state));
		} catch (FsmException e) {
			fail("Should not be executed");
		}

		try {
			machine.addTransition(new Transition(state, new TimeoutEvent(42,
					TimeoutEvent.Type.LOOP_RESTART), state));
			fail("Should not be executed");
		} catch (FsmException e) {
			assertTrue(e.getMessage().contains("Transition already defined"));
		}

		try {
			machine.addTransition(new Transition(state, new TimeoutEvent(42,
					TimeoutEvent.Type.LOOP_NO_RESTART), state));
		} catch (FsmException e) {
			fail("Should not be executed");
		}

		try {
			machine.addTransition(new Transition(state, new TimeoutEvent(10,
					TimeoutEvent.Type.LOOP_NO_RESTART), state));
			fail("Should not be executed");
		} catch (FsmException e) {
			assertTrue(e.getMessage().contains("Transition already defined"));
		}

		machine.close();
	}

	@Test
	public final void testStartTimeout() {
		StateMachine machine = new TimeoutStateMachine("fsm");

		try {
			machine.start();
			fail("Should not be executed");
		} catch (FsmException e) {
			assertTrue(e.getMessage().contains("Start state not defined"));
		}

		try {
			machine.setStartState(new State("state"));
			machine.start();
		} catch (FsmException e) {
			fail("Should not be executed");
		}

		machine.close();
	}

	@Test
	public final void testTimeoutInStartStateLoopRestart() {
		StateMachine machine = new TimeoutStateMachine("fsm");
		State startState = new State("startState");
		State timeoutState = new State("timeoutState");
		Transition toTimeout = new Transition(startState, new TimeoutEvent(
				TIMEOUT, TimeoutEvent.Type.LOOP_RESTART), timeoutState);

		try {
			machine.addState(startState);
			machine.addState(timeoutState);
			machine.addTransition(toTimeout);
			machine.setStartState(startState);
			machine.start();

			Thread.sleep(TIMEOUT * 2);

			assertEquals(timeoutState, machine.getActiveState());
		} catch (FsmException e) {
			fail("Should not be executed");
		} catch (InterruptedException e) {
			fail("Should not be executed");
		}

		machine.close();
	}

	@Test
	public final void testTimeoutInStartStateLoopNoRestart() {
		StateMachine machine = new TimeoutStateMachine("fsm");
		State startState = new State("startState");
		State timeoutState = new State("timeoutState");
		Transition toTimeout = new Transition(startState, new TimeoutEvent(
				TIMEOUT, TimeoutEvent.Type.LOOP_NO_RESTART), timeoutState);

		try {
			machine.addState(startState);
			machine.addState(timeoutState);
			machine.addTransition(toTimeout);
			machine.setStartState(startState);
			machine.start();

			Thread.sleep(TIMEOUT * 2);

			assertEquals(timeoutState, machine.getActiveState());
		} catch (FsmException e) {
			fail("Should not be executed");
		} catch (InterruptedException e) {
			fail("Should not be executed");
		}

		machine.close();
	}

	@Test
	public final void testClose() {
		StateMachine machine = new TimeoutStateMachine("fsm");
		machine.close();

		try {
			machine.setStartState(new State("state"));
			machine.start();
		} catch (FsmException e) {
			fail("Should not be executed");
		}

		machine.close();
		machine.close();
		machine.close();
	}

	@Test
	public final void testProcessNoTimeout() {
		StateMachine machine = new TimeoutStateMachine("fsm");
		State startState = new State("startState");
		State finalState = new State("finalState", State.Type.FINAL);
		Transition toStart = new Transition(startState, new TypeEventA(),
				finalState);
		Transition toFinal = new Transition(finalState, new TypeEventB(),
				startState);
		Event processedEvent = null;

		try {
			machine.addState(startState);
			machine.addState(finalState);
			machine.addTransition(toStart);
			machine.addTransition(toFinal);
			machine.setStartState(startState);
			machine.start();
			assertEquals(startState, machine.getActiveState());

			processedEvent = machine.process(new TypeEventA());
			assertEquals(new TypeEventA(), processedEvent);
			assertEquals(finalState, machine.getActiveState());

			processedEvent = machine.process(new TypeEventB());
			assertEquals(new TypeEventB(), processedEvent);
			assertEquals(startState, machine.getActiveState());
		} catch (FsmException e) {
			fail("Should not be executed");
		}

		machine.close();
	}

	@Test
	public final void testProcessTimeout() {
		final TimeoutEvent.Type TYPE = TimeoutEvent.Type.LOOP_RESTART;

		StateMachine machine = new TimeoutStateMachine("fsm");
		State startState = new State("startState");
		final State finalState = new State("finalState", State.Type.FINAL);
		final State timeoutState = new State("timeoutState");
		Transition toFinal = new Transition(startState, new TypeEventA(),
				finalState);

		Transition toTimeout = new Transition(finalState, new TimeoutEvent(
				TIMEOUT, TYPE), timeoutState);

		TransitionListenerImpl listener = new TransitionListenerImpl() {
			@Override
			public void onTransition(State source, Event event,
					State destination) {
				super.onTransition(source, event, destination);

				assertEquals(finalState, source);
				assertEquals(timeoutState, destination);

				assertTrue(event instanceof TimeoutEvent);
				assertEquals(TIMEOUT, ((TimeoutEvent) event).getTimeout());
				assertEquals(TYPE, ((TimeoutEvent) event).getType());
			}
		};

		toTimeout.addListener(listener);

		Event processedEvent = null;

		try {
			machine.addState(startState);
			machine.addState(finalState);
			machine.addTransition(toFinal);
			machine.addTransition(toTimeout);
			machine.setStartState(startState);
			machine.start();
			assertEquals(startState, machine.getActiveState());

			processedEvent = machine.process(new TypeEventA());
			assertEquals(new TypeEventA(), processedEvent);
			assertEquals(finalState, machine.getActiveState());

			Thread.sleep(TIMEOUT * 2);

			assertEquals(timeoutState, machine.getActiveState());
			assertEquals(1, listener.transitionsNum);
		} catch (FsmException e) {
			fail("Should not be executed");
		} catch (InterruptedException e) {
			fail("Should not be executed");
		}

		machine.close();
	}

	@Test
	public final void testProcessLoopRestart() {
		final TimeoutEvent.Type TYPE = TimeoutEvent.Type.LOOP_RESTART;

		StateMachine machine = new TimeoutStateMachine("fsm");
		State startState = new State("startState");
		final State finalState = new State("finalState", State.Type.FINAL);
		final State timeoutState = new State("timeoutState");
		Transition toFinal = new Transition(startState, new TypeEventA(),
				finalState);

		Transition toFinalLoop = new Transition(finalState, new TypeEventB(),
				finalState);

		Transition toTimeout = new Transition(finalState, new TimeoutEvent(
				TIMEOUT, TYPE), timeoutState);

		TransitionListenerImpl listener = new TransitionListenerImpl() {
			@Override
			public void onTransition(State source, Event event,
					State destination) {
				super.onTransition(source, event, destination);

				assertEquals(finalState, source);
				assertEquals(timeoutState, destination);

				assertTrue(event instanceof TimeoutEvent);
				assertEquals(TIMEOUT, ((TimeoutEvent) event).getTimeout());
				assertEquals(TYPE, ((TimeoutEvent) event).getType());
			}
		};

		toTimeout.addListener(listener);

		Event processedEvent = null;

		try {
			machine.addState(startState);
			machine.addState(finalState);
			machine.addTransition(toFinal);
			machine.addTransition(toTimeout);
			machine.addTransition(toFinalLoop);
			machine.setStartState(startState);
			machine.start();
			assertEquals(startState, machine.getActiveState());

			processedEvent = machine.process(new TypeEventA());
			assertEquals(new TypeEventA(), processedEvent);
			assertEquals(finalState, machine.getActiveState());

			long startTime = System.currentTimeMillis();

			int i;
			for (i = 0; i < 5; ++i) {
				Thread.sleep(TIMEOUT / 2);
				processedEvent = machine.process(new TypeEventB());
				assertEquals(new TypeEventB(), processedEvent);
				assertEquals(finalState, machine.getActiveState());
			}

			assertEquals(5, i);
			// 27 during development
			assertTrue(System.currentTimeMillis() - startTime > TIMEOUT * 2);

			assertEquals(0, listener.transitionsNum);

			Thread.sleep(TIMEOUT * 2);

			assertEquals(timeoutState, machine.getActiveState());
			assertEquals(1, listener.transitionsNum);
		} catch (FsmException e) {
			fail("Should not be executed");
		} catch (InterruptedException e) {
			fail("Should not be executed");
		}

		machine.close();
	}

	@Test
	public final void testProcessLoopNoRestart() {
		StateMachine machine = new TimeoutStateMachine("fsm");
		State startState = new State("startState");
		final State finalState = new State("finalState", State.Type.FINAL);
		final State timeoutState = new State("timeoutState");
		Transition toFinal = new Transition(startState, new TypeEventA(),
				finalState);

		Transition toFinalLoop = new Transition(finalState, new TypeEventB(),
				finalState);

		Transition toTimeout = new Transition(finalState, new TimeoutEvent(
				TIMEOUT, TimeoutEvent.Type.LOOP_NO_RESTART), timeoutState);

		Transition toTimeoutLoop = new Transition(timeoutState,
				new TypeEventB(), timeoutState);

		TransitionListenerImpl listener = new TransitionListenerImpl() {
			@Override
			public void onTransition(State source, Event event,
					State destination) {
				super.onTransition(source, event, destination);

				assertEquals(finalState, source);
				assertEquals(timeoutState, destination);

				assertTrue(event instanceof TimeoutEvent);
				assertEquals(TIMEOUT, ((TimeoutEvent) event).getTimeout());
				assertEquals(TimeoutEvent.Type.LOOP_NO_RESTART,
						((TimeoutEvent) event).getType());
			}
		};

		toTimeout.addListener(listener);

		Event processedEvent = null;

		try {
			machine.addState(startState);
			machine.addState(finalState);
			machine.addTransition(toFinal);
			machine.addTransition(toTimeout);
			machine.addTransition(toFinalLoop);
			machine.addTransition(toTimeoutLoop);
			machine.setStartState(startState);
			machine.start();
			assertEquals(startState, machine.getActiveState());

			processedEvent = machine.process(new TypeEventA());
			assertEquals(new TypeEventA(), processedEvent);
			assertEquals(finalState, machine.getActiveState());

			long startTime = System.currentTimeMillis();

			int i;
			for (i = 0; i < 20; ++i) {
				if (machine.getActiveState().equals(timeoutState))
					break;

				Thread.sleep(TIMEOUT / 4);

				processedEvent = machine.process(new TypeEventB());
				assertEquals(new TypeEventB(), processedEvent);
			}

			// System.err.println(i);
			// 4 during development
			assertTrue(i >= 2 && i <= 6);
			// 12 during development
			assertTrue(System.currentTimeMillis() - startTime < TIMEOUT * 3);
			assertEquals(timeoutState, machine.getActiveState());
			assertEquals(1, listener.transitionsNum);
		} catch (FsmException e) {
			fail("Should not be executed");
		} catch (InterruptedException e) {
			fail("Should not be executed");
		}

		machine.close();
	}

	@Test
	public final void testRuntimeExcpetionInTimeoutCallback() {
		class TestStateMachine extends TimeoutStateMachine {
			public boolean exceptionOccurred = false;

			public TestStateMachine(String name) {
				super(name);
			}

			@Override
			public Event process(Event event) throws FsmException {
				try {
					super.process(event);
					fail("Should not be executed");
				} catch (NullPointerException e) {
					assertEquals("Testing exception", e.getMessage());
					exceptionOccurred = true;

					// The thread is not finished but notifyEnter() and
					// scheduling of a new timeout is not processed
				}

				return event;
			}
		}

		TestStateMachine machine = new TestStateMachine("fsm");
		final State startState = new State("startState");
		Transition toTimeout = new Transition(startState, new TimeoutEvent(
				TIMEOUT, TimeoutEvent.Type.LOOP_RESTART), startState);

		TransitionListenerImpl listener = new TransitionListenerImpl() {
			@Override
			public void onTransition(State source, Event event,
					State destination) {
				super.onTransition(source, event, destination);

				// Serious error in the client code
				throw new NullPointerException("Testing exception");
			}
		};

		toTimeout.addListener(listener);

		try {
			machine.addState(startState);
			machine.addTransition(toTimeout);
			machine.setStartState(startState);
			machine.start();

			Thread.sleep(TIMEOUT * 3);

			// Only the first timeout callback was processed. The timer thread
			// then finished on unhandled runtime exception and no other timeout
			// will be ever processed. Such situation is bad but it is better to
			// be deterministic in such case than hide more serious error.

			assertEquals(1, listener.transitionsNum);
			assertTrue(machine.exceptionOccurred);
		} catch (FsmException e) {
			fail("Should not be executed");
		} catch (InterruptedException e) {
			fail("Should not be executed");
		}

		machine.close();
	}

	@Test
	public final void testProcessLoopTimeoutTransition() {
		StateMachine machine = new TimeoutStateMachine("fsm");
		final State startState = new State("startState");
		Transition toTimeout = new Transition(startState, new TimeoutEvent(
				TIMEOUT, TimeoutEvent.Type.LOOP_RESTART), startState);

		TransitionListenerImpl listener = new TransitionListenerImpl() {
			@Override
			public void onTransition(State source, Event event,
					State destination) {
				super.onTransition(source, event, destination);

				assertEquals(startState, source);
				assertEquals(startState, destination);

				assertTrue(event instanceof TimeoutEvent);
				assertEquals(TIMEOUT, ((TimeoutEvent) event).getTimeout());
				assertEquals(TimeoutEvent.Type.LOOP_RESTART,
						((TimeoutEvent) event).getType());
			}
		};

		toTimeout.addListener(listener);

		try {
			machine.addState(startState);
			machine.addTransition(toTimeout);
			machine.setStartState(startState);
			machine.start();

			Thread.sleep(TIMEOUT * 4);

			assertEquals(startState, machine.getActiveState());
			// 3 during development
			assertTrue(listener.transitionsNum >= 3
					&& listener.transitionsNum <= 5);
		} catch (FsmException e) {
			fail("Should not be executed");
		} catch (InterruptedException e) {
			fail("Should not be executed");
		}

		machine.close();
	}

	@Test
	public final void testProcessTwoTimeouts() {
		StateMachine machine = new TimeoutStateMachine("fsm");
		final State startState = new State("startState");
		final State timeoutState = new State("timeoutState");
		Transition restartTimeout = new Transition(startState,
				new TimeoutEvent(TIMEOUT, TimeoutEvent.Type.LOOP_RESTART),
				startState);
		Transition noRestartTimeout = new Transition(
				startState,
				new TimeoutEvent(TIMEOUT * 3, TimeoutEvent.Type.LOOP_NO_RESTART),
				timeoutState);

		TransitionListenerImpl restartListener = new TransitionListenerImpl();
		TransitionListenerImpl noRestartListener = new TransitionListenerImpl();
		restartTimeout.addListener(restartListener);
		noRestartTimeout.addListener(noRestartListener);

		try {
			machine.addState(startState);
			machine.addState(timeoutState);
			machine.addTransition(restartTimeout);
			machine.addTransition(noRestartTimeout);
			machine.setStartState(startState);
			machine.start();

			Thread.sleep(TIMEOUT * 4);

			// 2 during development
			assertTrue(restartListener.transitionsNum >= 2
					&& restartListener.transitionsNum <= 4);
			assertEquals(1, noRestartListener.transitionsNum);
			assertEquals(timeoutState, machine.getActiveState());
		} catch (FsmException e) {
			fail("Should not be executed");
		} catch (InterruptedException e) {
			fail("Should not be executed");
		}

		machine.close();
	}
}
