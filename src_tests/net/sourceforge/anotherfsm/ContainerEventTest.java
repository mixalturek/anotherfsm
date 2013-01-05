package net.sourceforge.anotherfsm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import net.sourceforge.anotherfsm.logger.StdStreamLoggerFactory;

import org.junit.BeforeClass;
import org.junit.Test;

public class ContainerEventTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		AnotherFsm.init(new StdStreamLoggerFactory());
	}

	@Test
	public final void testEqualsObject() {
		assertEquals(new ContainerEvent<Character>('A'),
				new ContainerEvent<Character>('A'));
		assertEquals(new ContainerEvent<TypeEventA>(new TypeEventA()),
				new ContainerEvent<TypeEventA>(new TypeEventA()));
		assertFalse(new ContainerEvent<TypeEventA>(new TypeEventA())
				.equals(new ContainerEvent<TypeEventB>(new TypeEventB())));
		assertFalse(new ContainerEvent<Object>(new Object())
				.equals(new ContainerEvent<Object>(new Object())));
	}

	@Test
	public final void testToString() {
		assertEquals("ContainerEvent(A)",
				new ContainerEvent<Character>('A').toString());
		assertEquals("ContainerEvent(TypeEventA)",
				new ContainerEvent<TypeEventA>(new TypeEventA()).toString());
	}

	@Test
	public final void testStateMachine() {
		StateMachine machine = new DeterministicStateMachine("fsm");
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
}
