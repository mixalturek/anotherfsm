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
 * The deterministic state machine for use in multithreaded environment.
 * 
 * Building of the object is NOT synchronized and should be done just in one
 * thread. Only the processing of the events is synchronized.
 * 
 * @author Michal Turek
 * 
 * @see StateMachine#process(Event)
 * @see StateMachine#getActiveState()
 * @see StateMachine#getActiveStates()
 * @see StateMachine#isInFinalState()
 */
public class SynchronizedStateMachine extends DeterministicStateMachine {
	/**
	 * Create the object.
	 * 
	 * @param name
	 *            the name of the state machine
	 */
	public SynchronizedStateMachine(String name) {
		super(name);
	}

	@Override
	public synchronized Event process(Event event) throws FsmException {
		return super.process(event);
	}

	@Override
	public synchronized State getActiveState() {
		return super.getActiveState();
	}

	@Override
	public synchronized Set<State> getActiveStates() {
		return super.getActiveStates();
	}

	@Override
	public synchronized boolean isInFinalState() {
		return super.isInFinalState();
	}
}
