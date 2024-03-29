package net.sourceforge.anotherfsm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.concurrent.BlockingQueue;
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

	@Test
	public void testProcessTooShortQueue() {
		Processor processor = new ThreadProcessor(new TypePreprocessor("test"),
				false, new LinkedBlockingQueue<Event>(3));

		// Processor not started to simulate too short queue for input events

		try {
			processor.process(NullEvent.instance);
			processor.process(NullEvent.instance);
			processor.process(NullEvent.instance);
		} catch (FsmException e) {
			fail("Should not be executed: " + e);
		}

		try {
			processor.process(NullEvent.instance);
			fail("Should not be executed");
		} catch (FsmException e) {
			assertTrue(e.getMessage().contains(
					"Adding event to the queue failed"));
		}

		processor.close();
	}

	@Test
	public final void testRuntimeExceptionInCallback() {
		class TestThreadProcessor extends ThreadProcessor {
			public int numExceptions = 0;

			public TestThreadProcessor(Processor processor, boolean daemon,
					BlockingQueue<Event> queue) {
				super(processor, daemon, queue);
			}

			@Override
			public void run() {
				try {
					super.run();
					fail("Should not be executed");
				} catch (NullPointerException e) {
					assertEquals("Testing exception", e.getMessage());
					++numExceptions;

					// The thread is not finished but notifyEnter() and
					// scheduling of a new timeout is not processed
				}
			}
		}

		TypePreprocessor preprocessor = new TypePreprocessor("processor");
		TestThreadProcessor processor = new TestThreadProcessor(preprocessor,
				false, new LinkedBlockingQueue<Event>(3));

		try {
			preprocessor.addProcessor(TypeEventA.class,
					new Preprocessor.Processor<TypeEventA>() {
						@Override
						public Event process(TypeEventA event) {
							// Serious error in the client code
							throw new NullPointerException("Testing exception");
						}
					});
			processor.start();
		} catch (FsmException e) {
			fail("Should not be executed: " + e);
		}

		try {
			processor.process(new TypeEventA());
		} catch (FsmException e) {
			fail("Should not be executed: " + e);
		}

		UnitTestHelpers.sleepThreadCommunicationDelay();
		assertEquals(1, processor.numExceptions);
		processor.close();
	}
}
