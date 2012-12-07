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

import java.util.HashMap;
import java.util.Map;

import net.sourceforge.anotherfsm.api.Event;
import net.sourceforge.anotherfsm.api.FsmException;
import net.sourceforge.anotherfsm.api.State;
import net.sourceforge.anotherfsm.api.Transition;

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
	 *            the transtion
	 * @throws FsmException
	 *             if something fails
	 */
	public void addTransition(Transition transition) throws FsmException {
		if (transitions.containsValue(transition))
			throw new FsmException("Transition has been already defined: "
					+ transition);

		transitions.put(transition.getEvent(), transition);
	}

	/**
	 * Get a transition for the event.
	 * 
	 * @param event
	 *            the event
	 * @param state
	 *            the source state
	 * @return the transition or null
	 * @throws FsmException
	 *             if transition is not defined
	 */
	public Transition getTransition(Event event, State state)
			throws FsmException {
		Transition transition = transitions.get(event);

		if (transition == null)
			throw new FsmException("Transition is not defined: " + state + ", "
					+ event);

		return transition;
	}
}
