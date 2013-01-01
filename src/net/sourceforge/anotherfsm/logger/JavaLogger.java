/*
 *  Copyright 2013 Michal Turek, Another FSM
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

import net.sourceforge.anotherfsm.AnotherFsm;

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
		logger = Logger.getLogger(clazz.getName()
				+ AnotherFsm.CLASS_INSTANCE_DELIMITER + instance);
	}

	/**
	 * Create the object.
	 * 
	 * @param name
	 *            the logger name
	 */
	public JavaLogger(String name) {
		logger = Logger.getLogger(name);
	}

	@Override
	public String getName() {
		return logger.getName();
	}

	@Override
	public void fatal(Object message) {
		logger.log(Level.SEVERE, message.toString());
	}

	@Override
	public void fatal(Object message, Throwable throwable) {
		logger.log(Level.SEVERE, message.toString(), throwable);
	}

	@Override
	public void error(Object message) {
		logger.log(Level.SEVERE, message.toString());
	}

	@Override
	public void error(Object message, Throwable throwable) {
		logger.log(Level.SEVERE, message.toString(), throwable);
	}

	@Override
	public void warn(Object message) {
		logger.log(Level.WARNING, message.toString());
	}

	@Override
	public void warn(Object message, Throwable throwable) {
		logger.log(Level.WARNING, message.toString(), throwable);
	}

	@Override
	public void info(Object message) {
		logger.log(Level.INFO, message.toString());
	}

	@Override
	public void info(Object message, Throwable throwable) {
		logger.log(Level.INFO, message.toString(), throwable);
	}

	@Override
	public void debug(Object message) {
		logger.log(Level.FINE, message.toString());
	}

	@Override
	public void debug(Object message, Throwable throwable) {
		logger.log(Level.FINE, message.toString(), throwable);
	}

	@Override
	public void trace(Object message) {
		logger.log(Level.FINER, message.toString());
	}

	@Override
	public void trace(Object message, Throwable throwable) {
		logger.log(Level.FINER, message.toString(), throwable);
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
