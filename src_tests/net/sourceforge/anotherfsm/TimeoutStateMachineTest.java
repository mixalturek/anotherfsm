package net.sourceforge.anotherfsm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

public class TimeoutStateMachineTest {
	/** The default timeout for the tests. */
	private static final long TIMEOUT = 10;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		BasicConfigurator.configure();
	}

	@Test
	public final void testAddTransition() {
		StateMachine machine = new TimeoutStateMachine("fsm");
		State state = new State("state");

		try {
			// OK
			machine.addState(state);
			machine.addTransition(new Transition(state, new TimeoutEventImpl(
					42, TimeoutEvent.Type.LOOP_RESTART), state));
		} catch (FsmException e) {
			fail("Should not be executed");
		}

		try {
			// The same timeout
			machine.addTransition(new Transition(state, new TimeoutEventImpl(
					42, TimeoutEvent.Type.LOOP_RESTART), state));
			fail("Should not be executed");
		} catch (FsmException e) {
			assertTrue(e.getMessage().contains("Transition already defined"));
		}

		try {
			// Different timeout
			machine.addTransition(new Transition(state, new TimeoutEventImpl(
					10, TimeoutEvent.Type.LOOP_NO_RESTART), state));
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

	@Test
	public final void testStart() {
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
			machine.close();
		} catch (FsmException e) {
			fail("Should not be executed");
		} catch (IOException e) {
			fail("Should not be executed");
		}
	}

	@Test
	public final void testTimeoutInStartStateLoopRestart() {
		StateMachine machine = new TimeoutStateMachine("fsm");
		State startState = new State("startState");
		State timeoutState = new State("timeoutState");
		Transition toTimeout = new Transition(startState, new TimeoutEventImpl(
				TIMEOUT, TimeoutEvent.Type.LOOP_RESTART), timeoutState);

		try {
			machine.addState(startState);
			machine.addState(timeoutState);
			machine.addTransition(toTimeout);
			machine.setStartState(startState);
			machine.start();

			Thread.sleep(TIMEOUT * 2);

			assertEquals(timeoutState, machine.getActiveState());

			machine.close();
		} catch (FsmException e) {
			fail("Should not be executed");
		} catch (IOException e) {
			fail("Should not be executed");
		} catch (InterruptedException e) {
			fail("Should not be executed");
		}
	}

	@Test
	public final void testTimeoutInStartStateLoopNoRestart() {
		StateMachine machine = new TimeoutStateMachine("fsm");
		State startState = new State("startState");
		State timeoutState = new State("timeoutState");
		Transition toTimeout = new Transition(startState, new TimeoutEventImpl(
				TIMEOUT, TimeoutEvent.Type.LOOP_NO_RESTART), timeoutState);

		try {
			machine.addState(startState);
			machine.addState(timeoutState);
			machine.addTransition(toTimeout);
			machine.setStartState(startState);
			machine.start();

			Thread.sleep(TIMEOUT * 2);

			assertEquals(startState, machine.getActiveState());

			machine.close();
		} catch (FsmException e) {
			fail("Should not be executed");
		} catch (IOException e) {
			fail("Should not be executed");
		} catch (InterruptedException e) {
			fail("Should not be executed");
		}
	}

	@Test
	public final void testClose() {
		StateMachine machine = new TimeoutStateMachine("fsm");

		try {
			machine.close();
		} catch (IOException e) {
			fail("Should not be executed");
		}

		try {
			machine.setStartState(new State("state"));
			machine.start();
			machine.close();
		} catch (FsmException e) {
			fail("Should not be executed");
		} catch (IOException e) {
			fail("Should not be executed");
		}
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

			machine.close();
		} catch (FsmException e) {
			fail("Should not be executed");
		} catch (IOException e) {
			fail("Should not be executed");
		}
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

		Transition toTimeout = new Transition(finalState, new TimeoutEventImpl(
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

			machine.close();
		} catch (FsmException e) {
			fail("Should not be executed");
		} catch (IOException e) {
			fail("Should not be executed");
		} catch (InterruptedException e) {
			fail("Should not be executed");
		}
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

		Transition toTimeout = new Transition(finalState, new TimeoutEventImpl(
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
			assertTrue("27 during development", System.currentTimeMillis()
					- startTime > TIMEOUT * 2);

			assertEquals(0, listener.transitionsNum);

			Thread.sleep(TIMEOUT * 2);

			assertEquals(timeoutState, machine.getActiveState());
			assertEquals(1, listener.transitionsNum);

			machine.close();
		} catch (FsmException e) {
			fail("Should not be executed");
		} catch (IOException e) {
			fail("Should not be executed");
		} catch (InterruptedException e) {
			fail("Should not be executed");
		}
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

		Transition toTimeout = new Transition(finalState, new TimeoutEventImpl(
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
				Thread.sleep(TIMEOUT / 4);

				processedEvent = machine.process(new TypeEventB());
				assertEquals(new TypeEventB(), processedEvent);

				if (machine.getActiveState().equals(timeoutState))
					break;
			}

			// System.err.println(i);
			assertTrue("4 during development", i >= 3 && i <= 5);
			assertTrue("12 during development", System.currentTimeMillis()
					- startTime < TIMEOUT * 2);
			assertEquals(timeoutState, machine.getActiveState());
			assertEquals(1, listener.transitionsNum);

			machine.close();
		} catch (FsmException e) {
			fail("Should not be executed");
		} catch (IOException e) {
			fail("Should not be executed");
		} catch (InterruptedException e) {
			fail("Should not be executed");
		}
	}

	@Test
	public final void testProcessNullPointerExcpetionInTimeoutCallback() {
		fail("Not implemented yet"); // TODO:
	}

	@Test
	public final void testProcessZeroTimeout() {
		fail("Not implemented yet"); // TODO:
	}

	@Test
	public final void testProcessLoopTimeoutTransition() {
		fail("Not implemented yet"); // TODO:
	}
}
