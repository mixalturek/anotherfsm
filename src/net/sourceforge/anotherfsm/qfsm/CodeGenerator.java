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

	/** Program arguments. */
	private final CodeGeneratorParameters parameters;

	/** Configuration of generator. */
	private final Configuration configuration;

	/** Qfsm data file. */
	private final QfsmProject qfsm;

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
	}

	private void generateFsm() throws QfsmException {
		File file = new File(configuration.getOutputDirectory()
				+ File.separator + qfsm.getMachine().getName() + "Fsm.java");

		logger.info("Generating file: " + file.getPath());

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

			out.println(configuration.getFileHeader().trim());
			out.println();
			out.println("package " + configuration.getJavaPackage().trim()
					+ ";");
			out.println();
			out.println(configuration.getFsmImports().trim());
			out.println();

			out.close();
		} catch (IOException e) {
			throw new QfsmException("Writing file failed", e);
		}

		logger.info("File generated: " + file.getPath());
	}

	private void generateProcessor() {

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
