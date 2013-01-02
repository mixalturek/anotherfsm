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

import java.io.Closeable;
import java.util.Set;

/**
 * Abstract state machine
 * 
 * @author Michal Turek
 * 
 * @see AnotherFsm#genDeterministicStateMachine(String)
 * @see AnotherFsm#genSynchronizedStateMachine(String)
 * @see AnotherFsm#genTimeoutStateMachine(String)
 */
public interface StateMachine extends Processor, Closeable {
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
	 * Start processing of the events, building of the state machine is
	 * finished.
	 * 
	 * @throws FsmException
	 *             if something fails
	 * @see StartEvent
	 * @see #close()
	 */
	public void start() throws FsmException;

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
