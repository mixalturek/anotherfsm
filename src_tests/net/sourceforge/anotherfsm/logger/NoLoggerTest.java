package net.sourceforge.anotherfsm.logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import net.sourceforge.anotherfsm.AnotherFsm;

import org.junit.BeforeClass;
import org.junit.Test;

public class NoLoggerTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		AnotherFsm.setLoggerFactory(new NoLoggerFactory());
	}

	@Test
	public void testGetName() {
		assertEquals("NoLogger", AnotherFsm.getLogger(NoLoggerTest.class)
				.getName());
		assertEquals("NoLogger",
				AnotherFsm.getLogger(NoLoggerTest.class, "instance").getName());
		assertEquals("NoLogger", AnotherFsm.getLogger("name").getName());
	}

	@Test
	public void testFatalString() {
		AnotherFsm.getLogger(NoLoggerTest.class).fatal("test");
	}

	@Test
	public void testFatalStringThrowable() {
		AnotherFsm.getLogger(NoLoggerTest.class).fatal("test",
				new NullPointerException("test"));
	}

	@Test
	public void testErrorString() {
		AnotherFsm.getLogger(NoLoggerTest.class).error("test");
	}

	@Test
	public void testErrorStringThrowable() {
		AnotherFsm.getLogger(NoLoggerTest.class).error("test",
				new NullPointerException("test"));
	}

	@Test
	public void testWarnString() {
		AnotherFsm.getLogger(NoLoggerTest.class).warn("test");
	}

	@Test
	public void testWarnStringThrowable() {
		AnotherFsm.getLogger(NoLoggerTest.class).warn("test",
				new NullPointerException("test"));
	}

	@Test
	public void testInfoString() {
		AnotherFsm.getLogger(NoLoggerTest.class).info("test");
	}

	@Test
	public void testInfoStringThrowable() {
		AnotherFsm.getLogger(NoLoggerTest.class).info("test",
				new NullPointerException("test"));
	}

	@Test
	public void testDebugString() {
		AnotherFsm.getLogger(NoLoggerTest.class).debug("test");
	}

	@Test
	public void testDebugStringThrowable() {
		AnotherFsm.getLogger(NoLoggerTest.class).debug("test",
				new NullPointerException("test"));
	}

	@Test
	public void testTraceString() {
		AnotherFsm.getLogger(NoLoggerTest.class).trace("test");
	}

	@Test
	public void testTraceStringThrowable() {
		AnotherFsm.getLogger(NoLoggerTest.class).trace("test",
				new NullPointerException("test"));
	}

	@Test
	public void testIsInfoEnabled() {
		assertFalse(AnotherFsm.getLogger(NoLoggerTest.class).isInfoEnabled());
	}

	@Test
	public void testIsDebugEnabled() {
		assertFalse(AnotherFsm.getLogger(NoLoggerTest.class).isDebugEnabled());
	}

	@Test
	public void testIsTraceEnabled() {
		assertFalse(AnotherFsm.getLogger(NoLoggerTest.class).isTraceEnabled());
	}
}
