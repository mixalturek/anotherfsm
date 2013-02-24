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

import java.util.Set;

/**
 * Interface for all state machines.
 * 
 * @author Michal Turek
 */
public interface StateMachine extends Processor, AutoCloseable {
	/** The name of temporary generated initial state. */
	public static final String INITIAL_STATE_NAME = "@INITIAL@";

	/**
	 * Set the start state.
	 * 
	 * @param state
	 *            the state
	 * @see #start()
	 * @see #process(Event)
	 */
	public void setStartState(State state) throws FsmException;

	/**
	 * Add a new state.
	 * 
	 * @param state
	 *            the state
	 * @throws FsmException
	 *             if something fails
	 */
	public void addState(State state) throws FsmException;

	/**
	 * Add a new transition.
	 * 
	 * @param transition
	 *            the transition
	 * @throws FsmException
	 *             if something fails
	 */
	public void addTransition(Transition transition) throws FsmException;

	/**
	 * Add a new listener.
	 * 
	 * @param listener
	 *            the listener
	 */
	public void addListener(StateListener listener);

	/**
	 * Add a new listener.
	 * 
	 * @param listener
	 *            the listener
	 */
	public void addListener(TransitionListener listener);

	/**
	 * Listeners of start state will be notified with non-loop transition from
	 * temporary generated initial state and {@link StartEvent} object.
	 * 
	 * @throws FsmException
	 *             if something fails
	 * @see StartEvent
	 * @see #INITIAL_STATE_NAME
	 */
	@Override
	public void start() throws FsmException;

	@Override
	public void close();

	/**
	 * Get the currently active state. Intended use is in deterministic state
	 * machines, for nondeterministic state machines only one of the active
	 * states is returned.
	 * 
	 * @return the currently active state
	 * @see #getActiveStates()
	 */
	public State getActiveState();

	/**
	 * Get the currently active states. Intended use is in nondeterministic
	 * state machines.
	 * 
	 * @return the currently active states
	 * @see #getActiveState()
	 */
	public Set<State> getActiveStates();

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

	/**
	 * Is the active state a final state?
	 * 
	 * @return true if at least one of the active states is final otherwise
	 *         false
	 */
	public boolean isInFinalState();
}
