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
 * The listener for FSM transition callback.
 * 
 * @author Michal Turek
 */
public interface StateListener {
	/**
	 * The state was entered.
	 * 
	 * Always compare the previous and current state if the state contains a
	 * loop transition to check this is real state enter from a different state.
	 * 
	 * @param previous
	 *            the previous state
	 * @param event
	 *            the event
	 * @param current
	 *            the current state
	 * 
	 * @see LoopTransition
	 */
	public void onStateEnter(State previous, Event event, State current);

	/**
	 * The state was exited.
	 * 
	 * Always compare the current and next state if the state contains a loop
	 * transition to check this is real state exit to a different state.
	 * 
	 * @param current
	 *            the current state
	 * @param event
	 *            the event
	 * @param next
	 *            the next state
	 * 
	 * @see LoopTransition
	 */
	public void onStateExit(State current, Event event, State next);

	/**
	 * The final state was entered.
	 * 
	 * Always compare the previous and current state if the state contains a
	 * loop transition to check this is real state enter from a different state.
	 * 
	 * @param previous
	 *            the previous state
	 * @param event
	 *            the event
	 * @param current
	 *            the current state
	 * 
	 * @see LoopTransition
	 */
	public void onFinalStateEnter(State previous, Event event, State current);

	/**
	 * The final state was exited.
	 * 
	 * Always compare the current and next state if the state contains a loop
	 * transition to check this is real state exit to a different state.
	 * 
	 * @param current
	 *            the current state
	 * @param event
	 *            the event
	 * @param next
	 *            the next state
	 * 
	 * @see LoopTransition
	 */
	public void onFinalStateExit(State current, Event event, State next);
}
