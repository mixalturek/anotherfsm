/*
 *  Copyright 2013 Michal Turek, AnotherFSM
 *
 *      http://anotherfsm.sourceforge.net/
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package net.sourceforge.anotherfsm.qfsm;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;

import net.sourceforge.anotherfsm.AnotherFsm;
import net.sourceforge.anotherfsm.logger.FsmLogger;
import net.sourceforge.anotherfsm.logger.NoLoggerFactory;
import net.sourceforge.anotherfsm.logger.StdStreamLoggerFactory;

/**
 * The code generator main class.
 * 
 * @author Michal Turek
 */
public class CodeGenerator {
	/** The logger. */
	private final FsmLogger logger;

	private static final String FSM_CLASS_SUFFIX = "Fsm";

	/** Program arguments. */
	private final CodeGeneratorParameters parameters;

	/** Configuration of generator. */
	private final Configuration configuration;

	/** Qfsm data file. */
	private final QfsmProject qfsm;

	/** Qfsm state machine. */
	private final QfsmMachine machine;

	/**
	 * Create the object.
	 * 
	 * @param parameters
	 *            program arguments
	 * @throws QfsmException
	 */
	private CodeGenerator(CodeGeneratorParameters parameters)
			throws QfsmException {
		logger = AnotherFsm.getLogger(this.getClass());
		this.parameters = parameters;
		configuration = Configuration.parse(parameters.getConfigFile());
		qfsm = Parser.parse(parameters.getQfsmFile());
		machine = qfsm.getMachine();
	}

	private void generateFsm() throws QfsmException {
		String className = identifier(machine.getName()) + FSM_CLASS_SUFFIX;

		String content = loadTemplate("TemplateFsm.txt");

		content = content.replace("{{FILE_HEADER}}", configuration
				.getFileHeader().trim());
		content = content.replace("{{PACKAGE}}", configuration.getJavaPackage()
				.trim());
		content = content.replace("{{IMPORTS}}", configuration.getFsmImports()
				.trim());
		content = content.replace("{{FSM_DESCRIPTION}}",
				machine.getDescription());
		content = content.replace("{{FSM_AUTHOR}}", machine.getAuthor());
		content = content.replace("{{FSM_NAME}}", className);
		content = content.replace("{{BASE_CLASS}}", configuration
				.getBaseClass().trim());
		content = content.replace("{{START_STATE}}", identifier(machine
				.getStartState().getName()));

		content = content
				.replace("{{DECLARATIONS}}", generateFsmDeclarations());
		content = content.replace("{{CONSTRUCTOR_BODY}}",
				generateFsmCreations());

		File file = new File(configuration.getOutputDirectory()
				+ File.separator + className + ".java");

		writeFile(file, content);
	}

	private String generateFsmDeclarations() throws QfsmException {
		String template = loadTemplate("TemplateStateDeclaration.txt");

		StringBuilder builder = new StringBuilder();

		for (QfsmState state : machine.getStates()) {
			String content = template;

			content = content
					.replace("{{DESCRIPTION}}", state.getDescription());
			content = content.replace("{{JAVA_NAME}}",
					identifier(state.getName()));

			builder.append(content);
		}

		return builder.toString();
	}

	private String generateFsmCreations() throws QfsmException {
		String template = loadTemplate("TemplateStateCreation.txt");

		StringBuilder builder = new StringBuilder();

		for (QfsmState state : machine.getStates()) {
			String content = template;

			content = content.replace("{{JAVA_NAME}}",
					identifier(state.getName()));

			content = content.replace("{{NAME}}", state.getName());

			if (state.isFinalState()) {
				content = content.replace("{{FINAL}}", ", State.Type.FINAL");
			} else {
				content = content.replace("{{FINAL}}", "");
			}

			builder.append(content);
		}

		return builder.toString();
	}

	private void writeFile(File file, String content) throws QfsmException {
		logger.info("Writing file: " + file.getPath());

		if (file.exists()) {
			if (parameters.isForce()) {
				logger.info("File exists, overwriting");
			} else {
				logger.info("File exists, use '-f | --force' parameter to overwrite");
				return;
			}
		}

		try {
			PrintWriter out = new PrintWriter(new FileWriter(file));
			out.println(content);
			out.close();
		} catch (IOException e) {
			throw new QfsmException("Writing file failed", e);
		}

		logger.info("File written: " + file.getPath());
	}

	private void generateProcessor() {

	}

	private String identifier(String name) {
		// TODO:
		return name;
	}

	private String loadTemplate(String name) throws QfsmException {
		InputStream stream = getClass().getResourceAsStream(name);
		try {
			byte[] data = new byte[stream.available()];
			stream.read(data);
			return new String(data);
		} catch (IOException e) {
			throw new QfsmException("Template loading failed: " + name, e);
		}
	}

	/**
	 * The program enter.
	 * 
	 * @param args
	 *            the input arguments
	 * 
	 * @see CodeGeneratorParameters
	 */
	public static void main(String[] args) {
		CodeGeneratorParameters parameters = null;

		try {
			parameters = new CodeGeneratorParameters(args);
		} catch (QfsmException e) {
			CodeGeneratorParameters.showUsage();
			System.err.println("Parameters parsing failed");
			e.printStackTrace();
			System.exit(1);
		}

		if (parameters.isUsage()) {
			CodeGeneratorParameters.showUsage();
			System.exit(0);
		}

		if (parameters.isVerbose()) {
			AnotherFsm.setLoggerFactory(new StdStreamLoggerFactory());
			parameters.dump();
		} else {
			AnotherFsm.setLoggerFactory(new NoLoggerFactory());
		}

		try {
			CodeGenerator generator = new CodeGenerator(parameters);
			generator.generateFsm();
			generator.generateProcessor();
		} catch (QfsmException e) {
			e.printStackTrace();
			System.exit(1);
		}

		System.out.println("Time: "
				+ ManagementFactory.getRuntimeMXBean().getUptime() + " ms");
		System.out.println("Exiting main()");// TODO: remove
	}
}
