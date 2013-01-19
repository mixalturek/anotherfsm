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
 * The adapter for FSM transition callback.
 * 
 * @author Michal Turek
 */
public class StateAdapter implements StateListener {
	/** The type of the listener. */
	private final StateListener.Type type;

	/**
	 * Create the object. The listener will be processed even on loop
	 * transitions.
	 * 
	 * @see StateListener.Type
	 */
	public StateAdapter() {
		this(StateListener.Type.LOOP_PROCESS);
	}

	/**
	 * Create the object.
	 * 
	 * @param type
	 *            the type of the listener
	 */
	public StateAdapter(StateListener.Type type) {
		Helpers.ensureNotNull(type, "type");

		this.type = type;
	}

	@Override
	public StateListener.Type getType() {
		return type;
	}

	@Override
	public void onStateEnter(State previous, Event event, State current) {
		// Do nothing by default.
	}

	@Override
	public void onStateExit(State current, Event event, State next) {
		// Do nothing by default.
	}
}
