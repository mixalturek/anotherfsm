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

/**
 * Logger that passes the messages to the standard output and standard error
 * streams. All received messages are logged independently to the severity.
 * 
 * The instances of the class is NOT thread safe. The output could be messed by
 * external System.out.*() and System.err.*() calls and the logger should be as
 * fast as possible.
 * 
 * @author Michal Turek
 */
class BasicLogger implements FsmLogger {
	/** The class instance. */
	public static final FsmLogger instance = new BasicLogger();

	/**
	 * Forbid creating objects.
	 */
	private BasicLogger() {
		// Do nothing
	}

	@Override
	public String getName() {
		return getClass().getSimpleName();
	}

	@Override
	public void fatal(String message) {
		System.err.println(message);
	}

	@Override
	public void fatal(String message, Throwable throwable) {
		System.err.println(message);
		throwable.printStackTrace(System.err);
	}

	@Override
	public void error(String message) {
		System.err.println(message);
	}

	@Override
	public void error(String message, Throwable throwable) {
		System.err.println(message);
		throwable.printStackTrace(System.err);
	}

	@Override
	public void warn(String message) {
		System.err.println(message);
	}

	@Override
	public void warn(String message, Throwable throwable) {
		System.err.println(message);
		throwable.printStackTrace(System.err);
	}

	@Override
	public void info(String message) {
		System.out.println(message);
	}

	@Override
	public void info(String message, Throwable throwable) {
		System.out.println(message);
		throwable.printStackTrace(System.out);
	}

	@Override
	public void debug(String message) {
		System.out.println(message);
	}

	@Override
	public void debug(String message, Throwable throwable) {
		System.out.println(message);
		throwable.printStackTrace(System.out);
	}

	@Override
	public void trace(String message) {
		System.out.println(message);
	}

	@Override
	public void trace(String message, Throwable throwable) {
		System.out.println(message);
		throwable.printStackTrace(System.out);
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
}
