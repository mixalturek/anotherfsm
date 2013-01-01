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
 * Processor of events.
 * 
 * @author Michal Turek
 */
public interface EventProcessor {
	/**
	 * Get the name of the processor.
	 * 
	 * @return the name
	 */
	public String getName();

	/**
	 * Add a new preprocessor. The preprocessors will be called in the
	 * registration order during preprocessing of the events.
	 * 
	 * @param preprocessor
	 *            the preprocessor
	 * 
	 * @see #process(Event)
	 */
	public void addPreprocessor(EventPreprocessor preprocessor);

	/**
	 * Process the event.
	 * 
	 * @param event
	 *            the input event
	 * @return the processed event
	 * @throws FsmException
	 *             if something fails
	 */
	public Event process(Event event) throws FsmException;
}
