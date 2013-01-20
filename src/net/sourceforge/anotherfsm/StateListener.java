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

/**
 * The listener for state changes.
 * 
 * It is a responsibility of the client code to throw no runtime exceptions in
 * callbacks. Any unhandled exception can stop an internal thread and break
 * whole processing of events. It is generally bad to handle all possible
 * exceptions to prevent and hide errors so it is not implemented in the library
 * at all.
 * 
 * @author Michal Turek
 */
public interface StateListener {
	/**
	 * Get the listener type.
	 * 
	 * @return the type
	 */
	public StateListener.Type getType();

	/**
	 * The state was entered.
	 * 
	 * @param previous
	 *            the previous state
	 * @param event
	 *            the event
	 * @param current
	 *            the current state
	 */
	public void onStateEnter(State previous, Event event, State current);

	/**
	 * The state was exited.
	 * 
	 * @param current
	 *            the current state
	 * @param event
	 *            the event
	 * @param next
	 *            the next state
	 */
	public void onStateExit(State current, Event event, State next);

	/**
	 * The type of state listener to specify the behavior on loop transition.
	 * The state is not changed during such transition.
	 * 
	 * @author Michal Turek
	 * 
	 * @see Transition#Transition(State, Event)
	 */
	public static enum Type {
		/** Process the listener on loop transition. */
		LOOP_PROCESS,

		/** Don't process the listener on loop transition. */
		LOOP_NO_PROCESS
	}
}
