package net.sourceforge.anotherfsm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.concurrent.LinkedBlockingQueue;

import net.sourceforge.anotherfsm.logger.NoLoggerFactory;

import org.junit.BeforeClass;
import org.junit.Test;

public class ThreadProcessorTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		AnotherFsm.setLoggerFactory(new NoLoggerFactory());
	}

	@Test
	public void testStartClose() {
		int numThreads = UnitTestHelpers.getNumberOfLivingThreads();

		StateMachine machine = new TimeoutStateMachine("test", false);
		Processor processor = new ThreadProcessor(machine, false,
				new LinkedBlockingQueue<Event>());

		try {
			machine.setStartState(new State("start"));
			processor.start();
		} catch (FsmException e) {
			fail("Should not be executed: " + e);
		}

		UnitTestHelpers.sleepThreadCommunicationDelay();

		// 1 thread for ThreadProcessor and 1 thread for TimeoutStateMachine
		assertEquals(numThreads + 2, UnitTestHelpers.getNumberOfLivingThreads());

		processor.close();
		UnitTestHelpers.sleepThreadCommunicationDelay();

		assertEquals(numThreads, UnitTestHelpers.getNumberOfLivingThreads());

		processor.close();
		processor.close();
		processor.close();
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
