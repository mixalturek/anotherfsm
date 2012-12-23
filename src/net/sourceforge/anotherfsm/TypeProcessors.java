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
public interface TypeProcessors {
	/**
	 * Add a new processor. The method is not thread safe.
	 * 
	 * @param clazz
	 *            the type of event
	 * @param processor
	 *            the preprocessor
	 * @throws FsmException
	 *             if the preprocessor is already defined
	 */
	public <T extends Event> void addProcessor(Class<T> clazz,
			Processor<T> processor) throws FsmException;

	/**
	 * Process (or preprocess) the event before passing it to the state machine.
	 * 
	 * @param event
	 *            the input event
	 * @return the original event a newly generated event or null to ignore the
	 *         event
	 * @throws FsmException
	 *             if something fails
	 */
	public Event process(Event event) throws FsmException;
}