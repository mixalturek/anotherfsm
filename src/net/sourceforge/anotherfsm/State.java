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

import java.util.LinkedList;
import java.util.List;

/**
 * The state in state machine.
 * 
 * @author Michal Turek
 */
class State {
	/** The name of the state. */
	private final String name;

	/** The state is final flag. */
	private final boolean finalState;

	/** The listeners. */
	private final List<StateListener> listeners = new LinkedList<StateListener>();

	/**
	 * Create the object.
	 * 
	 * @param name
	 *            the name of the state
	 */
	public State(String name) {
		this(name, false);
	}

	/**
	 * Create the object.
	 * 
	 * @param name
	 *            the name of the state
	 * @param finalState
	 *            the state is final flag
	 */
	public State(String name, boolean finalState) {
		if (name == null)
			throw new NullPointerException("Name must not be null");

		this.name = name;
		this.finalState = finalState;
	}

	/**
	 * Add a new listener. The method is not thread safe.
	 * 
	 * @param listener
	 *            the listener
	 */
	public void addListener(StateListener listener) {
		listeners.add(listener);
	}

	/**
	 * The state was entered, notify listeners. Internal use only.
	 * 
	 * @param previous
	 *            the previous state
	 * @param event
	 *            the event
	 * @param current
	 *            the current state
	 */
	void notifyEnter(State previous, Event event, State current) {
		if (finalState) {
			for (StateListener listener : listeners) {
				listener.onStateEnter(previous, event, current);
				listener.onFinalStateEnter(previous, event, current);
			}
		} else {
			for (StateListener listener : listeners) {
				listener.onStateEnter(previous, event, current);
			}
		}
	}

	/**
	 * The state was exited, notify listeners. Internal use only.
	 * 
	 * @param current
	 *            the current state
	 * @param event
	 *            the event
	 * @param next
	 *            the next state
	 */
	void notifyExit(State current, Event event, State next) {
		if (finalState) {
			for (StateListener listener : listeners) {
				listener.onStateExit(current, event, next);
				listener.onFinalStateExit(current, event, next);
			}
		} else {
			for (StateListener listener : listeners) {
				listener.onStateExit(current, event, next);
			}
		}
	}

	/**
	 * Get name of the state.
	 * 
	 * @return the name of the state
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the state is final flag.
	 * 
	 * @return the flag
	 */
	public boolean isFinalState() {
		return finalState;
	}

	/**
	 * Get the hash code. Only name of the state is used for creating hash code.
	 * 
	 * @return the hash code
	 */
	@Override
	public int hashCode() {
		return 3452542 + name.hashCode();
	}

	/**
	 * Compare the objects using internal fields. FSM uses this method while
	 * building the state machine to ensure all states are unique.
	 * 
	 * Only name of the states is used during the comparison.
	 * 
	 * @param object
	 *            the object
	 * @return true if the objects are same, otherwise false
	 * @see StateMachine#process(Event)
	 */
	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;

		if (object == null || !(object instanceof State))
			return false;

		State other = (State) object;
		return name.equals(other.name);
	}

	/**
	 * The string representation of the object. It is expected the name of the
	 * state is returned.
	 * 
	 * @return the string representation
	 */
	@Override
	public String toString() {
		return name;
	}
}
