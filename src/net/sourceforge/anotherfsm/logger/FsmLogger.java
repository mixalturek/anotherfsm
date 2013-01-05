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

/**
 * Interface of a logger.
 * 
 * @author Michal Turek
 */
public interface FsmLogger {
	/**
	 * Get name of the logger.
	 * 
	 * @return the name
	 */
	public String getName();

	/**
	 * Log a fatal message.
	 * 
	 * @param message
	 *            the message
	 */
	public void fatal(String message);

	/**
	 * Log a fatal message.
	 * 
	 * @param message
	 *            the message
	 * @param throwable
	 *            a throwable object
	 */
	public void fatal(String message, Throwable throwable);

	/**
	 * Log an error message.
	 * 
	 * @param message
	 *            the message
	 */
	public void error(String message);

	/**
	 * Log an error message.
	 * 
	 * @param message
	 *            the message
	 * @param throwable
	 *            a throwable object
	 */
	public void error(String message, Throwable throwable);

	/**
	 * Log a warning message.
	 * 
	 * @param message
	 *            the message
	 */
	public void warn(String message);

	/**
	 * Log a warning message.
	 * 
	 * @param message
	 *            the message
	 * @param throwable
	 *            a throwable object
	 */
	public void warn(String message, Throwable throwable);

	/**
	 * Log an info message.
	 * 
	 * @param message
	 *            the message
	 */
	public void info(String message);

	/**
	 * Log an info message.
	 * 
	 * @param message
	 *            the message
	 * @param throwable
	 *            a throwable object
	 */
	public void info(String message, Throwable throwable);

	/**
	 * Log a debug message.
	 * 
	 * @param message
	 *            the message
	 */
	public void debug(String message);

	/**
	 * Log a debug message.
	 * 
	 * @param message
	 *            the message
	 * @param throwable
	 *            a throwable object
	 */
	public void debug(String message, Throwable throwable);

	/**
	 * Log a trace message.
	 * 
	 * @param message
	 *            the message
	 */
	public void trace(String message);

	/**
	 * Log a trace message.
	 * 
	 * @param message
	 *            the message
	 * @param throwable
	 *            a throwable object
	 */
	public void trace(String message, Throwable throwable);

	/**
	 * Get status of info messages logging in this logger.
	 * 
	 * @return true if logging of info messages is enabled otherwise false
	 */
	public boolean isInfoEnabled();

	/**
	 * Get status of debug messages logging in this logger.
	 * 
	 * @return true if logging of debug messages is enabled otherwise false
	 */
	public boolean isDebugEnabled();

	/**
	 * Get status of trace messages logging in this logger.
	 * 
	 * @return true if logging of trace messages is enabled otherwise false
	 */
	public boolean isTraceEnabled();
}
