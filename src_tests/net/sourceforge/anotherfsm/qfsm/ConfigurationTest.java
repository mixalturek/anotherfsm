package net.sourceforge.anotherfsm.qfsm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class ConfigurationTest {
	/** Directory where data files are stored. */
	private static final String DATA_DIR = "src_tests/net/sourceforge/anotherfsm/qfsm/";

	@Test
	public void testParse() {
		try {
			Configuration configuration = Configuration.parse(DATA_DIR
					+ "configuration.xml");

			assertEquals("version", configuration.getVersion());
			assertEquals("output directory", configuration.getOutputDirectory());
			assertEquals("base class", configuration.getBaseClass());
			assertEquals("java package", configuration.getJavaPackage());
			assertFalse(configuration.isTransitionDescriptionContainsCode());
			assertEquals("fsm suffix", configuration.getFsmFileSuffix());
			assertEquals("state machine imports", configuration.getFsmImports());
			assertTrue(configuration.isTypePreprocessor());
			assertTrue(configuration.isEqualsPreprocessor());
			assertTrue(configuration.isGlobalStateListener());
			assertTrue(configuration.isGlobalTransitionListener());
			assertTrue(configuration.isStateListener());
			assertTrue(configuration.isTransitionListener());
			assertEquals("processor suffix",
					configuration.getProcessorFileSuffix());
			assertEquals("processor imports",
					configuration.getProcessorImports());
			assertEquals("file header", configuration.getFileHeader());
		} catch (QfsmException e) {
			fail("Should not be executed: " + e);
		}
	}
}
