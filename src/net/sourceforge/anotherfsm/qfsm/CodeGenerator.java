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
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;

import net.sourceforge.anotherfsm.AnotherFsm;
import net.sourceforge.anotherfsm.logger.BasicLoggerFactory;
import net.sourceforge.anotherfsm.logger.FsmLogger;

/**
 * The code generator main class.
 * 
 * @author Michal Turek
 */
public class CodeGenerator {
	static {
		AnotherFsm.setLoggerFactory(new BasicLoggerFactory());
	}

	/** The logger. */
	private static final FsmLogger logger = AnotherFsm
			.getLogger(CodeGenerator.class);

	/** Suffix of generated state machine class name. */
	private static final String FSM_CLASS_SUFFIX = "Fsm";

	/** Suffix of generated processor class name. */
	private static final String PROCESSOR_CLASS_SUFFIX = "Processor";

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
		this.parameters = parameters;
		configuration = Configuration.parse(parameters.getConfigFile());
		qfsm = Parser.parse(parameters.getQfsmFile());
		machine = qfsm.getMachine();

		// Continual conversions to identifiers have bad performance but it is
		// only one time and acceptable here
		Collections.sort(machine.getStates(), new Comparator<QfsmState>() {
			@Override
			public int compare(QfsmState o1, QfsmState o2) {
				return identifier(o1).compareTo(identifier(o2));
			}
		});

		Collections.sort(machine.getTransitions(),
				new Comparator<QfsmTransition>() {
					@Override
					public int compare(QfsmTransition o1, QfsmTransition o2) {
						return identifier(o1).compareTo(identifier(o2));
					}
				});
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
		content = content.replace("{{CLASS_DESCRIPTION}}",
				machine.getDescription());
		content = content.replace("{{COMMAND_LINE}}",
				parameters.getCommandLine());
		content = content.replace("{{CLASS_AUTHOR}}", machine.getAuthor());
		content = content.replace("{{CLASS_VERSION}}", machine.getVersion());
		content = content.replace("{{CLASS_NAME}}", className);
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

