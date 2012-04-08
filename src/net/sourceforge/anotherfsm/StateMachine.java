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
 * Abstract state machine
 * 
 * @author Michal Turek
 */
public interface StateMachine {

	/**
	 * Set the start state. This is the last step to build the state machine, it
	 * will be able to process the incoming events after this call finishes.
	 * 
	 * @param state
	 *            the state
	 * @see #processEvent(Event)
	 */
	public void setStartState(State state) throws FsmException;

	/**
	 * Process an event.
	 * 
	 * @param event
	 *            the event
	 * @throws FsmException
	 *             If something fails.
	 */
	public void processEvent(Event event) throws FsmException;

	/**
	 * Start processing of the events, building of the state machine finished.
	 */
	public void start();

	/**
	 * Stop procession of the events, free all allocated resources.
	 */
	public void stop();

	/**
	 * The string representation of the object. It is expected the name of the
	 * state machine will be returned (if defined).
	 * 
	 * @return the string representation
	 */
	@Override
	public String toString();
}
