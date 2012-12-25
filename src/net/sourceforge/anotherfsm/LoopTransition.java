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
 * The loop transition. The source and destination state is the same.
 * 
 * <pre>
 * +---+
 * | S |------+
 * +---+      |
 *   ^        | A loop transition, 'S' is both source and destination state.
 *   |        |
 *   +--------+
 * </pre>
 * 
 * @author Michal Turek
 */
public class LoopTransition extends Transition {
	/** The delimiter for string representation. */
	public static final String TR = " <-> ";

	/**
	 * Create the object.
	 * 
	 * @param state
	 *            the source and destination state
	 * @param event
	 *            the event
	 */
	public LoopTransition(State state, Event event) {
		super(state, event, state);
	}

	/**
	 * Get the source and destination state.
	 * 
	 * @return the state
	 */
	public State getState() {
		return getSource();
		// or
		// return getDestination();
	}

	// Don't define hashCode() and equals() the ones from super class works.

	@Override
	public String toString() {
		return getState() + TR + getEvent();
	}
}
