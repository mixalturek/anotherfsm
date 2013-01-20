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

package net.sourceforge.anotherfsm;

/**
 * Adapter of preprocessor, helper class.
 * 
 * @author Michal Turek
 */
abstract class PreprocessorAdapter extends ProcessorAdapter implements
		Preprocessor {
	/**
	 * Create the object.
	 * 
	 * @param name
	 *            the name of the event processor
	 */
	public PreprocessorAdapter(String name) {
		super(name);
	}

	// The input event can be whatever event, the processor is searched based on
	// information from it. It should always work since findProcessor() does
	// checks.
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Event process(Event event) throws FsmException {
		Helpers.ensureNotNull(event, "event");

		Event preprocessedEvent = preprocessEvent(event);
		if (preprocessedEvent == null)
			return null;

		Processor processor = findProcessor(event);
		if (processor == null)
			return event;

		Event resultEvent = null;

		try {
			resultEvent = processor.process(event);
		} catch (RuntimeException e) {
			Helpers.logExceptionInClientCallback(logger, e, event);
			throw e;
		}

		if (!event.equals(resultEvent) && logger.isInfoEnabled()) {
			logger.info("Event preprocessed: " + event + Transition.TR
					+ resultEvent);
		}

		return resultEvent;
	}

	/**
	 * Find appropriate processor for an event.
	 * 
	 * @param event
	 *            the event
	 * @return the processor or null if the processor was not found
	 */
	// The input event can have whatever type
	@SuppressWarnings("rawtypes")
	abstract protected Preprocessor.Processor findProcessor(Event event);
}
