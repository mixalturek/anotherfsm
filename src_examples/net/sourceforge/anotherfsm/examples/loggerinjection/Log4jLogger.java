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

package net.sourceforge.anotherfsm.examples.loggerinjection;

import net.sourceforge.anotherfsm.logger.FsmLogger;

import org.apache.log4j.Logger;

/**
 * Wrapper of log4j's logger.
 * 
 * @author Michal Turek
 */
class Log4jLogger implements FsmLogger {
	/** The wrapped log4j logger. */
	private final Logger logger;

	/**
	 * Create the object.
	 * 
	 * @param clazz
	 *            the class
	 */
	public Log4jLogger(Class<?> clazz) {
		logger = Logger.getLogger(clazz);
	}

	/**
	 * Create the object.
	 * 
	 * @param name
	 *            the logger name
	 */
	public Log4jLogger(String name) {
		logger = Logger.getLogger(name);
	}

	@Override
	public String getName() {
		return logger.getName();
	}

	@Override
	public void fatal(String message) {
		logger.fatal(message);
	}

	@Override
	public void fatal(String message, Throwable throwable) {
		logger.fatal(message, throwable);
	}

	@Override
	public void error(String message) {
		logger.error(message);
	}

	@Override
	public void error(String message, Throwable throwable) {
		logger.error(message, throwable);
	}

	@Override
	public void warn(String message) {
		logger.warn(message);
	}

	@Override
	public void warn(String message, Throwable throwable) {
		logger.warn(message, throwable);
	}

	@Override
	public void info(String message) {
		logger.info(message);
	}

	@Override
	public void info(String message, Throwable throwable) {
		logger.info(message, throwable);
	}

	@Override
	public void debug(String message) {
		logger.debug(message);
	}

	@Override
	public void debug(String message, Throwable throwable) {
		logger.debug(message, throwable);
	}

	@Override
	public void trace(String message) {
		logger.trace(message);
	}

	@Override
	public void trace(String message, Throwable throwable) {
		logger.trace(message, throwable);
	}

	@Override
	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	@Override
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	@Override
	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}
}
