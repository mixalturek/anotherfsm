package net.sourceforge.anotherfsm;

import static org.junit.Assert.assertEquals;
import net.sourceforge.anotherfsm.logger.NoLoggerFactory;
import net.sourceforge.anotherfsm.logger.StdStreamLoggerFactory;

import org.junit.Test;

public class AnotherFsmTest {
	@Test
	public synchronized void testSetLoggerFactory() {
		AnotherFsm.setLoggerFactory(new StdStreamLoggerFactory());
		assertEquals("StdStreamLogger",
				AnotherFsm.getLogger(AnotherFsmTest.class).getClass()
						.getSimpleName());

		AnotherFsm.setLoggerFactory(new NoLoggerFactory());
		assertEquals("NoLogger", AnotherFsm.getLogger(AnotherFsmTest.class)
				.getClass().getSimpleName());
	}

	@Test
	public synchronized void testGetLoggerClassOfQ() {
		AnotherFsm.setLoggerFactory(new StdStreamLoggerFactory());
		assertEquals("AnotherFsmTest",
				AnotherFsm.getLogger(AnotherFsmTest.class).getName());
	}

	@Test
	public synchronized void testGetLoggerClassOfQString() {
		AnotherFsm.setLoggerFactory(new StdStreamLoggerFactory());
		assertEquals("AnotherFsmTest.instance",
				AnotherFsm.getLogger(AnotherFsmTest.class, "instance")
						.getName());
	}

	@Test
	public synchronized void testGetLoggerString() {
		AnotherFsm.setLoggerFactory(new StdStreamLoggerFactory());
		assertEquals("name", AnotherFsm.getLogger("name").getName());
	}

	@Test
	public synchronized void testAnotherFsm() {
		new AnotherFsm();
	}
}
