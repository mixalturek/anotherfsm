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

package net.sourceforge.anotherfsm;

/**
 * The implementation of timeout event.
 * 
 * State machine allows only one transition of timeout event for each state
 * independently to their timeouts and parameters. If a state had two timeouts
 * the first one would be always processed and the second never.
 * 
 * @author Michal Turek
 * 
 * @see TimeoutStateMachine
 */
class TimeoutEventImpl implements TimeoutEvent {
	/** The instance of the object for use in timeout state machine. */
	static TimeoutEvent INSTANCE = new TimeoutEventImpl(1,
			TimeoutEvent.Type.LOOP_RESTART);

	/** The timeout in milliseconds. */
	private final long timeout;

	/** The type of the event. */
	private final TimeoutEvent.Type type;

	/**
	 * Create the object.
	 * 
	 * @param timeout
	 *            the timeout in milliseconds, must be positive.
	 * @param type
	 *            the type of the event
	 */
	public TimeoutEventImpl(long timeout, TimeoutEvent.Type type) {
		// Zero passed to Java timer works too, but let's forbid it
		if (timeout <= 0)
			throw new IllegalArgumentException("Timeout value must be positive");

		if (type == null)
			throw new NullPointerException(
					"Timeout event type must not be null");

		this.timeout = timeout;
		this.type = type;
	}

	@Override
	public long getTimeout() {
		return timeout;
	}

	@Override
	public TimeoutEvent.Type getType() {
		return type;
	}

	@Override
	public int hashCode() {
		return HASH_CODE;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;

		return obj instanceof TimeoutEvent;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" + timeout + ", " + type + ")";
	}
}
