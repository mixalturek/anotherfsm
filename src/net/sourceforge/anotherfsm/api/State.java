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

package net.sourceforge.anotherfsm.api;

/**
 * Abstract FSM state.
 * 
 * @author Michal Turek
 */
public interface State {

	/**
	 * Get name of the state.
	 * 
	 * @return the name of the state
	 */
	public String getName();

	/**
	 * Get the state is final flag.
	 * 
	 * @return the flag
	 */
	public boolean isFinalState();

	/**
	 * Add a new listener. The method is not thread safe.
	 * 
	 * @param listener
	 *            the listener
	 */
	public void addListener(StateListener listener);

	/**
	 * Remove the listener. The method is not thread safe.
	 * 
	 * @param listener
	 *            the listener
	 * @return true if the listener was defined and removed
	 */
	public boolean removeListener(StateListener listener);

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
	public void notifyEnter(State previous, Event event, State current);

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
	public void notifyExit(State current, Event event, State next);

	/**
	 * Get the hash code.
	 * 
	 * @return the hash code
	 */
	@Override
	public int hashCode();

	/**
	 * Compare the objects using internal fields. FSM uses this method while
	 * building the state machine to ensure all states are unique.
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
	 * state is returned.
	 * 
	 * @return the string representation
	 */
	@Override
	public String toString();
}
