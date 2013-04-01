package net.sourceforge.anotherfsm.qfsm;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class CodeGeneratorTest {
	@Test
	public void testMainNoArg() {
		CodeGenerator.setSystemExitCallAllowed(false);

		String[] args = {};
		CodeGenerator.main(args);
	}

	@Test
	public void testMainHelp() {
		CodeGenerator.setSystemExitCallAllowed(false);

		String[] args = { "--help" };
		CodeGenerator.main(args);
	}

	@Test
	public void testMainTemplate() {
		CodeGenerator.setSystemExitCallAllowed(false);

		File outputFile = null;

		try {
			outputFile = File.createTempFile("temp", ".generated");
		} catch (IOException e) {
			fail("Should not be executed: " + e);
		}

		String[] args = { "--force", "--template", outputFile.getAbsolutePath() };
		CodeGenerator.main(args);

		assertTrue(outputFile.exists());
		assertTrue(outputFile.isFile());
		assertTrue(outputFile.canRead());
		assertThat(outputFile.length(), greaterThan(2000L));
		assertTrue(outputFile.delete());
	}

	@Test
	public void testMainSearchStringSample() {
		generateCode("SearchString",
				"src_examples/net/sourceforge/anotherfsm/examples/qfsm/", ".",
				".generated", 9000, 11000);
	}

	@Test
	public void testMainTimeoutsSample() {
		generateCode("TimeoutConnection",
				"src_examples/net/sourceforge/anotherfsm/examples/timeouts/",
				".", "", 4000, 3000);
	}

	@Test
	public void testMainTimeoutConnectionJUnit() {
		generateCode("TimeoutConnectionJUnit",
				"src_tests/net/sourceforge/anotherfsm/qfsm/", ".", "", 5000,
				3000);
	}

	/**
	 * Helper method to generate Java code.
	 * 
	 * @param name
	 *            the name of state machine
	 * @param inputDirectory
	 *            the input directory
	 * @param outputDirectory
	 *            the output directory
	 * @param fileSuffix
	 *            the suffix of output files
	 * @param expectedSizeFsm
	 *            the expected size of state machine file
	 * @param expectedSizeProcessor
	 *            the expected size of processor file
	 */
	private void generateCode(String name, String inputDirectory,
			String outputDirectory, String fileSuffix, long expectedSizeFsm,
			long expectedSizeProcessor) {
		CodeGenerator.setSystemExitCallAllowed(false);

		File fsmFileGen = new File(outputDirectory + "/" + name + "Fsm.java"
				+ fileSuffix);
		File procFileGen = new File(outputDirectory + "/" + name
				+ "Processor.java" + fileSuffix);
		assertFalse(fsmFileGen.exists());
		assertFalse(procFileGen.exists());

		File xmlFile = new File(inputDirectory + name + ".xml");
		File qfsmFile = new File(inputDirectory + name + ".fsm");
		assertTrue(xmlFile.canRead());
		assertTrue(qfsmFile.canRead());

		String[] args = { "--force", "--config-file",
				xmlFile.getAbsolutePath(), "--qfsm-file",
				qfsmFile.getAbsolutePath() };

		CodeGenerator.main(args);

		assertTrue(fsmFileGen.exists());
		assertTrue(fsmFileGen.isFile());
		assertTrue(fsmFileGen.canRead());
		assertThat(fsmFileGen.length(), greaterThan(expectedSizeFsm));
		assertTrue(fsmFileGen.delete());

		assertTrue(procFileGen.exists());
		assertTrue(procFileGen.isFile());
		assertTrue(procFileGen.canRead());
		assertThat(procFileGen.length(), greaterThan(expectedSizeProcessor));
		assertTrue(procFileGen.delete());
	}
}
