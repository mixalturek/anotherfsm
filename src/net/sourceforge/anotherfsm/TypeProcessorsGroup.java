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
 * Group of processors based on type.
 * 
 * @author Michal Turek
 */
public interface TypeProcessorsGroup {
	/**
	 * Add a new processor. The method is not thread safe.
	 * 
	 * @param processor
	 *            the preprocessor
	 * @throws FsmException
	 *             if the preprocessor is already defined
	 */
	public void addProcessor(Class<? extends Event> clazz,
			Processor<Event> processor) throws FsmException;

	/**
	 * Remove the processor. The method is not thread safe.
	 * 
	 * @param preprocessor
	 *            the preprocessor
	 * @return true if the preprocessor was defined and removed
	 */
	public boolean removeProcessor(Class<? extends Event> clazz);

	/**
	 * Process (or preprocess) the event before passing it to the state machine.
	 * 
	 * @param event
	 *            the input event
	 * @return the original event a newly generated event or null to ignore the
	 *         event
	 */
	public Event process(Event event);
}
