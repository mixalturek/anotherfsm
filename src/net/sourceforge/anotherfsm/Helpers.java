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

package net.sourceforge.anotherfsm;

import net.sourceforge.anotherfsm.logger.FsmLogger;

/**
 * Helper functions.
 * 
 * @author Michal Turek
 */
public class Helpers {
	/** The delimiter of class names and instances. */
	public static final String CLASS_INSTANCE_DELIMITER = ".";

	/**
	 * Verify the passed object is not null.
	 * 
	 * @param object
	 *            the object
	 * @param objectName
	 *            the object name
	 * @throws NullPointerException
	 *             if the object is null
	 */
	public static void ensureNotNull(Object object, String objectName)
			throws NullPointerException {
		if (object == null) {
			throw new NullPointerException("Object must not be null: "
					+ objectName);
		}
	}

	/**
	 * Log runtime exception that occurred in client callback.
	 * 
	 * Log everything what is possible for future analysis and re-throw the
	 * exception. Current thread may stop but it is better than hide a more
	 * serious error.
	 * 
	 * @param logger
	 *            the logger
	 * @param message
	 *            the message to log
	 * @param exception
	 *            the exception that occurred
	 * 
	 * @see TimeoutStateMachine
	 */
	public static void logExceptionInClientCallback(FsmLogger logger,
			String message, RuntimeException exception) {
		logger.fatal(
				"Unexpected exception occurred probably in client callback code: "
						+ message + ", thread "
						+ Thread.currentThread().getName() + ", exception "
						+ exception.getClass() + ", exception message "
						+ exception.getMessage() + ", exception " + exception,
				exception);
	}

	/**
	 * Log that a serious error occurred and the currently running thread is
	 * being unexpectedly terminated.
	 * 
	 * @param logger
	 *            the logger for logging
	 * @param message
	 *            the message to log
	 * @param exception
	 *            the exception that occurred
	 */
	public static void logThreadUnexpectedlyFinished(FsmLogger logger,
			String message, Throwable exception) {
		logger.fatal(
				"Thread unexpectedly finished: " + message + ", thread "
						+ Thread.currentThread().getName() + ", exception "
						+ exception.getClass() + ", exception message "
						+ exception.getMessage() + ", exception " + exception,
				exception);
	}
}
