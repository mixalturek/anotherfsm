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

package net.sourceforge.anotherfsm.logger;

/**
 * Logger implementation that logs nothing similar to {@link NoLogger}, but the
 * is*Enabled() methods return true. This allows unit tests to process logging
 * specific code that would be prevented from running instead.
 * 
 * @author Michal Turek
 */
class NoLoggerJUnit implements FsmLogger {
	/** The class instance. */
	public static final FsmLogger instance = new NoLoggerJUnit();

	/**
	 * Forbid creating of the object.
	 */
	private NoLoggerJUnit() {
		// Do nothing
	}

	@Override
	public String getName() {
		return getClass().getSimpleName();
	}

	@Override
	public void fatal(String message) {
		// Do nothing
	}

	@Override
	public void fatal(String message, Throwable throwable) {
		// Do nothing
	}

	@Override
	public void error(String message) {
		// Do nothing
	}

	@Override
	public void error(String message, Throwable throwable) {
		// Do nothing
	}

	@Override
	public void warn(String message) {
		// Do nothing
	}

	@Override
	public void warn(String message, Throwable throwable) {
		// Do nothing
	}

	@Override
	public void info(String message) {
		// Do nothing
	}

	@Override
	public void info(String message, Throwable throwable) {
		// Do nothing
	}

	@Override
	public void debug(String message) {
		// Do nothing
	}

	@Override
	public void debug(String message, Throwable throwable) {
		// Do nothing
	}

	@Override
	public void trace(String message) {
		// Do nothing
	}

	@Override
	public void trace(String message, Throwable throwable) {
		// Do nothing
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
