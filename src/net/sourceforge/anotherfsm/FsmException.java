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

package net.sourceforge.anotherfsm;

/**
 * Base class for all FSM exceptions.
 * 
 * @author Michal Turek
 */
public class FsmException extends Exception {
	/** The version UID. */
	private static final long serialVersionUID = -7618461541208193028L;

	/**
	 * Create the object.
	 */
	public FsmException() {
		// empty
	}

	/**
	 * Create the object.
	 * 
	 * @param message
	 *            the message
	 */
	public FsmException(String message) {
		super(message);
	}

	/**
	 * Create the object.
	 * 
	 * @param cause
	 *            the cause
	 */
	public FsmException(Throwable cause) {
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
	public FsmException(String message, Throwable cause) {
		super(message, cause);
	}
}
