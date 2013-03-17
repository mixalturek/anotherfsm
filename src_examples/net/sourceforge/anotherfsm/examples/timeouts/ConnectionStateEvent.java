/*
 *  Copyright 2013 Michal Turek, AnotherFSM
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

package net.sourceforge.anotherfsm.examples.timeouts;

import net.sourceforge.anotherfsm.Event;

/**
 * Connection state event.
 * 
 * @author Michal Turek
 */
public class ConnectionStateEvent implements Event {
	/** Predefined instance. */
	public static final ConnectionStateEvent instanceCONNECT = new ConnectionStateEvent(
			ConnectionStateEvent.ConnectionState.CONNECT);

	/** Predefined instance. */
	public static final ConnectionStateEvent instanceDISCONNECT = new ConnectionStateEvent(
			ConnectionStateEvent.ConnectionState.DISCONNECT);

	/** The state. */
	private final ConnectionStateEvent.ConnectionState state;

	/**
	 * Create the object.
	 * 
	 * @param state
	 *            the state
	 */
	public ConnectionStateEvent(ConnectionStateEvent.ConnectionState state) {
		this.state = state;
	}

	/**
	 * Get the state.
	 * 
	 * @return the state
	 */
	public ConnectionStateEvent.ConnectionState getState() {
		return state;
	}

	@Override
	public int hashCode() {
		return 86543221 + state.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ConnectionStateEvent))
			return false;

		ConnectionStateEvent other = (ConnectionStateEvent) obj;
		return state.equals(other.state);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" + state + ")";
	}

	/**
	 * The connection state.
	 * 
	 * @author Michal Turek
	 */
	public static enum ConnectionState {
		CONNECT, DISCONNECT
	}
}
