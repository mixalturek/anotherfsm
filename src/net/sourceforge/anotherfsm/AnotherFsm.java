/*
 *  Copyright 2012 Michal Turek, AnotherFSM
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

package net.sourceforge.anotherfsm;

import net.sourceforge.anotherfsm.logger.FsmLogger;
import net.sourceforge.anotherfsm.logger.FsmLoggerFactory;
import net.sourceforge.anotherfsm.logger.StdStreamLoggerFactory;

/**
 * The main class in this library for configuration purposes.
 * 
 * @author Michal Turek
 */
public class AnotherFsm {
	/**
	 * The version of the library. The string has format
	 * "major.minor.release-note".
	 */
	public static final String VERSION = "0.2.0"; // See build.xml

	/** The factory of loggers. */
	private static FsmLoggerFactory loggerFactory = new StdStreamLoggerFactory();

	/**
	 * Define a logger factory that will be used to create loggers. The default
	 * logging subsystem is standard output and error streams.
	 * 
	 * Existing loggers will remain untouched. Prefer to call this method before
	 * all other ones in the library and just once.
	 * 
	 * @param factory
	 *            the logger factory that will be used for creating loggers
	 */
	public static void setLoggerFactory(FsmLoggerFactory factory) {
		Helpers.ensureNotNull(factory, "factory");

		loggerFactory = factory;
	}

	/**
	 * Get logger for a specific class.
	 * 
	 * @param clazz
	 *            the class
	 * @return the logger
	 */
	public static FsmLogger getLogger(Class<?> clazz) {
		Helpers.ensureNotNull(clazz, "class");

		return loggerFactory.getLogger(clazz);
	}

	/**
	 * Get logger for a specific class and instance.
	 * 
	 * @param clazz
	 *            the class
	 * @param instance
	 *            the class instance
	 * @return the logger
	 */
	public static FsmLogger getLogger(Class<?> clazz, String instance) {
		Helpers.ensureNotNull(clazz, "class");
		Helpers.ensureNotNull(instance, "instance");

		return loggerFactory.getLogger(clazz, instance);
	}

	/**
	 * Get logger for a specific logger with name.
	 * 
	 * @param name
	 *            the logger name
	 * @return the logger
	 */
	public static FsmLogger getLogger(String name) {
		Helpers.ensureNotNull(name, "name");

		return loggerFactory.getLogger(name);
	}
}
