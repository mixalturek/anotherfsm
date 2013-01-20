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
 * Simple implementation of StateListener for use in JUnit tests.
 * 
 * @author Michal Turek
 */
public class StateListenerImpl implements StateListener {
	/** The type of the listener. */
	private final StateListener.Type type;

	public int enteredNum = 0;
	public int exitedNum = 0;

	/**
	 * Create the object.
	 * 
	 * @param type
	 *            the type of the listener
	 */
	public StateListenerImpl(StateListener.Type type) {
		this.type = type;
	}

	@Override
	public StateListener.Type getType() {
		return type;
	}

	@Override
	public void onStateEnter(State previous, Event event, State current) {
		++enteredNum;
	}

	@Override
	public void onStateExit(State current, Event event, State next) {
		++exitedNum;
	}
}
