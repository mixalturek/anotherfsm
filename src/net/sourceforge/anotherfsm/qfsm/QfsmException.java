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

package net.sourceforge.anotherfsm.qfsm;

import net.sourceforge.anotherfsm.FsmException;

/**
 * Qfsm related exception.
 * 
 * @author Michal Turek
 */
public class QfsmException extends FsmException {
	/** The version UID. */
	private static final long serialVersionUID = -1272714407207116582L;

	/**
	 * Create the object.
	 */
	public QfsmException() {
		// empty
	}

	/**
	 * Create the object.
	 * 
	 * @param message
	 *            the message
	 */
	public QfsmException(String message) {
		super(message);
	}

	/**
	 * Create the object.
	 * 
	 * @param cause
	 *            the cause
	 */
	public QfsmException(Throwable cause) {
		super(cause);
	}

	/**
	 * Create the object.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public QfsmException(String message, Throwable cause) {
		super(message, cause);
	}
}
