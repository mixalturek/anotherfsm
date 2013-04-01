package net.sourceforge.anotherfsm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import net.sourceforge.anotherfsm.logger.NoLoggerJUnitFactory;

import org.junit.BeforeClass;
import org.junit.Test;

public class HelpersTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		AnotherFsm.setLoggerFactory(new NoLoggerJUnitFactory());
	}

	@Test
	public void testEnsureNotNull() {
		try {
			Helpers.ensureNotNull(new Object(), "object");
		} catch (Throwable e) {
			fail("Should not be executed: " + e);
		}

		try {
			Helpers.ensureNotNull(null, "object");
		} catch (NullPointerException e) {
			assertEquals("Object must not be null: object", e.getMessage());
		}
	}

	@Test
	public void testLogExceptionInClientCallback() {
		Helpers.logExceptionInClientCallback(
				AnotherFsm.getLogger(HelpersTest.class), "Serious error",
				new NullPointerException());
	}

	@Test
	public void testLogThreadUnexpectedlyFinished() {
		Helpers.logThreadUnexpectedlyFinished(
				AnotherFsm.getLogger(HelpersTest.class), "Serious error",
				new NullPointerException());
	}

}
