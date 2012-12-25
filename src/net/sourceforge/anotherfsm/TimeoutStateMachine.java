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
 * Deterministic state machine with timeouts for states. The object is thread
 * safe and can be used in multithreaded environment.
 * 
 * Building of the object is NOT synchronized and should be done just in one
 * thread. Only the processing of the events is synchronized.
 * 
 * Note creating of this object also executes an additional thread for a timer
 * and generating of timeout events. Code of callbacks/listeners for timeout
 * events is executed under this thread.
 * 
 * @author Michal Turek
 * 
 * @see AnotherFsm#genTimeoutEvent(long, TimeoutEvent.Type)
 * @see AnotherFsm#genTimeoutStateMachine(String)
 */
// TODO: check the behavior with zero timeout
class TimeoutStateMachine extends SynchronizedStateMachine {

	/**
	 * Create the state machine.
	 * 
	 * @param name
	 *            the name of the state machine
	 */
	public TimeoutStateMachine(String name) {
		super(name);
	}

	// TODO: implementation
}
