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
 * Event that contains another (whatever) object. The stored object is used for
 * comparison while processing the events too.
 * 
 * @author Michal Turek
 */
public class ContainerEvent<T> implements Event {
	/** The stored object. */
	private final T storedObject;

	/**
	 * Create the object.
	 * 
	 * The internal state of the stored object must be unmodifiable or never
	 * modified to verify the state machine works correctly. The behavior and
	 * return values of its hashCode() and equals() must remain unchanged after
	 * possible object modification. Completely unmodifiable objects with all
	 * member variables final are recommended.
	 * 
	 * @param storedObject
	 *            the object to store
	 */
	public ContainerEvent(T storedObject) {
		Helpers.ensureNotNull(storedObject, "stored object");

		this.storedObject = storedObject;
	}

	/**
	 * Get the stored object.
	 * 
	 * @return the stored object
	 */
	public T getStoredObject() {
		return storedObject;
	}

	@Override
	public int hashCode() {
		return 86545265 + storedObject.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ContainerEvent))
			return false;

		// Other can contain object with whatever type
		@SuppressWarnings("rawtypes")
		ContainerEvent other = (ContainerEvent) obj;
		return storedObject.equals(other.storedObject);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" + storedObject + ")";
	}
}
