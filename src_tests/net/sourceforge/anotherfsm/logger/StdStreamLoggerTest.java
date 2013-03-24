package net.sourceforge.anotherfsm.logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import net.sourceforge.anotherfsm.AnotherFsm;

import org.junit.BeforeClass;
import org.junit.Test;

public class StdStreamLoggerTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		AnotherFsm.setLoggerFactory(new StdStreamLoggerFactory());
	}

	@Test
	public void testGetName() {
		assertEquals("StdStreamLoggerTest",
				AnotherFsm.getLogger(StdStreamLoggerTest.class).getName());
		assertEquals("StdStreamLoggerTest.instance",
				AnotherFsm.getLogger(StdStreamLoggerTest.class, "instance")
						.getName());
		assertEquals("name", AnotherFsm.getLogger("name").getName());
	}

	@Test
	public void testFatalString() {
		AnotherFsm.getLogger(StdStreamLoggerTest.class).fatal("test");
	}

	@Test
	public void testFatalStringThrowable() {
		AnotherFsm.getLogger(StdStreamLoggerTest.class).fatal("test",
				new NullPointerException("test"));
	}

	@Test
	public void testErrorString() {
		AnotherFsm.getLogger(StdStreamLoggerTest.class).error("test");
	}

	@Test
	public void testErrorStringThrowable() {
		AnotherFsm.getLogger(StdStreamLoggerTest.class).error("test",
				new NullPointerException("test"));
	}

	@Test
	public void testWarnString() {
		AnotherFsm.getLogger(StdStreamLoggerTest.class).warn("test");
	}

	@Test
	public void testWarnStringThrowable() {
		AnotherFsm.getLogger(StdStreamLoggerTest.class).warn("test",
				new NullPointerException("test"));
	}

	@Test
	public void testInfoString() {
		AnotherFsm.getLogger(StdStreamLoggerTest.class).info("test");
	}

	@Test
	public void testInfoStringThrowable() {
		AnotherFsm.getLogger(StdStreamLoggerTest.class).info("test",
				new NullPointerException("test"));
	}

	@Test
	public void testDebugString() {
		AnotherFsm.getLogger(StdStreamLoggerTest.class).debug("test");
	}

	@Test
	public void testDebugStringThrowable() {
		AnotherFsm.getLogger(StdStreamLoggerTest.class).debug("test",
				new NullPointerException("test"));
	}

	@Test
	public void testTraceString() {
		AnotherFsm.getLogger(StdStreamLoggerTest.class).trace("test");
	}

	@Test
	public void testTraceStringThrowable() {
		AnotherFsm.getLogger(StdStreamLoggerTest.class).trace("test",
				new NullPointerException("test"));
	}

	@Test
	public void testIsInfoEnabled() {
		assertTrue(AnotherFsm.getLogger(StdStreamLoggerTest.class)
				.isInfoEnabled());
	}

	@Test
	public void testIsDebugEnabled() {
		assertTrue(AnotherFsm.getLogger(StdStreamLoggerTest.class)
				.isDebugEnabled());
	}

	@Test
	public void testIsTraceEnabled() {
		assertTrue(AnotherFsm.getLogger(StdStreamLoggerTest.class)
				.isTraceEnabled());
	}
}
