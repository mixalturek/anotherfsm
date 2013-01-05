/*
 *  Copyright 2012 Michal Turek, Another FSM
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

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sourceforge.anotherfsm.Helpers;

/**
 * Logger that logs to the standard output and standard error streams. All
 * received messages are logged independently to the severity.
 * 
 * The instances of the class is NOT thread safe. The output could be messed by
 * external System.out.*() and System.err.*() calls and the logger should be as
 * fast as possible.
 * 
 * @author Michal Turek
 */
class StdStreamLogger implements FsmLogger {
	/** The logger name. */
	private final String name;

	/** Formatter of date. */
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss,SSS");

	/**
	 * Create the object.
	 * 
	 * @param name
	 *            the logger name
	 */
	public StdStreamLogger(String name) {
		Helpers.ensureNotNull(name, "name");

		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void fatal(String message) {
		log(System.err, "FATAL", message, null);
	}

	@Override
	public void fatal(String message, Throwable throwable) {
		log(System.err, "FATAL", message, throwable);
	}

	@Override
	public void error(String message) {
		log(System.err, "ERROR", message, null);
	}

	@Override
	public void error(String message, Throwable throwable) {
		log(System.err, "ERROR", message, throwable);
	}

	@Override
	public void warn(String message) {
		log(System.err, " WARN", message, null);
	}

	@Override
	public void warn(String message, Throwable throwable) {
		log(System.err, " WARN", message, throwable);
	}

	@Override
	public void info(String message) {
		log(System.out, " INFO", message, null);
	}

	@Override
	public void info(String message, Throwable throwable) {
		log(System.out, " INFO", message, throwable);
	}

	@Override
	public void debug(String message) {
		log(System.out, "DEBUG", message, null);
	}

	@Override
	public void debug(String message, Throwable throwable) {
		log(System.out, "DEBUG", message, throwable);
	}

	@Override
	public void trace(String message) {
		log(System.out, "TRACE", message, null);
	}

	@Override
	public void trace(String message, Throwable throwable) {
		log(System.out, "TRACE", message, throwable);
	}

	@Override
	public boolean isInfoEnabled() {
		return true;
	}

	@Override
	public boolean isDebugEnabled() {
		return true;
	}

	@Override
	public boolean isTraceEnabled() {
		return true;
	}

	/**
	 * Log the message.
	 * 
	 * @param stream
	 *            the output stream
	 * @param severity
	 *            the severity of the message
	 * @param message
	 *            the message
	 * @param throwable
	 *            the exception or null
	 */
	private void log(PrintStream stream, String severity, String message,
			Throwable throwable) {
		String date = dateFormat.format(new Date());
		String thread = Thread.currentThread().getName();

		stream.println(date + " [" + thread + "] " + severity + " " + name
				+ " - " + message);

		if (throwable != null)
			throwable.printStackTrace(stream);
	}
}
