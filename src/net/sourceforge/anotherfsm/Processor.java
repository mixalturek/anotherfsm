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
 * The processor of events.
 * 
 * It is a responsibility of the client code to throw no runtime exceptions in
 * callbacks. Any unhandled exception can stop an internal thread and break
 * whole processing of events. It is generally bad to handle all possible
 * exceptions to prevent and hide errors so it is not implemented in the library
 * at all.
 * 
 * @author Michal Turek
 */
public interface Processor<T extends Event> {
	/**
	 * Process (or preprocess) the event before passing it to the state machine.
	 * 
	 * @param event
	 *            the input event
	 * @return the original event a newly generated event or null to ignore the
	 *         event
	 */
	public Event process(T event);
}
