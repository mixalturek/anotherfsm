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

package net.sourceforge.anotherfsm.deterministic;

import java.util.LinkedList;
import java.util.List;

import net.sourceforge.anotherfsm.api.Event;
import net.sourceforge.anotherfsm.api.State;
import net.sourceforge.anotherfsm.api.StateListener;

/**
 * The implementation of state.
 * 
 * @author Michal Turek
 */
public class BasicState implements State {
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
	public BasicState(String name) {
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
	public BasicState(String name, boolean finalState) {
		if (name == null)
			throw new NullPointerException("Name must not be null");

		this.name = name;
		this.finalState = finalState;
	}

	@Override
	public void addListener(StateListener listener) {
		listeners.add(listener);
	}

	@Override
	public boolean removeListener(StateListener listener) {
		return listeners.remove(listener);
	}

	@Override
	public void notifyEnter(State previous, Event event, State current) {
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

	@Override
	public void notifyExit(State current, Event event, State next) {
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

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isFinalState() {
		return finalState;
	}

	/**
	 * Only name of the state is used for creating hash code.
	 */
	@Override
	public int hashCode() {
		return 3452542 + name.hashCode();
	}

	/**
	 * Only name of the states is used during the comparison.
	 */
	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;

		if (object == null || !(object instanceof BasicState))
			return false;

		BasicState other = (BasicState) object;
		return name.equals(other.name);
	}

	@Override
	public String toString() {
		return name;
	}
}
