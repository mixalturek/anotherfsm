package net.sourceforge.anotherfsm.qfsm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class CodeGeneratorParametersTest {
	@Test
	public void testDefault() {
		String[] args = { "-m", "unused", "-c", "unused" };

		try {
			CodeGeneratorParameters params = new CodeGeneratorParameters(args);
			assertFalse(params.isForce());
			assertFalse(params.isUsage());
		} catch (QfsmException e) {
			fail("Should not be executed: " + e);
		}
	}

	@Test
	public void testIsUsageShort() {
		String[] args = { "-m", "unused", "-c", "unused", "-h" };

		try {
			CodeGeneratorParameters params = new CodeGeneratorParameters(args);
			assertTrue(params.isUsage());
		} catch (QfsmException e) {
			fail("Should not be executed: " + e);
		}
	}

	@Test
	public void testIsUsageLong() {
		String[] args = { "-m", "unused", "-c", "unused", "--help" };

		try {
			CodeGeneratorParameters params = new CodeGeneratorParameters(args);
			assertTrue(params.isUsage());
		} catch (QfsmException e) {
			fail("Should not be executed: " + e);
		}
	}

	@Test
	public void testIsForceShort() {
		String[] args = { "-m", "unused", "-c", "unused", "-f" };

		try {
			CodeGeneratorParameters params = new CodeGeneratorParameters(args);
			assertTrue(params.isForce());
		} catch (QfsmException e) {
			fail("Should not be executed: " + e);
		}
	}

	@Test
	public void testIsForceLong() {
		String[] args = { "-m", "unused", "-c", "unused", "--force" };

		try {
			CodeGeneratorParameters params = new CodeGeneratorParameters(args);
			assertTrue(params.isForce());
		} catch (QfsmException e) {
			fail("Should not be executed: " + e);
		}
	}

	@Test
	public void testGetConfigFileShort() {
		String[] args = { "-m", "unused", "-c", "test" };

		try {
			CodeGeneratorParameters params = new CodeGeneratorParameters(args);
			assertEquals("test", params.getConfigFile());
		} catch (QfsmException e) {
			fail("Should not be executed: " + e);
		}
	}

	@Test
	public void testGetConfigFileLong() {
		String[] args = { "-m", "unused", "--config-file", "test" };

		try {
			CodeGeneratorParameters params = new CodeGeneratorParameters(args);
			assertEquals("test", params.getConfigFile());
		} catch (QfsmException e) {
			fail("Should not be executed: " + e);
		}
	}

	@Test
	public void testGetConfigFileShortPreprocess() {
		String[] args = { "-m", "unused", "-c=test" };

		try {
			CodeGeneratorParameters params = new CodeGeneratorParameters(args);
			assertEquals("test", params.getConfigFile());
		} catch (QfsmException e) {
			fail("Should not be executed: " + e);
		}
	}

	@Test
	public void testGetConfigFileLongPreprocess() {
		String[] args = { "-m", "unused", "--config-file=test" };

		try {
			CodeGeneratorParameters params = new CodeGeneratorParameters(args);
			assertEquals("test", params.getConfigFile());
		} catch (QfsmException e) {
			fail("Should not be executed: " + e);
		}
	}

	@Test
	public void testGetConfigFileShortBroken() {
		String[] args = { "-m", "unused", "-c" };

		try {
			new CodeGeneratorParameters(args);
			fail("Should not be executed");
		} catch (QfsmException e) {
			assertEquals("Missing second argument of -c | --config-file",
					e.getMessage());
		}
	}

	@Test
	public void testGetConfigFileLongBroken() {
		String[] args = { "-m", "unused", "--config-file" };

		try {
			new CodeGeneratorParameters(args);
			fail("Should not be executed");
		} catch (QfsmException e) {
			assertEquals("Missing second argument of -c | --config-file",
					e.getMessage());
		}
	}

	@Test
	public void testGetConfigFileUndefined() {
		String[] args = { "-m", "unused" };

		try {
			new CodeGeneratorParameters(args);
			fail("Should not be executed");
		} catch (QfsmException e) {
			assertEquals("Missing mandatory parameter: -c | --config-file",
					e.getMessage());
		}
	}

	@Test
	public void testGetQfsmFileShort() {
		String[] args = { "-c", "unused", "-m", "test" };

		try {
			CodeGeneratorParameters params = new CodeGeneratorParameters(args);
			assertEquals("test", params.getQfsmFile());
		} catch (QfsmException e) {
			fail("Should not be executed: " + e);
		}
	}

	@Test
	public void testGetQfsmFileLong() {
		String[] args = { "-c", "unused", "--qfsm-file", "test" };

		try {
			CodeGeneratorParameters params = new CodeGeneratorParameters(args);
			assertEquals("test", params.getQfsmFile());
		} catch (QfsmException e) {
			fail("Should not be executed: " + e);
		}
	}

	@Test
	public void testGetQfsmFileShortPreprocess() {
		String[] args = { "-c", "unused", "-m=test" };

		try {
			CodeGeneratorParameters params = new CodeGeneratorParameters(args);
			assertEquals("test", params.getQfsmFile());
		} catch (QfsmException e) {
			fail("Should not be executed: " + e);
		}
	}

	@Test
	public void testGetQfsmFileLongPreprocess() {
		String[] args = { "-c", "unused", "--qfsm-file=test" };

		try {
			CodeGeneratorParameters params = new CodeGeneratorParameters(args);
			assertEquals("test", params.getQfsmFile());
		} catch (QfsmException e) {
			fail("Should not be executed: " + e);
		}
	}

	@Test
	public void testGetQfsmFileShortBroken() {
		String[] args = { "-c", "unused", "-m" };

		try {
			new CodeGeneratorParameters(args);
			fail("Should not be executed");
		} catch (QfsmException e) {
			assertEquals("Missing second argument of -m | --qfsm-file",
					e.getMessage());
		}
	}

	@Test
	public void testGetQfsmFileLongBroken() {
		String[] args = { "-c", "unused", "--qfsm-file" };

		try {
			new CodeGeneratorParameters(args);
			fail("Should not be executed");
		} catch (QfsmException e) {
			assertEquals("Missing second argument of -m | --qfsm-file",
					e.getMessage());
		}
	}

	@Test
	public void testGetQfsmFileUndefined() {
		String[] args = { "-c", "unused" };

		try {
			new CodeGeneratorParameters(args);
			fail("Should not be executed");
		} catch (QfsmException e) {
			assertEquals("Missing mandatory parameter: -m | --qfsm-file",
					e.getMessage());
		}
	}

	@Test
	public void testGetConfigTemplateShort() {
		String[] args = { "-t", "test" };

		try {
			CodeGeneratorParameters params = new CodeGeneratorParameters(args);
			assertEquals("test", params.getConfigTemplate());
		} catch (QfsmException e) {
			fail("Should not be executed: " + e);
		}
	}

	@Test
	public void testGetConfigTemplateLong() {
		String[] args = { "--template", "test" };

		try {
			CodeGeneratorParameters params = new CodeGeneratorParameters(args);
			assertEquals("test", params.getConfigTemplate());
		} catch (QfsmException e) {
			fail("Should not be executed: " + e);
		}
	}

	@Test
	public void testGetConfigTemplateShortPreprocess() {
		String[] args = { "-t=test" };

		try {
			CodeGeneratorParameters params = new CodeGeneratorParameters(args);
			assertEquals("test", params.getConfigTemplate());
		} catch (QfsmException e) {
			fail("Should not be executed: " + e);
		}
	}

	@Test
	public void testGetConfigTemplateLongPreprocess() {
		String[] args = { "--template=test" };

		try {
			CodeGeneratorParameters params = new CodeGeneratorParameters(args);
			assertEquals("test", params.getConfigTemplate());
		} catch (QfsmException e) {
			fail("Should not be executed: " + e);
		}
	}

	@Test
	public void testGetConfigTemplateShortBroken() {
		String[] args = { "-t" };

		try {
			new CodeGeneratorParameters(args);
			fail("Should not be executed");
		} catch (QfsmException e) {
			assertEquals("Missing second argument of -t | --template",
					e.getMessage());
		}
	}

	@Test
	public void testGetConfigTemplateLongBroken() {
		String[] args = { "--template" };

		try {
			new CodeGeneratorParameters(args);
			fail("Should not be executed");
		} catch (QfsmException e) {
			assertEquals("Missing second argument of -t | --template",
					e.getMessage());
		}
	}

	@Test
	public void testUnexpectedParameter() {
		String[] args = { "-c", "unused", "-m", "unused", "unexpected 1",
				"-u 2", "--unexpected 3" };

		try {
			new CodeGeneratorParameters(args);
			fail("Should not be executed");
		} catch (QfsmException e) {
			assertEquals(
					"Unexpected parameters: [unexpected 1, -u 2, --unexpected 3]",
					e.getMessage());
		}
	}
}
