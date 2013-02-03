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

	/** Suffix of generated state machine class name. */
	private static final String FSM_CLASS_SUFFIX = "Fsm";

	/** Program parameters. */
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

	/**
	 * Generate the state machine file.
	 * 
	 * @throws QfsmException
	 *             if something fails
	 */
	private void genFsmFile() throws QfsmException {
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
		content = content.replace("{{START_STATE}}",
				identifier(machine.getStartState()));

		content = content.replace("{{DECLARATIONS_STATES}}",
				genStateDeclarations());
		content = content.replace("{{DECLARATIONS_TRANSITIONS}}",
				genTransitionDeclarations());

		content = content.replace("{{CONSTRUCTOR_BODY_STATES}}",
				genStateInitializations());
		content = content.replace("{{CONSTRUCTOR_BODY_TRANSITIONS}}",
				genTransitionInitializations());

		File file = new File(configuration.getOutputDirectory()
				+ File.separator + className + ".java");

		writeFile(file, content);
	}

	/**
	 * Generate declarations of states.
	 * 
	 * @return the string representation of declarations
	 * @throws QfsmException
	 *             if something fails
	 */
	private String genStateDeclarations() throws QfsmException {
		String template = loadTemplate("TemplateStateDeclaration.txt");

		StringBuilder builder = new StringBuilder();

		for (QfsmState state : machine.getStates()) {
			String content = template;

			content = content
					.replace("{{DESCRIPTION}}", state.getDescription());
			content = content.replace("{{JAVA_NAME}}", identifier(state));

			builder.append(content);
		}

		return builder.toString();
	}

	/**
	 * Generate initializations of states.
	 * 
	 * @return the string representation of initializations
	 * @throws QfsmException
	 *             if something fails
	 */
	private String genStateInitializations() throws QfsmException {
		String template = loadTemplate("TemplateStateInitialization.txt");

		StringBuilder builder = new StringBuilder();

		for (QfsmState state : machine.getStates()) {
			String content = template;

			content = content.replace("{{JAVA_NAME}}", identifier(state));
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

	/**
	 * Generate declarations of transitions.
	 * 
	 * @return the string representations of declarations
	 * @throws QfsmException
	 *             if something fails
	 */
	private String genTransitionDeclarations() throws QfsmException {
		String template = loadTemplate("TemplateTransitionDeclaration.txt");

		StringBuilder builder = new StringBuilder();

		for (QfsmTransition transition : machine.getTransitions()) {
			String content = template;

			content = content.replace("{{DESCRIPTION}}",
					transition.getDescription());
			content = content.replace("{{JAVA_NAME}}", identifier(transition));

			builder.append(content);
		}

		return builder.toString();
	}

	/**
	 * Generate initializations of transitions.
	 * 
	 * @return the string representation fo initializations
	 * @throws QfsmException
	 *             if something fails
	 */
	private String genTransitionInitializations() throws QfsmException {
		String template = loadTemplate("TemplateTransitionInitialization.txt");

		StringBuilder builder = new StringBuilder();

		for (QfsmTransition transition : machine.getTransitions()) {
			String content = template;

			content = content.replace("{{JAVA_NAME}}", identifier(transition));
			content = content.replace("{{SOURCE_STATE}}",
					identifier(transition.getSourceState()));
			content = content.replace("{{EVENT}}", transition.getInputEvent());
			content = content.replace("{{DESTINATION_STATE}}",
					identifier(transition.getDestinationState()));

			builder.append(content);
		}

		return builder.toString();
	}

	/**
	 * Write a text file.
	 * 
	 * @param file
	 *            the destination file
	 * @param content
	 *            the text content of the file
	 * @throws QfsmException
	 *             if something fails
	 */
	private void writeFile(File file, String content) throws QfsmException {
		if (file.exists() && !parameters.isForce()) {
			logger.warn("File exists, use '-f | --force' parameter to overwrite: "
					+ file.getPath());
			return;
		}

		try {
			PrintWriter out = new PrintWriter(new FileWriter(file));
			out.println(content);
			out.close();
		} catch (IOException e) {
			throw new QfsmException("Writing file failed", e);
		}
	}

	private void generateProcessor() {

	}

	/**
	 * Generate a Java identifier from a string.
	 * 
	 * @param str
	 *            the input string
	 * @return the generated Java identifier
	 */
	private String identifier(String str) {
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < str.length(); ++i) {
			char c = str.charAt(i);
			if (c == '.')
				builder.append("_");
			else if (Character.isJavaIdentifierPart(c))
				builder.append(c);
			// Else ignore it
		}

		// TODO: check empty string
		return builder.toString();
	}

	/**
	 * Generate a Java identifier from a state.
	 * 
	 * @param state
	 *            the state
	 * @return the Java identifier
	 */
	private String identifier(QfsmState state) {
		return identifier(state.getName());
	}

	/**
	 * Generate a Java identifier from a transition.
	 * 
	 * @param transition
	 *            the transition
	 * @return the Java identifier
	 */
	private String identifier(QfsmTransition transition) {
		return identifier(transition.getSourceState()) + "_"
				+ identifier(transition.getInputEvent()) + "_"
				+ identifier(transition.getDestinationState());
	}

	/**
	 * Load a template for the code generation.
	 * 
	 * @param name
	 *            the path in file system or in jar package
	 * @return the text content of the template file
	 * @throws QfsmException
	 *             if something fails
	 */
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
			generator.genFsmFile();
			generator.generateProcessor();
		} catch (QfsmException e) {
			e.printStackTrace();
			System.exit(1);
		}

		System.out.println("Exiting main()");// TODO: remove
	}
}