		writeFile(new File(configuration.getOutputDirectory() + File.separator
				+ className + ".java.generated"), content, parameters.isForce());
	}

	private void generateProcessor() throws QfsmException {
		String className = identifier(machine.getName())
				+ PROCESSOR_CLASS_SUFFIX;

		String baseClassName = identifier(machine.getName() + FSM_CLASS_SUFFIX);

		String content = loadTemplate("TemplateProcessor.txt");

		content = content.replace("{{FILE_HEADER}}", configuration
				.getFileHeader().trim());
		content = content.replace("{{PACKAGE}}", configuration.getJavaPackage()
				.trim());
		content = content.replace("{{IMPORTS}}", configuration
				.getProcessorImports().trim());
		content = content.replace("{{CLASS_DESCRIPTION}}",
				machine.getDescription());
		content = content.replace("{{COMMAND_LINE}}",
				parameters.getCommandLine());
		content = content.replace("{{CLASS_AUTHOR}}", machine.getAuthor());
		content = content.replace("{{CLASS_VERSION}}", machine.getVersion());
		content = content.replace("{{CLASS_NAME}}", className);
		content = content.replace("{{BASE_CLASS}}", baseClassName);

		content = content
				.replace(
						"{{TYPE_PREPROCESSOR}}",
						configuration.isTypePreprocessor() ? loadTemplate("TemplateTypePreprocessor.txt")
								: "");

		content = content
				.replace(
						"{{EQUALS_PREPROCESSOR}}",
						configuration.isEqualsPreprocessor() ? loadTemplate("TemplateEqualsPreprocessor.txt")
								: "");

		content = content.replace("{{GLOBAL_STATE_LISTENERS}}", configuration
				.isGlobalStateListener() ? genGlobalStateListeners() : "");

		content = content
				.replace(
						"{{GLOBAL_TRANSITION_LISTENERS}}",
						configuration.isGlobalTransitionListener() ? genGlobalTransitionListener()
								: "");

		content = content.replace("{{STATE_LISTENERS}}",
				configuration.isStateListener() ? genStateListeners() : "");

		content = content.replace("{{TRANSITION_LISTENERS}}", configuration
				.isTransitionListener() ? genTransitionListeners() : "");

		writeFile(new File(configuration.getOutputDirectory() + File.separator
				+ className + ".java.generated"), content, parameters.isForce());
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
	 * Generate the state listeners.
	 * 
	 * @return the string representation of listeners
	 * @throws QfsmException
	 *             if something fails
	 */
	private String genStateListeners() throws QfsmException {
		String template = loadTemplate("TemplateStateListener.txt");

		StringBuilder builder = new StringBuilder();

		for (QfsmState state : machine.getStates()) {
			String content = template;

			content = content.replace("{{JAVA_NAME}}", identifier(state));

			builder.append(content);
		}

		return builder.toString();
	}

	/**
	 * Generate the global state listener.
	 * 
	 * @return the string representation of listener
	 * @throws QfsmException
	 *             if something fails
	 */
	private String genGlobalStateListeners() throws QfsmException {
		String template = loadTemplate("TemplateStateListener.txt");
		return template.replace("{{JAVA_NAME}}.", "");
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

			content = content.replace(
					"{{DESCRIPTION}}",
					"Transition from {@code "
							+ transition.getSourceState().getName()
							+ "} to {@code "
							+ transition.getDestinationState().getName()
							+ "} on {@code "
							+ transition.getInputEvent()
							+ "} event."
							+ (configuration
									.isTransitionDescriptionContainsCode() ? ""
									: "\n\n" + transition.getDescription()));

			content = content.replace("{{JAVA_NAME}}", identifier(transition));

			builder.append(content);
		}

		return builder.toString();
	}

	/**
	 * Generate initializations of transitions.
	 * 
	 * @return the string representation of initializations
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
	 * Generate the transition listeners.
	 * 
	 * @return the string representation of listeners
	 * @throws QfsmException
	 *             if something fails
	 */
	private String genTransitionListeners() throws QfsmException {
		String template = loadTemplate("TemplateTransitionListener.txt");

		StringBuilder builder = new StringBuilder();

		for (QfsmTransition transition : machine.getTransitions()) {
			String content = template;

			content = content.replace("{{JAVA_NAME}}", identifier(transition));

			if (configuration.isTransitionDescriptionContainsCode()) {
				content = content.replace("{{CODE}}",
						"// The following code is generated, don't edit it directly\n"
								+ transition.getDescription());
			} else {
				content = content.replace("{{CODE}}",
						"// TODO Auto-generated method stub");
			}

			builder.append(content);
		}

		return builder.toString();
	}

	/**
	 * Generate the global transition listener.
	 * 
	 * @return the string representation of listener
	 * @throws QfsmException
	 *             if something fails
	 */
	private String genGlobalTransitionListener() throws QfsmException {
		String content = loadTemplate("TemplateTransitionListener.txt");

		content = content.replace("{{JAVA_NAME}}.", "");
		content = content.replace("{{CODE}}",
				"// TODO Auto-generated method stub");

		return content;
	}

	/**
	 * Write a text file.
	 * 
	 * @param file
	 *            the destination file
	 * @param content
	 *            the text content of the file
	 * @param force
	 *            rewrite existing file
	 * @throws QfsmException
	 *             if something fails
	 */
	private static void writeFile(File file, String content, boolean force)
			throws QfsmException {
		if (file.exists() && !force) {
			logger.warn("File exists, use '-f | --force' parameter to overwrite: "
					+ file.getPath());
			return;
		}

		try (PrintWriter out = new PrintWriter(file, "UTF-8")) {
			out.print(content);
		} catch (IOException e) {
			throw new QfsmException("Writing file failed", e);
		}

		logger.info("File written: " + file.getAbsolutePath());
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

		// Ignore empty identifier, the generated file will show it
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
	private static String loadTemplate(String name) throws QfsmException {
		try (InputStream stream = CodeGenerator.class.getResourceAsStream(name)) {
			int size = stream.available();
			byte[] data = new byte[size];
			int actualSize = stream.read(data);

			if (actualSize != size) {
				throw new QfsmException(
						"Unexpected number of bytes loaded from template: expected "
								+ size + ", actual " + actualSize);
			}

			return new String(data, "UTF-8");
		} catch (IOException e) {
			throw new QfsmException("Template loading failed: " + name, e);
		}
	}

	private static String formatPrompt(String[] args) {
		StringBuilder builder = new StringBuilder();

		builder.append("java -classpath anotherfsm-");
		builder.append(AnotherFsm.VERSION);
		builder.append(".jar ");
		builder.append(CodeGenerator.class.getName());

		for (String arg : args) {
			builder.append(" ");
			builder.append(arg);
		}

		return builder.toString();
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
			parameters = new CodeGeneratorParameters(args, formatPrompt(args));
		} catch (QfsmException e) {
			CodeGeneratorParameters.showUsage();
			logger.error("Parameters parsing failed", e);
			System.exit(1);
		}

		if (parameters.isUsage()) {
			CodeGeneratorParameters.showUsage();
			System.exit(0);
		}

		try {
			if (parameters.getConfigTemplate() != null) {
				String template = loadTemplate("TemplateConfiguration.txt");
				writeFile(new File(parameters.getConfigTemplate()), template,
						parameters.isForce());
				System.exit(0);
			}

			CodeGenerator generator = new CodeGenerator(parameters);
			generator.genFsmFile();
			generator.generateProcessor();
		} catch (QfsmException e) {
			logger.error("Unexpected error", e);
			System.exit(1);
		}
	}
}
