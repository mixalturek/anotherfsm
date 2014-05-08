/*
 *  Copyright 2013 Michal Turek, AnotherFSM
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
import java.util.LinkedList;
import java.util.List;

/**
 * Non-deterministic state to transition mapping.
 * 
 * @author Michal Turek
 */
class NondeterministicTransitionMap {
	/** The transitions. */
	private final List<Transition> transitions = new LinkedList<Transition>();

	/**
	 * Create the object.
	 */
	public NondeterministicTransitionMap() {
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

		for (Transition tr : transitions) {
			if (tr.equals(transition)) {
				throw new FsmException("Transition already defined: "
						+ transition);
			}
		}

		transitions.add(transition);
	}

	/**
	 * Get all transitions for the event.
	 * 
	 * @param event
	 *            the event
	 * @return the transitions for the event, empty list if nothing is found
	 */
	public Collection<Transition> getTransitions(Event event) {
		Helpers.ensureNotNull(event, "event");

		List<Transition> matchingTransitions = new LinkedList<Transition>();

		for (Transition transition : transitions) {
			if (event.equals(transition.getEvent())) {
				matchingTransitions.add(transition);
			}
		}

		return matchingTransitions;
	}

	/**
	 * Get all defined transitions.
	 * 
	 * @return the transitions
	 */
	public Collection<Transition> getTransitions() {
		// Don't copy, the class is package private
		return transitions;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		for (Transition transition : transitions) {
			builder.append(transition.toString());
			builder.append("\n");
		}

		return builder.toString();
	}
}
