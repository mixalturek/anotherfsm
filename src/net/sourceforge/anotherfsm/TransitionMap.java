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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * State to transition mapping.
 * 
 * @author Michal Turek
 */
class TransitionMap {
	/** The transitions. */
	private final Map<Event, Transition> transitions = new HashMap<Event, Transition>();

	/**
	 * Create the object.
	 */
	public TransitionMap() {
		// Empty
	}

	/**
	 * Add a new transition.
	 * 
	 * @param transition
	 *            the transition
	 * @throws FsmException
	 *             if something fails
	 */
	public void addTransition(Transition transition) throws FsmException {
		Helpers.ensureNotNull(transition, "transition");

		if (transitions.containsKey(transition.getEvent()))
			throw new FsmException("Transition already defined: " + transition);

		transitions.put(transition.getEvent(), transition);
	}

	/**
	 * Get a transition for the event.
	 * 
	 * @param event
	 *            the event
	 * @return the transition or null
	 */
	public Transition getTransition(Event event) {
		Helpers.ensureNotNull(event, "event");

		Transition transition = transitions.get(event);

		if (transition == null)
			return null;

		return transition;
	}

	/**
	 * Get all defined transitions.
	 * 
	 * @return the transitions
	 */
	public Collection<Transition> getTransitions() {
		return transitions.values();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		for (Transition transition : transitions.values()) {
			builder.append(transition.toString());
			builder.append("\n");
		}

		return builder.toString();
	}
}
