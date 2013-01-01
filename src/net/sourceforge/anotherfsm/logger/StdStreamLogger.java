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

/**
 * Logger that logs to the standard output and error streams.
 * 
 * @author Michal Turek
 */
public class StdStreamLogger implements FsmLogger {
	/** The logger name. */
	private final String name;

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss,SSS");

	/**
	 * Create the object.
	 * 
	 * @param name
	 *            the logger name
	 */
	public StdStreamLogger(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void fatal(Object message) {
		log(System.err, "FATAL", message, null);
	}

	@Override
	public void fatal(Object message, Throwable throwable) {
		log(System.err, "FATAL", message, throwable);
	}

	@Override
	public void error(Object message) {
		log(System.err, "ERROR", message, null);
	}

	@Override
	public void error(Object message, Throwable throwable) {
		log(System.err, "ERROR", message, throwable);
	}

	@Override
	public void warn(Object message) {
		log(System.err, " WARN", message, null);
	}

	@Override
	public void warn(Object message, Throwable throwable) {
		log(System.err, " WARN", message, throwable);
	}

	@Override
	public void info(Object message) {
		log(System.out, " INFO", message, null);
	}

	@Override
	public void info(Object message, Throwable throwable) {
		log(System.out, " INFO", message, throwable);
	}

	@Override
	public void debug(Object message) {
		log(System.out, "DEBUG", message, null);
	}

	@Override
	public void debug(Object message, Throwable throwable) {
		log(System.out, "DEBUG", message, throwable);
	}

	@Override
	public void trace(Object message) {
		log(System.out, "TRACE", message, null);
	}

	@Override
	public void trace(Object message, Throwable throwable) {
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
	 * @param message
	 *            the message
	 * @param throwable
	 *            the exception or null
	 */
	private void log(PrintStream stream, String severity, Object message,
			Throwable throwable) {
		// Log4j default is
		// date [thread] severity class message
		// 395 [main] INFO class
		// net.sourceforge.anotherfsm.DeterministicStateMachine-fsm - Transition
		// started: state -> TypeEventA -> another

		String date = dateFormat.format(new Date());
		String thread = Thread.currentThread().getName();

		stream.println(date + " [" + thread + "] " + severity + " " + name
				+ " - " + message);

		if (throwable != null)
			throwable.printStackTrace(stream);
	}
}
