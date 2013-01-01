/*
 *  Copyright 2013 Michal Turek, Another FSM
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
 * Preprocessor of events.
 * 
 * @author Michal Turek
 */
public interface EventPreprocessor extends EventProcessor {
	/**
	 * Process or preprocess the event.
	 * 
	 * @param event
	 *            the input event
	 * @return the original event, a newly generated event or null to stop the
	 *         processing
	 * @throws FsmException
	 *             if something fails
	 */
	@Override
	public Event process(Event event) throws FsmException;
}
