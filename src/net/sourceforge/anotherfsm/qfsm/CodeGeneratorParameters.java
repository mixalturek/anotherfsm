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

import java.util.LinkedList;
import java.util.List;

/**
 * Helper class to parse the program input parameters and arguments.
 * 
 * @author Michal Turek
 */
class CodeGeneratorParameters {
	/** Show usage. */
	private boolean usage = false;

	/** Force, override output files. */
	private boolean force = false;

	/** Generate only template for configuration. */
	private String configTemplate = null;

	/** The qfsm file */
	private String configFile = null;

	/** The configuration file */
	private String qfsmFile = null;

	/**
	 * Create the object.
	 * 
	 * @param args
	 *            the parameters to parse
	 * @throws QfsmException
	 *             if the parameters are invalid
	 */
	public CodeGeneratorParameters(String[] args) throws QfsmException {
		String[] params = preprocessParameters(args);
		List<String> unexpected = new LinkedList<String>();

		for (int i = 0; i < params.length; ++i) {
			switch (params[i]) {
			case "-h":
			case "--help":
				usage = true;
				break;

			case "-f":
			case "--force":
				force = true;
				break;

			case "-t":
			case "--template":
				if (i + 1 >= params.length) {
					throw new QfsmException(
							"Missing second argument of -t | --template");
				}

				++i;
				configTemplate = params[i];
				break;

			case "-c":
			case "--config-file":
				if (i + 1 >= params.length) {
					throw new QfsmException(
							"Missing second argument of -c | --config-file");
				}

				++i;
				configFile = params[i];
				break;

			case "-m":
			case "--qfsm-file":
				if (i + 1 >= params.length) {
					throw new QfsmException(
							"Missing second argument of -m | --qfsm-file");
				}

				++i;
				qfsmFile = params[i];
				break;

			default:
				unexpected.add(params[i]);
				break;
			}
		}

		if (configTemplate == null) {
			if (configFile == null) {
				throw new QfsmException(
						"Missing mandatory parameter: -c | --config-file");
			}

			if (qfsmFile == null) {
				throw new QfsmException(
						"Missing mandatory parameter: -m | --qfsm-file");
			}
		}

		if (!unexpected.isEmpty()) {
			throw new QfsmException("Unexpected parameters: " + unexpected);
		}
	}

	/**
	 * Preprocess multi value parameters, split to parts delimited by "=".
	 * 
	 * @param args
	 *            the input arguments
	 * @return the preprocessed arguments
	 */
	private final String[] preprocessParameters(String[] args) {
		List<String> out = new LinkedList<String>();

		for (int i = 0; i < args.length; ++i) {
			for (String part : args[i].split("=", 2))
				out.add(part);
		}

		return out.toArray(new String[0]);
	}

	/**
	 * Write usage to the standard output.
	 */
	public static void showUsage() {
		System.out.println("Usage:");
		System.out.println("\tjava CodeGenerator [arguments] parameters");
		System.out.println();
		System.out.println("Arguments: ");
		System.out.println("\t[-h | --help]           Show this usage.");
		System.out.println("\t[-f | --force]          Override output files.");
		System.out.println();
		System.out.println("Parameters: ");
		System.out.println("\t-t | --template file    Generate config.");
		System.out.println("\t-c | --config-file file Configuration file.");
		System.out.println("\t-m | --qfsm-file file   Qfsm file.");
	}

	public boolean isUsage() {
		return usage;
	}

	public boolean isForce() {
		return force;
	}

	public String getConfigTemplate() {
		return configTemplate;
	}

	public String getConfigFile() {
		return configFile;
	}

	public String getQfsmFile() {
		return qfsmFile;
	}
}