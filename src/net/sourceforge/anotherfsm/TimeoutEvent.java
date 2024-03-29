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
 * The timeout event.
 * 
 * State machines allow only two transitions of timeout event per state
 * independently to the timeout values. If a state had two same timeout types
 * the first one would be always processed and the second never.
 * 
 * @author Michal Turek
 * 
 * @see TimeoutStateMachine
 * @see Type#LOOP_RESTART
 * @see Type#LOOP_NO_RESTART
 */
public class TimeoutEvent implements Event {
	/** The instance of the object for use in timeout state machine. */
	static TimeoutEvent instance_LOOP_RESTART = new TimeoutEvent(1,
			TimeoutEvent.Type.LOOP_RESTART);

	/** The instance of the object for use in timeout state machine. */
	static TimeoutEvent instance_LOOP_NO_RESTART = new TimeoutEvent(1,
			TimeoutEvent.Type.LOOP_NO_RESTART);

	/** The timeout in milliseconds. */
	private final long timeout;

	/** The type of the event. */
	private final TimeoutEvent.Type type;

	/**
	 * Create instance of this class.
	 * 
	 * @param timeout
	 *            the timeout in milliseconds, must be positive
	 * @param type
	 *            the type of the event
	 * @return the instance
	 */
	public static TimeoutEvent instance(long timeout, TimeoutEvent.Type type) {
		return new TimeoutEvent(timeout, type);
	}

	/**
	 * Create the object.
	 * 
	 * @param timeout
	 *            the timeout in milliseconds, must be positive
	 * @param type
	 *            the type of the event
	 * @deprecated Prefer {@link #instance(long, Type)} method instead.
	 */
	@Deprecated
	public TimeoutEvent(long timeout, TimeoutEvent.Type type) {
		// Zero passed to Java timer works too, but let's forbid it
		if (timeout <= 0)
			throw new IllegalArgumentException("Timeout value must be positive");

		Helpers.ensureNotNull(type, "type");

		this.timeout = timeout;
		this.type = type;
	}

	public long getTimeout() {
		return timeout;
	}

	public TimeoutEvent.Type getType() {
		return type;
	}

	@Override
	public int hashCode() {
		return 34893234 + type.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (!(obj instanceof TimeoutEvent))
			return false;

		TimeoutEvent other = (TimeoutEvent) obj;
		return type == other.type;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" + type + ", " + timeout + ")";
	}

	/**
	 * The type of timeout event to specify the behavior on loop transition. The
	 * state is not changed during such transition.
	 * 
	 * <pre>
	 *   +----------------------------------------->
	 *   |          TimeoutEvent(timeout)
	 * +---+
	 * | S |------+
	 * +---+      | A loop transition
	 *   ^        | The timeout would be always restarted without any real
	 *   |        | change of the state if the loop transition was processed.
	 *   +--------+
	 * </pre>
	 * 
	 * @author Michal Turek
	 * 
	 * @see Transition#Transition(State, Event)
	 */
	public static enum Type {
		/** Restart the timeout on loop transition. */
		LOOP_RESTART,

		/**
		 * Don't restart the timeout on loop transition and let the previous to
		 * remain active.
		 */
		LOOP_NO_RESTART
	}
}
