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
 * This event is processed if no other event match and this transition is
 * defined.
 * 
 * @author Michal Turek
 */
public class OtherEvent extends TypeEvent {
	/** The instance of the object for building of the state machine. */
	public static final OtherEvent INSTANCE = new OtherEvent(null);

	/** The source event that caused this transition. */
	private final Event sourceEvent;

	/**
	 * Create the object. Internal use only. For internal use while processing
	 * the transitions.
	 * 
	 * @param sourceEvent
	 *            the source event that caused this transition
	 */
	OtherEvent(Event sourceEvent) {
		this.sourceEvent = sourceEvent;
	}

	/**
	 * Get the source event that caused this transition.
	 * 
	 * @return the sourceEvent the source event
	 */
	public Event getSourceEvent() {
		return sourceEvent;
	}

	// Don't define hashCode() and equals(), this is only a container for source
	// event.

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" + sourceEvent + ")";
	}
}
