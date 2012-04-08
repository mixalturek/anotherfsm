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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * The implementation of state.
 * 
 * @author Michal Turek
 */
public class SimpleState implements State {

	/** The name of the state. */
	private final String name;

	/** The transitions from this state. */
	private final Map<Event, Transition> transitions = new HashMap<Event, Transition>();

	/** The listeners. */
	private final List<StateListener> listeners = new LinkedList<StateListener>();

	/**
	 * Create the object.
	 * 
	 * @param name
	 *            the name of the state
	 */
	public SimpleState(String name) {
		if (name == null)
			throw new NullPointerException("Name must not be null");

		this.name = name;
	}

	@Override
	public void addTransition(Transition transition) throws FsmException {
		if (transition == null)
			throw new NullPointerException("Transition must not be null");

		if (!equals(transition.getSourceState()))
			throw new FsmException("Source states don't match: " + this + ", "
					+ transition);

		Transition existing = transitions.get(transition.getEvent());
		if (existing != null)
			throw new FsmException("Transition already defined: " + transition);

		transitions.put(transition.getEvent(), transition);
	}

	@Override
	public void addTransition(Event event, State destinationState,
			TransitionListener listener) throws FsmException {
		if (event == null)
			throw new NullPointerException("Event must not be null");
		if (destinationState == null)
			throw new NullPointerException("State must not be null");

		addTransition(new SimpleTransition(this, event, destinationState,
				listener));
	}

	@Override
	public void addTransition(Event event, TransitionListener listener)
			throws FsmException {
		addTransition(event, this, listener);
	}

	@Override
	public void addTransition(Event event, State destinationState)
			throws FsmException {
		addTransition(event, this, null);
	}

	@Override
	public void addListener(StateListener listener) {
		listeners.add(listener);
	}

	@Override
	public List<StateListener> getListeners() {
		return new LinkedList<StateListener>(listeners);
	}

	@Override
	public State processEvent(Event event) throws FsmException {
		if (event == null)
			return this;

		Transition transition = transitions.get(event);
		if (transition == null)
			return this;

		State destinationState = transition.getDestinationState();

		for (StateListener listener : listeners)
			listener.onStateExit(this, event, destinationState);

		transition.processEvent(event);

		for (StateListener listener : destinationState.getListeners())
			listener.onStateEntry(this, event, destinationState);

		return destinationState;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return 3452542 + name.hashCode();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;

		if (object == null || !(object instanceof SimpleState))
			return false;

		SimpleState other = (SimpleState) object;
		return name.equals(other.name);
	}

	@Override
	public String toString() {
		return name;
	}
}
