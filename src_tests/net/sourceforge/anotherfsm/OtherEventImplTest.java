package net.sourceforge.anotherfsm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

import net.sourceforge.anotherfsm.logger.StdStreamLoggerFactory;

import org.junit.BeforeClass;
import org.junit.Test;

public class OtherEventImplTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		AnotherFsm.init(new StdStreamLoggerFactory());
	}

	@Test
	public final void hashCodeTest() {
		assertEquals(OtherEvent.HASH_CODE,
				new OtherEventImpl(new TypeEventA()).hashCode());

		assertEquals(OtherEvent.HASH_CODE,
				new NonstandardOtherEvent().hashCode());
	}

	@Test
	public final void equalsTest() {
		assertEquals(new OtherEventImpl(new TypeEventA()), new OtherEventImpl(
				new TypeEventB()));

		assertEquals(new OtherEventImpl(new TypeEventB()), new OtherEventImpl(
				new TypeEventA()));

		assertEquals(new NonstandardOtherEvent(), new OtherEventImpl(
				new TypeEventA()));

		assertEquals(new OtherEventImpl(new TypeEventA()),
				new NonstandardOtherEvent());
	}

	@Test
	public final void testToString() {
		assertEquals("OtherEventImpl(TypeEventA)", new OtherEventImpl(
				new TypeEventA()).toString());
	}

	@Test
	public final void testStateMachine() {
		StateMachine machine = new DeterministicStateMachine("fsm");
		final State state = new State("state");
		final State another = new State("another");
		Transition loop = new Transition(state, new TypeEventA(), state);
		Transition transition = new Transition(state, OtherEventImpl.INSTANCE,
				another);
		Transition back = new Transition(another, new TypeEventA(), state);

		TransitionListenerImpl listener = new TransitionListenerImpl() {
			@Override
			public void onTransition(State source, Event event,
					State destination) {
				assertEquals(state, source);
				assertEquals(OtherEventImpl.INSTANCE, event);
				assertEquals(new TypeEventB(),
						((OtherEventImpl) event).getSourceEvent());
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
			assertEquals(OtherEventImpl.INSTANCE, processedEvent);
			assertEquals(new TypeEventB(),
					((OtherEventImpl) processedEvent).getSourceEvent());
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

		try {
			machine.close();
		} catch (IOException e) {
			fail("Should not be executed");
		}
	}
}
