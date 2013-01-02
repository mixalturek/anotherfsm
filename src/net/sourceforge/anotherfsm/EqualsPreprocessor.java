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

import java.util.HashMap;
import java.util.Map;

/**
 * Processors of the events based on equals method. The object can be used in
 * multithreaded environment.
 * 
 * Building of the object is NOT synchronized and should be done just in one
 * thread. Only the processing of the events is thread safe.
 * 
 * @author Michal Turek
 */
public class EqualsPreprocessor extends PreprocessorAdapter {
	/** The procesors. */
	private final Map<Event, Processor<? extends Event>> processors = new HashMap<Event, Processor<? extends Event>>();

	/**
	 * Create the object.
	 * 
	 * @param name
	 *            the name of the event processor
	 */
	public EqualsPreprocessor(String name) {
		super(name);
	}

	/**
	 * Add a new processor. The method is not thread safe.
	 * 
	 * @param event
	 *            the event
	 * @param processor
	 *            the processor
	 * @throws FsmException
	 *             if the processor is already defined
	 */
	public <T extends Event> void addProcessor(T event, Processor<T> processor)
			throws FsmException {
		if (event == null)
			throw new NullPointerException("Event must not be null");

		if (processor == null)
			throw new NullPointerException("Processor must not be null");

		if (processors.containsKey(event))
			throw new FsmException("Preprocessor already defined: " + event);

		processors.put(event, processor);
	}

	// The input event can have whatever type
	@SuppressWarnings("rawtypes")
	@Override
	protected Preprocessor.Processor findProcessor(Event event) {
		return processors.get(event);
	}
}
