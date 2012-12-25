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
 * The other event is processed if no other event match and this transition is
 * defined for the source state.
 * 
 * @author Michal Turek
 * @see AnotherFsm#genOtherEvent()
 */
public interface OtherEvent extends Event {
	/**
	 * Get the source event that caused this transition.
	 * 
	 * @return the sourceEvent the source event
	 */
	public Event getSourceEvent();
}
