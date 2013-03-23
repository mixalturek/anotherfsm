package net.sourceforge.anotherfsm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.concurrent.LinkedBlockingQueue;

import net.sourceforge.anotherfsm.logger.NoLoggerJUnitFactory;

import org.junit.BeforeClass;
import org.junit.Test;

public class ThreadProcessorTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		AnotherFsm.setLoggerFactory(new NoLoggerJUnitFactory());
	}

	@Test
	public void testStartClose() {
		// The object/thread's name must be unique
		TimeoutStateMachine machine = new TimeoutStateMachine("testStartClose",
				false);
		ThreadProcessor processor = new ThreadProcessor(machine, false,
				new LinkedBlockingQueue<Event>());

		assertFalse(UnitTestHelpers.isThreadRunning(machine.getThreadName()));
		assertFalse(UnitTestHelpers.isThreadRunning(processor.getThreadName()));

		try {
			machine.setStartState(new State("start"));
			processor.start();
		} catch (FsmException e) {
			fail("Should not be executed: " + e);
		}

		UnitTestHelpers.sleepThreadCommunicationDelay();

		assertTrue(UnitTestHelpers.isThreadRunning(machine.getThreadName()));
		assertTrue(UnitTestHelpers.isThreadRunning(processor.getThreadName()));

		processor.close();
		UnitTestHelpers.sleepThreadCommunicationDelay();

		assertFalse(UnitTestHelpers.isThreadRunning(machine.getThreadName()));
		assertFalse(UnitTestHelpers.isThreadRunning(processor.getThreadName()));

		processor.close();
		processor.close();
		processor.close();

		assertFalse(UnitTestHelpers.isThreadRunning(machine.getThreadName()));
		assertFalse(UnitTestHelpers.isThreadRunning(processor.getThreadName()));
	}

	@Test
	public void testGetName() {
		Processor processor = new ThreadProcessor(new TypePreprocessor("test"));
		assertEquals("test", processor.getName());
	}

	@Test
	public void testProcess() {
		StateMachine machine = new TimeoutStateMachine("test", false);
		State state = new State("state");
		Transition transition = new Transition(state, new TypeEventA(), state);

		TransitionListenerImpl listener = new TransitionListenerImpl();
		transition.addListener(listener);

		Processor processor = new ThreadProcessor(machine, false,
				new LinkedBlockingQueue<Event>());

		Event addedEvent = null;

		try {
			machine.addState(state);
			machine.addTransition(transition);
			machine.setStartState(state);

			processor.start();
		} catch (FsmException e) {
			fail("Should not be executed: " + e);
		}

		assertEquals(0, listener.transitionsNum);

		try {
			processor.process(null);
			fail("Should not be executed");
		} catch (FsmException e) {
			fail("Should not be executed: " + e);
		} catch (NullPointerException e) {
			assertEquals("Object must not be null: event", e.getMessage());
		}

		assertEquals(0, listener.transitionsNum);

		try {
			// Existing transition
			addedEvent = processor.process(new TypeEventA());
			UnitTestHelpers.sleepThreadCommunicationDelay();

			assertEquals(new TypeEventA(), addedEvent);
			assertEquals(1, listener.transitionsNum);

			// No such transition
			addedEvent = processor.process(new TypeEventB());
			UnitTestHelpers.sleepThreadCommunicationDelay();

			assertEquals(new TypeEventB(), addedEvent);
			assertEquals(1, listener.transitionsNum);
		} catch (FsmException e) {
			fail("Should not be executed: " + e);
		}

		processor.close();
	}
}
