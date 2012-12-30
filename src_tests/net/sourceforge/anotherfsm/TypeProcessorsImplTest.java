package net.sourceforge.anotherfsm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Test;

public class TypeProcessorsImplTest {
	@Test
	public final void testAddProcessor() {
		TypeProcessors processor = new TypeProcessorsImpl("processor");
		try {
			processor.addProcessor(TypeEventA.class,
					new Processor<TypeEventA>() {
						@Override
						public Event process(TypeEventA event) {
							return new TypeEventB();
						}
					});
		} catch (FsmException e) {
			fail("Should not be executed");
		}

		try {
			processor.addProcessor(TypeEventA.class,
					new Processor<TypeEventA>() {
						@Override
						public Event process(TypeEventA event) {
							return new TypeEventB();
						}
					});
			fail("Should not be executed");
		} catch (FsmException e) {
			// Do nothing
		}

		try {
			processor.addProcessor(TypeEventA.class, null);
			fail("Should not be executed");
		} catch (FsmException e) {
			fail("Should not be executed");
		} catch (NullPointerException e) {
			// Do nothing
		}

		try {
			processor.addProcessor(null, new Processor<TypeEventA>() {
				@Override
				public Event process(TypeEventA event) {
					return new TypeEventB();
				}
			});
			fail("Should not be executed");
		} catch (FsmException e) {
			fail("Should not be executed");
		} catch (NullPointerException e) {
			// Do nothing
		}
	}

	@Test
	public final void testProcess() {
		TypeProcessors processor = new TypeProcessorsImpl("processor");
		Event processedEvent = null;

		try {
			processor.addProcessor(TypeEventA.class,
					new Processor<TypeEventA>() {
						@Override
						public Event process(TypeEventA event) {
							return new TypeEventB();
						}
					});

			processor.addProcessor(TypeEventB.class,
					new Processor<TypeEventB>() {
						@Override
						public Event process(TypeEventB event) {
							return null;
						}
					});
		} catch (FsmException e) {
			fail("Should not be executed");
		}

		try {
			processedEvent = processor.process(null);
			fail("Should not be executed");
		} catch (FsmException e) {
			fail("Should not be executed");
		} catch (NullPointerException e) {
			// Do nothing
		}

		try {
			processedEvent = processor.process(new TypeEventA());
			assertEquals(new TypeEventB(), processedEvent);

			processedEvent = processor.process(new TypeEventB());
			assertNull(processedEvent);
		} catch (FsmException e) {
			fail("Should not be executed");
		}
	}

	@Test
	public final void testProcessNoProcessor() {
		TypeProcessors processor = new TypeProcessorsImpl("processor");
		Event processedEvent = null;

		try {
			processedEvent = processor.process(new TypeEventA());
			assertEquals(new TypeEventA(), processedEvent);

			processedEvent = processor.process(new TypeEventB());
			assertEquals(new TypeEventB(), processedEvent);
		} catch (FsmException e) {
			fail("Should not be executed");
		}
	}
}
