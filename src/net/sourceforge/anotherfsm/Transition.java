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
 * The transition in state machine.
 * 
 * @author Michal Turek
 */
class Transition {
	/** The delimiter for string representation. */
	static final String TR = " -> ";

	/** The source state. */
	private final State source;

	/** The event. */
	private final Event event;

	/** The destination state. */
	private final State destination;

	/** The listeners. */
	private final List<TransitionListener> listeners = new LinkedList<TransitionListener>();

	/**
	 * Create the object.
	 * 
	 * @param sourceState
	 *            the source state
	 * @param event
	 *            the event
	 * @param destination
	 *            the destination state
	 */
	public Transition(State source, Event event, State destination) {
		Helpers.ensureNotNull(source, "source state");
		Helpers.ensureNotNull(event, "event");
		Helpers.ensureNotNull(destination, "destination state");

		this.source = source;
		this.event = event;
		this.destination = destination;
	}

	/**
	 * Create the object. The loop transition, the source and destination states
	 * are the same.
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
	 * @param state
	 *            the source and destination state
	 * @param event
	 *            the event
	 */
	public Transition(State state, Event event) {
		this(state, event, state);
	}

	/**
	 * Get the source state.
	 * 
	 * @return the transition source state
	 */
	public State getSource() {
		return source;
	}

	/**
	 * Get the transition event.
	 * 
	 * @return the transition event
	 */
	public Event getEvent() {
		return event;
	}

	/**
	 * Get the transition destination state.
	 * 
	 * @return the transition destination state
	 */
	public State getDestination() {
		return destination;
	}

	/**
	 * Add a new listener. The method is not thread safe.
	 * 
	 * @param listener
	 *            the listener
	 */
	public void addListener(TransitionListener listener) {
		Helpers.ensureNotNull(listener, "listener");

		listeners.add(listener);
	}

	/**
	 * The transition was processed, notify listeners. Internal use only.
	 * 
	 * @param source
	 *            the source state
	 * @param event
	 *            the event
	 * @param destination
	 *            the destination state
	 */
	void notifyTransition(State source, Event event, State destination) {
		for (TransitionListener listener : listeners) {
			listener.onTransition(source, event, destination);
		}
	}

	/**
	 * Get the hash code.
	 * 
	 * @return the hash code
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + destination.hashCode();
		result = prime * result + event.hashCode();
		result = prime * result + source.hashCode();
		return result;
	}

	/**
	 * Compare the objects using internal fields. FSM uses this method while
	 * building the state machine to ensure all transitions are unique.
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

		if (object == null || !(object instanceof Transition))
			return false;

		Transition other = (Transition) object;
		return source.equals(other.getSource())
				&& event.equals(other.getEvent())
				&& destination.equals(other.getDestination());
	}

	/**
	 * The string representation of the object. It is expected the name of the
	 * internal fields are returned.
	 * 
	 * @return the string representation
	 */
	@Override
	public String toString() {
		return source + TR + event + TR + destination;
	}
}
