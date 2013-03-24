package net.sourceforge.anotherfsm.logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import net.sourceforge.anotherfsm.AnotherFsm;

import org.junit.BeforeClass;
import org.junit.Test;

public class BasicLoggerTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		AnotherFsm.setLoggerFactory(new BasicLoggerFactory());
	}

	@Test
	public void testGetName() {
		assertEquals("BasicLogger", AnotherFsm.getLogger(BasicLoggerTest.class)
				.getName());
		assertEquals("BasicLogger",
				AnotherFsm.getLogger(BasicLoggerTest.class, "instance")
						.getName());
		assertEquals("BasicLogger", AnotherFsm.getLogger("name").getName());
	}

	@Test
	public void testFatalString() {
		AnotherFsm.getLogger(BasicLoggerTest.class).fatal("test");
	}

	@Test
	public void testFatalStringThrowable() {
		AnotherFsm.getLogger(BasicLoggerTest.class).fatal("test",
				new NullPointerException("test"));
	}

	@Test
	public void testErrorString() {
		AnotherFsm.getLogger(BasicLoggerTest.class).error("test");
	}

	@Test
	public void testErrorStringThrowable() {
		AnotherFsm.getLogger(BasicLoggerTest.class).error("test",
				new NullPointerException("test"));
	}

	@Test
	public void testWarnString() {
		AnotherFsm.getLogger(BasicLoggerTest.class).warn("test");
	}

	@Test
	public void testWarnStringThrowable() {
		AnotherFsm.getLogger(BasicLoggerTest.class).warn("test",
				new NullPointerException("test"));
	}

	@Test
	public void testInfoString() {
		AnotherFsm.getLogger(BasicLoggerTest.class).info("test");
	}

	@Test
	public void testInfoStringThrowable() {
		AnotherFsm.getLogger(BasicLoggerTest.class).info("test",
				new NullPointerException("test"));
	}

	@Test
	public void testDebugString() {
		AnotherFsm.getLogger(BasicLoggerTest.class).debug("test");
	}

	@Test
	public void testDebugStringThrowable() {
		AnotherFsm.getLogger(BasicLoggerTest.class).debug("test",
				new NullPointerException("test"));
	}

	@Test
	public void testTraceString() {
		AnotherFsm.getLogger(BasicLoggerTest.class).trace("test");
	}

	@Test
	public void testTraceStringThrowable() {
		AnotherFsm.getLogger(BasicLoggerTest.class).trace("test",
				new NullPointerException("test"));
	}

	@Test
	public void testIsInfoEnabled() {
		assertTrue(AnotherFsm.getLogger(BasicLoggerTest.class).isInfoEnabled());
	}

	@Test
	public void testIsDebugEnabled() {
		assertTrue(AnotherFsm.getLogger(BasicLoggerTest.class).isDebugEnabled());
	}

	@Test
	public void testIsTraceEnabled() {
		assertTrue(AnotherFsm.getLogger(BasicLoggerTest.class).isTraceEnabled());
	}
}
