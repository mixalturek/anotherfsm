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
 * Factory of classes in this package.
 * 
 * @author Michal Turek
 */
public abstract class AnotherFsm {
	/**
	 * Forbid creating of objects of this class.
	 */
	private AnotherFsm() {
		// Do nothing
	}

	/**
	 * Create an instance of event processor based on the type. The returned
	 * object can be used in multithreaded environment.
	 * 
	 * Building of the object is NOT synchronized and should be done just in one
	 * thread. Only the processing of the events is synchronized.
	 * 
	 * @return the event processors
	 */
	public static TypeProcessors genTypeProcessors() {
		return new TypeProcessorsImpl();
	}

	/**
	 * Create an instance of deterministic state machine. The returned object is
	 * NOT thread safe.
	 * 
	 * @param name
	 *            the name of the state machine
	 * @return the created state machine
	 */
	public static StateMachine genDeterministicStateMachine(String name) {
		return new DeterministicStateMachine(name);
	}

	/**
	 * Create an instance of deterministic state machine for use in
	 * multithreaded environment.
	 * 
	 * Building of the object is NOT synchronized and should be done just in one
	 * thread. Only the processing of the events is synchronized.
	 * 
	 * @param name
	 *            the name of the state machine
	 * @return the created state machine
	 * 
	 * @see StateMachine#process(Event)
	 * @see StateMachine#getActiveState()
	 * @see StateMachine#getActiveStates()
	 */
	public static StateMachine genSynchronizedStateMachine(String name) {
		return new SynchronizedStateMachine(name);
	}
}
