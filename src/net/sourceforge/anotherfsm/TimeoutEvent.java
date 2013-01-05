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
 * The timeout event.
 * 
 * @author Michal Turek
 * 
 * @see TimeoutStateMachine
 */
public class TimeoutEvent extends TypeEvent {
	// FIXME: remove
	/** The instance of the object for use in timeout state machine. */
	static TimeoutEvent instance_LOOP_RESTART = new TimeoutEvent(1,
			TimeoutEvent.Type.LOOP_RESTART);

	// FIXME: remove
	/** The instance of the object for use in timeout state machine. */
	static TimeoutEvent instance_LOOP_NO_RESTART = new TimeoutEvent(1,
			TimeoutEvent.Type.LOOP_NO_RESTART);

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

	// FIXME: use timeout too
	@Override
	public int hashCode() {
		return super.hashCode() + type.hashCode();
	}

	// FIXME: use timeout too
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj) && type.equals(((TimeoutEvent) obj).getType());
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" + timeout + ", " + type + ")";
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
