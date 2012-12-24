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
	@Override
	public void onStateEnter(State previous, Event event, State current) {
		// Do nothing by default.
	}

	@Override
	public void onStateExit(State current, Event event, State next) {
		// Do nothing by default.
	}

	@Override
	public void onFinalStateEnter(State previous, Event event, State current) {
		// Do nothing by default.
	}

	@Override
	public void onFinalStateExit(State current, Event event, State next) {
		// Do nothing by default.
	}
}
