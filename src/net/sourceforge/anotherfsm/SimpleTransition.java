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
public class SimpleTransition implements Transition {
	/** The source state. */
	private final State sourceState;

	/** The event. */
	private final Event event;

	/** The destination state. */
	private final State destinationState;

	/** The listeners. */
	private final List<TransitionListener> listeners = new LinkedList<TransitionListener>();

	/**
	 * Create the object.
	 * 
	 * @param sourceState
	 *            the source state
	 * @param event
	 *            the event
	 * @param destinationState
	 *            the destination state
	 * @param listener
	 *            the listener
	 */
	public SimpleTransition(State sourceState, Event event,
			State destinationState, TransitionListener listener) {
		if (sourceState == null)
			throw new NullPointerException("Source state must not be null");
		if (event == null)
			throw new NullPointerException("Event must not be null");
		if (destinationState == null)
			throw new NullPointerException("Destination state must not be null");

		this.sourceState = sourceState;
		this.event = event;
		this.destinationState = destinationState;

		if (listener != null)
			listeners.add(listener);
	}

	/**
	 * Create the object.
	 * 
	 * @param sourceState
	 *            the source state
	 * @param event
	 *            the event
	 * @param destinationState
	 *            the destination state
	 */
	public SimpleTransition(State sourceState, Event event,
			State destinationState) {
		if (sourceState == null)
			throw new NullPointerException("Source state must not be null");
		if (event == null)
			throw new NullPointerException("Event must not be null");
		if (destinationState == null)
			throw new NullPointerException("Destination state must not be null");

		this.sourceState = sourceState;
		this.event = event;
		this.destinationState = destinationState;
	}

	@Override
	public void processEvent(Event event) {
		for (TransitionListener listener : listeners)
			listener.onTransition(sourceState, event, destinationState);
	}

	@Override
	public State getSourceState() {
		return sourceState;
	}

	@Override
	public Event getEvent() {
		return event;
	}

	@Override
	public State getDestinationState() {
		return destinationState;
	}

	@Override
	public void addListener(TransitionListener listener) {
		listeners.add(listener);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + destinationState.hashCode();
		result = prime * result + event.hashCode();
		result = prime * result + sourceState.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;

		if (object == null || !(object instanceof SimpleTransition))
			return false;

		SimpleTransition other = (SimpleTransition) object;
		return sourceState.equals(other.getSourceState())
				&& event.equals(other.getEvent())
				&& destinationState.equals(other.getDestinationState());
	}

	@Override
	public String toString() {
		return sourceState + " -> " + event + " -> " + destinationState;
	}
}
