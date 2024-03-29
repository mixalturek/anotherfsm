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
 * Logger implementation that logs nothing. Prefer to not use this class and
 * always log at least something.
 * 
 * @author Michal Turek
 */
class NoLogger implements FsmLogger {
	/** The class instance. */
	public static final FsmLogger instance = new NoLogger();

	/**
	 * Forbid creating of the object.
	 */
	private NoLogger() {
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
		return false;
	}

	@Override
	public boolean isDebugEnabled() {
		return false;
	}

	@Override
	public boolean isTraceEnabled() {
		return false;
	}
}
