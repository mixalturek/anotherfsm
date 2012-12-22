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
 * The transition in FSM.
 * 
 * @author Michal Turek
 */
public interface Transition {
	/**
	 * Get the source state.
	 * 
	 * @return the transition source state
	 */
	public State getSource();

	/**
	 * Get the transition event.
	 * 
	 * @return the transition event
	 */
	public Event getEvent();

	/**
	 * Get the transition destination state.
	 * 
	 * @return the transition destination state
	 */
	public State getDestination();

	/**
	 * Add a new listener. The method is not thread safe.
	 * 
	 * @param listener
	 *            the listener
	 */
	public void addListener(TransitionListener listener);

	/**
	 * Remove the listener. The method is not thread safe.
	 * 
	 * @param listener
	 *            the listener
	 * @return true if the listener was defined and removed
	 */
	public boolean removeListener(TransitionListener listener);

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
	public void notifyTransition(State source, Event event, State destination);

	/**
	 * Get the hash code.
	 * 
	 * @return the hash code
	 */
	@Override
	public int hashCode();

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
	public boolean equals(Object object);

	/**
	 * The string representation of the object. It is expected the name of the
	 * internal fields are returned.
	 * 
	 * @return the string representation
	 */
	@Override
	public String toString();
}
