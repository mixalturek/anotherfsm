package net.sourceforge.anotherfsm.qfsm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;

import net.sourceforge.anotherfsm.AnotherFsm;
import net.sourceforge.anotherfsm.logger.StdStreamLoggerFactory;

import org.junit.BeforeClass;
import org.junit.Test;

public class QfsmParserTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		AnotherFsm.setLoggerFactory(new StdStreamLoggerFactory());
	}

	@Test
	public void testParse() {
		try {
			// System.out.println("Dir: " + new File(".").getCanonicalPath());

			QfsmProject project = QfsmParser.parse(new File(
					"src_tests/net/sourceforge/anotherfsm/qfsm/test.fsm"));

			assertEquals("0.52", project.getVersion());
			assertEquals("Qfsm", project.getAuthor());
		} catch (QfsmException e) {
			e.printStackTrace();
			fail("Should not be executed" + e);
		}

		// TODO:
		fail("Not yet implemented");
	}
}
