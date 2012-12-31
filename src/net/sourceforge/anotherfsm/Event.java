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
 * Abstract event that causes the FSM transitions.
 * 
 * @author Michal Turek
 */
public interface Event {

	@Override
	public int hashCode();

	/**
	 * Compare the objects using internal fields. FSM uses this method while
	 * determining which transition process.
	 * 
	 * @param object
	 *            the object
	 * @return true if the objects are same, otherwise false
	 * @see StateMachine#process(Event)
	 */
	@Override
	public boolean equals(Object object);

	/**
	 * The string representation of the object. It is expected the class name
	 * and all fields used in equals() are listed.
	 * 
	 * @return the string representation
	 */
	@Override
	public String toString();
}
