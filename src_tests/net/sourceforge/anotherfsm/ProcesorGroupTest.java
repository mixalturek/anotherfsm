package net.sourceforge.anotherfsm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class ProcesorGroupTest {
	@Test
	public void testAddProcessorGetName() {
		ProcesorGroup group = new ProcesorGroup();
		assertEquals("ProcesorGroup()", group.getName());

		group.addProcessor(new TypePreprocessor("type"));
		assertEquals("ProcesorGroup(type, )", group.getName());

		group.addProcessor(new EqualsPreprocessor("equals"));
		assertEquals("ProcesorGroup(type, equals, )", group.getName());
	}

	@Test
	public void testStartClose() {
		ProcesorGroup group = new ProcesorGroup();
		group.addProcessor(new TypePreprocessor("type"));
		group.addProcessor(new EqualsPreprocessor("equals"));

		try {
			group.start();
		} catch (FsmException e) {
			fail("Should not be executed: " + e);
		}

		group.close();
	}

	@Test
	public void testProcess() {
		ProcesorGroup group = new ProcesorGroup();
		group.addProcessor(new TypePreprocessor("type"));
		group.addProcessor(new EqualsPreprocessor("equals"));

		try {
			group.start();
			group.process(new TypeEventA());
			group.process(new TypeEventB());
		} catch (FsmException e) {
			fail("Should not be executed: " + e);
		}

		group.close();
	}
}
