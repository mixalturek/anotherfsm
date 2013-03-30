package net.sourceforge.anotherfsm.qfsm;

import static net.sourceforge.anotherfsm.UnitTestHelpers.assertDoubleEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class XmlUtilsTest {
	@Test
	public void testXmlUtils() {
		new XmlUtils();
	}

	@Test
	public void testEnsureNotNull() {
		try {
			XmlUtils.ensureNotNull(new Object(), "object");
		} catch (QfsmException e) {
			fail("Should not be executed: " + e);
		}

		try {
			XmlUtils.ensureNotNull(null, "object");
			fail("Should not be executed");
		} catch (QfsmException e) {
			assertEquals("Object must not be null: object", e.getMessage());
		}
	}

	@Test
	public void testEnsureNotEmpty() {
		try {
			XmlUtils.ensureNotEmpty("test", "object");
		} catch (QfsmException e) {
			fail("Should not be executed: " + e);
		}

		try {
			XmlUtils.ensureNotEmpty("", "object");
			fail("Should not be executed");
		} catch (QfsmException e) {
			assertEquals("String must not be empty: object", e.getMessage());
		}
	}

	@Test
	public void testToInt() {
		try {
			assertEquals(-1, XmlUtils.toInt("-1"));
			assertEquals(0, XmlUtils.toInt("0"));
			assertEquals(1, XmlUtils.toInt("1"));
			assertEquals(1, XmlUtils.toInt("+1"));
		} catch (QfsmException e) {
			fail("Should not be executed: " + e);
		}

		try {
			XmlUtils.toInt("test");
			fail("Should not be executed");
		} catch (QfsmException e) {
			assertEquals("Integer parsing failed: test", e.getMessage());
		}
	}

	@Test
	public void testToDouble() {
		try {
			assertDoubleEquals(-1.42, XmlUtils.toDouble("-1.42"));
			assertDoubleEquals(0.42, XmlUtils.toDouble("0.42"));
			assertDoubleEquals(1.42, XmlUtils.toDouble("1.42"));
			assertDoubleEquals(1.42, XmlUtils.toDouble("+1.42"));
		} catch (QfsmException e) {
			fail("Should not be executed: " + e);
		}

		try {
			XmlUtils.toDouble("test");
			fail("Should not be executed");
		} catch (QfsmException e) {
			assertEquals("Double parsing failed: test", e.getMessage());
		}
	}

	@Test
	public void testToBoolean() {
		try {
			assertFalse(XmlUtils.toBoolean("0"));
			assertFalse(XmlUtils.toBoolean("false"));
			assertFalse(XmlUtils.toBoolean("no"));
			assertTrue(XmlUtils.toBoolean("1"));
			assertTrue(XmlUtils.toBoolean("true"));
			assertTrue(XmlUtils.toBoolean("yes"));
		} catch (QfsmException e) {
			fail("Should not be executed: " + e);
		}

		try {
			XmlUtils.toBoolean("test");
			fail("Should not be executed");
		} catch (QfsmException e) {
			assertEquals("Boolean parsing failed: test", e.getMessage());
		}
	}
}
