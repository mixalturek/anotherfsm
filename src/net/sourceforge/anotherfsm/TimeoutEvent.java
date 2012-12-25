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
 * State machine allows only one transition of timeout event for each state
 * independently to the timeouts and parameters. If a state had two timeouts the
 * first one would be always processed and the second never.
 * 
 * @author Michal Turek
 * 
 * @see TimeoutStateMachine
 */
public interface TimeoutEvent extends Event {
	/**
	 * Use this hash code in your implementation of this interface in
	 * hashCode(). Also ensure all instances of TimeoutEvent are equal.
	 * 
	 * @see #hashCode()
	 * @see #equals(Object)
	 * @see TimeoutEventImpl#hashCode()
	 * @see TimeoutEventImpl#equals(Object)
	 */
	public static final int HASH_CODE = 35345225;

	/**
	 * Get the timeout.
	 * 
	 * @return the timeout
	 */
	public long getTimeout();

	/**
	 * Get the type.
	 * 
	 * @return the type
	 */
	public TimeoutEvent.Type getType();

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
	 * @see LoopTransition
	 */
	public static enum Type {
		/** Restart the timeout on loop transition. */
		RESTART_TIMEOUT_ON_LOOP,

		/**
		 * Don't restart the timeout on loop transition and let the previous to
		 * remain active.
		 */
		DONT_RESTART_TIMEOUT_ON_LOOP
	}
}
