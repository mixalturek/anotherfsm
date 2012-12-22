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
 * The implementation of transition.
 * 
 * @author Michal Turek
 */
class BasicTransition implements Transition {
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
	public BasicTransition(State source, Event event, State destination) {
		if (source == null)
			throw new NullPointerException("Source state must not be null");
		if (event == null)
			throw new NullPointerException("Event must not be null");
		if (destination == null)
			throw new NullPointerException("Destination state must not be null");

		this.source = source;
		this.event = event;
		this.destination = destination;
	}

	@Override
	public State getSource() {
		return source;
	}

	@Override
	public Event getEvent() {
		return event;
	}

	@Override
	public State getDestination() {
		return destination;
	}

	@Override
	public void addListener(TransitionListener listener) {
		listeners.add(listener);
	}

	@Override
	public boolean removeListener(TransitionListener listener) {
		return listeners.remove(listener);
	}

	@Override
	public void notifyTransition(State source, Event event, State destination) {
		for (TransitionListener listener : listeners) {
			listener.onTransition(source, event, destination);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + destination.hashCode();
		result = prime * result + event.hashCode();
		result = prime * result + source.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;

		if (object == null || !(object instanceof BasicTransition))
			return false;

		BasicTransition other = (BasicTransition) object;
		return source.equals(other.getSource())
				&& event.equals(other.getEvent())
				&& destination.equals(other.getDestination());
	}

	@Override
	public String toString() {
		return source + " -> " + event + " -> " + destination;
	}
}
