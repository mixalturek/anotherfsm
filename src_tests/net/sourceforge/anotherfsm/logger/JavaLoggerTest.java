package net.sourceforge.anotherfsm.logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.sourceforge.anotherfsm.AnotherFsm;

import org.junit.BeforeClass;
import org.junit.Test;

public class JavaLoggerTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		AnotherFsm.setLoggerFactory(new JavaLoggerFactory());
	}

	@Test
	public void testGetName() {
		assertEquals("net.sourceforge.anotherfsm.logger.JavaLoggerTest",
				AnotherFsm.getLogger(JavaLoggerTest.class).getName());
		assertEquals(
				"net.sourceforge.anotherfsm.logger.JavaLoggerTest.instance",
				AnotherFsm.getLogger(JavaLoggerTest.class, "instance")
						.getName());
		assertEquals("name", AnotherFsm.getLogger("name").getName());
	}

	@Test
	public void testFatalString() {
		AnotherFsm.getLogger(JavaLoggerTest.class).fatal("test");
	}

	@Test
	public void testFatalStringThrowable() {
		AnotherFsm.getLogger(JavaLoggerTest.class).fatal("test",
				new NullPointerException("test"));
	}

	@Test
	public void testErrorString() {
		AnotherFsm.getLogger(JavaLoggerTest.class).error("test");
	}

	@Test
	public void testErrorStringThrowable() {
		AnotherFsm.getLogger(JavaLoggerTest.class).error("test",
				new NullPointerException("test"));
	}

	@Test
	public void testWarnString() {
		AnotherFsm.getLogger(JavaLoggerTest.class).warn("test");
	}

	@Test
	public void testWarnStringThrowable() {
		AnotherFsm.getLogger(JavaLoggerTest.class).warn("test",
				new NullPointerException("test"));
	}

	@Test
	public void testInfoString() {
		AnotherFsm.getLogger(JavaLoggerTest.class).info("test");
	}

	@Test
	public void testInfoStringThrowable() {
		AnotherFsm.getLogger(JavaLoggerTest.class).info("test",
				new NullPointerException("test"));
	}

	@Test
	public void testDebugString() {
		AnotherFsm.getLogger(JavaLoggerTest.class).debug("test");
	}

	@Test
	public void testDebugStringThrowable() {
		AnotherFsm.getLogger(JavaLoggerTest.class).debug("test",
				new NullPointerException("test"));
	}

	@Test
	public void testTraceString() {
		AnotherFsm.getLogger(JavaLoggerTest.class).trace("test");
	}

	@Test
	public void testTraceStringThrowable() {
		AnotherFsm.getLogger(JavaLoggerTest.class).trace("test",
				new NullPointerException("test"));
	}

	@Test
	public void testIsInfoEnabled() {
		assertTrue(AnotherFsm.getLogger(JavaLoggerTest.class).isInfoEnabled());
	}

	@Test
	public void testIsDebugEnabled() {
		assertFalse(AnotherFsm.getLogger(JavaLoggerTest.class).isDebugEnabled());
	}

	@Test
	public void testIsTraceEnabled() {
		assertFalse(AnotherFsm.getLogger(JavaLoggerTest.class).isTraceEnabled());
	}
}
