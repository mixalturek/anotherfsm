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
 * Abstract FSM state.
 * 
 * @author Michal Turek
 */
public interface State {

	/**
	 * Add a transition to the state.
	 * 
	 * @param event
	 *            the event that causes the transition
	 * @param nextState
	 *            the next state that will be active after the transition is
	 *            processed
	 * @param listener
	 *            the listener for callback
	 * @throws FsmException
	 *             if something fails
	 */
	public void addTransition(Event event, State nextState,
			TransitionListener listener) throws FsmException;

	/**
	 * Add a transition to the state. The active state won't be changed during
	 * this loop transition.
	 * 
	 * @param event
	 *            the event that causes the transition
	 * @param listener
	 *            the listener for callback
	 * @throws FsmException
	 *             if something fails
	 */
	public void addTransition(Event event, TransitionListener listener)
			throws FsmException;

	/**
	 * Add a transition to the state.
	 * 
	 * @param event
	 *            the event that causes the transition
	 * @param nextState
	 *            the next state that will be active after the transition is
	 *            processed
	 * @throws FsmException
	 *             if something fails
	 */
	public void addTransition(Event event, State nextState) throws FsmException;

	/**
	 * Set the listener.
	 * 
	 * @param listener
	 *            the listener
	 */
	public void setListener(StateListener listener);

	/**
	 * Process an event.
	 * 
	 * @param event
	 *            the event
	 * @return the new active state
	 * @throws FsmException
	 *             If something fails.
	 */
	public State processEvent(Event event) throws FsmException;

	@Override
	public int hashCode();

	/**
	 * Compare the objects using internal fields. FSM uses this method while
	 * building the state machine to ensure all states are unique.
	 * 
	 * @param object
	 *            the object
	 * @return true if the objects are same, otherwise false
	 * @see StateMachine#processEvent(Event)
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
