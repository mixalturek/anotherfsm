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

import java.util.HashMap;
import java.util.Map;

/**
 * Processors of the events based on their type. The object can be used in
 * multithreaded environment.
 * 
 * Building of the object is NOT synchronized and should be done just in one
 * thread. Only the processing of the events is thread safe.
 * 
 * @author Michal Turek
 */
public class TypePreprocessor extends PreprocessorAdapter {
	/** The procesors. */
	private final Map<Class<? extends Event>, Processor<? extends Event>> processors = new HashMap<Class<? extends Event>, Processor<? extends Event>>();

	/**
	 * Create the object.
	 * 
	 * @param name
	 *            the name of the event processor
	 */
	public TypePreprocessor(String name) {
		super(name);
	}

	/**
	 * Add a new processor. The method is not thread safe.
	 * 
	 * @param clazz
	 *            the type of event
	 * @param processor
	 *            the processor
	 * @throws FsmException
	 *             if the processor is already defined
	 */
	public <T extends Event> void addProcessor(Class<T> clazz,
			Processor<T> processor) throws FsmException {
		Helpers.ensureNotNull(clazz, "class");
		Helpers.ensureNotNull(processor, "processor");

		if (processors.containsKey(clazz))
			throw new FsmException("Preprocessor already defined: " + clazz);

		processors.put(clazz, processor);
	}

	// The input event can have whatever type
	@SuppressWarnings("rawtypes")
	@Override
	protected Preprocessor.Processor findProcessor(Event event) {
		Helpers.ensureNotNull(event, "event");

		return processors.get(event.getClass());
	}
}
