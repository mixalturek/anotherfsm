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

package net.sourceforge.anotherfsm.logger;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.sourceforge.anotherfsm.Helpers;

/**
 * Logger that uses Java logging API.
 * 
 * Note Java logging API is not fully compatible with FsmLogger. Both 'fatal'
 * and 'error' levels are mapped to 'severe'. Level 'finest' will be never
 * logged.
 * 
 * @author Michal Turek
 * 
 * @see java.util.logging.Logger
 */
class JavaLogger implements FsmLogger {
	/** The Java logger object. */
	private final Logger logger;

	/**
	 * Create the object.
	 * 
	 * @param clazz
	 *            the class
	 */
	public JavaLogger(Class<?> clazz) {
		Helpers.ensureNotNull(clazz, "class");

		logger = Logger.getLogger(clazz.getName());
	}

	/**
	 * Create the object.
	 * 
	 * @param clazz
	 *            the class
	 * @param instance
	 *            the class instance
	 */
	public JavaLogger(Class<?> clazz, String instance) {
		Helpers.ensureNotNull(clazz, "class");
		Helpers.ensureNotNull(instance, "instance");

		logger = Logger.getLogger(clazz.getName()
				+ Helpers.CLASS_INSTANCE_DELIMITER + instance);
	}

	/**
	 * Create the object.
	 * 
	 * @param name
	 *            the logger name
	 */
	public JavaLogger(String name) {
		Helpers.ensureNotNull(name, "name");

		logger = Logger.getLogger(name);
	}

	@Override
	public String getName() {
		return logger.getName();
	}

	@Override
	public void fatal(String message) {
		logger.log(Level.SEVERE, message);
	}

	@Override
	public void fatal(String message, Throwable throwable) {
		logger.log(Level.SEVERE, message, throwable);
	}

	@Override
	public void error(String message) {
		logger.log(Level.SEVERE, message);
	}

	@Override
	public void error(String message, Throwable throwable) {
		logger.log(Level.SEVERE, message, throwable);
	}

	@Override
	public void warn(String message) {
		logger.log(Level.WARNING, message);
	}

	@Override
	public void warn(String message, Throwable throwable) {
		logger.log(Level.WARNING, message, throwable);
	}

	@Override
	public void info(String message) {
		logger.log(Level.INFO, message);
	}

	@Override
	public void info(String message, Throwable throwable) {
		logger.log(Level.INFO, message, throwable);
	}

	@Override
	public void debug(String message) {
		logger.log(Level.FINE, message);
	}

	@Override
	public void debug(String message, Throwable throwable) {
		logger.log(Level.FINE, message, throwable);
	}

	@Override
	public void trace(String message) {
		logger.log(Level.FINER, message);
	}

	@Override
	public void trace(String message, Throwable throwable) {
		logger.log(Level.FINER, message, throwable);
	}

	@Override
	public boolean isInfoEnabled() {
		return logger.isLoggable(Level.INFO);
	}

	@Override
	public boolean isDebugEnabled() {
		return logger.isLoggable(Level.FINE);
	}

	@Override
	public boolean isTraceEnabled() {
		return logger.isLoggable(Level.FINER);
	}
}
