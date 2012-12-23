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

import java.util.Set;

/**
 * Abstract state machine
 * 
 * @author Michal Turek
 */
public interface StateMachine extends TypeProcessors {
	/**
	 * Get the name of the state machine.
	 * 
	 * @return the name
	 */
	public String getName();

	/**
	 * Set the start state. The method is not thread safe.
	 * 
	 * @param state
	 *            the state
	 * @see #process(Event)
	 */
	public void setStartState(State state) throws FsmException;

	/**
	 * Add a new state. The method is not thread safe.
	 * 
	 * @param state
	 *            the state
	 * @throws FsmException
	 *             if something fails
	 */
	public void addState(State state) throws FsmException;

	/**
	 * Add a new transition. The method is not thread safe.
	 * 
	 * @param transition
	 *            the transition
	 * @throws FsmException
	 *             if something fails
	 */
	public void addTransition(Transition transition) throws FsmException;

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
	 * Start processing of the events, building of the state machine is
	 * finished.
	 * 
	 * @throws FsmException
	 *             if something fails
	 */
	public void start() throws FsmException;

	/**
	 * Stop procession of the events, free all allocated resources.
	 */
	public void stop();

	/**
	 * Get the currently active state. Intended use in deterministic state
	 * machines.
	 * 
	 * @return the currently active state
	 */
	public State getActiveState();

	/**
	 * Get the currently active states. Intended use in nondeterministic state
	 * machines.
	 * 
	 * @return the currently active states
	 */
	public Set<State> getActiveStates();

	/**
	 * The string representation of the object. It is expected the name of the
	 * state machine will be returned (if defined).
	 * 
	 * @return the string representation
	 */
	@Override
	public String toString();

	/**
	 * Get all defined states.
	 * 
	 * @return the states
	 */
	public Set<State> getStates();

	/**
	 * Get all defined transitions.
	 * 
	 * @return the transitions
	 */
	public Set<Transition> getTransitions();
}
