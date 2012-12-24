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
 * Processors of the events based on their type.
 * 
 * @author Michal Turek
 */
class TypeProcessorsImpl implements TypeProcessors {
	/** The procesors. */
	private final Map<Class<? extends Event>, Processor<? extends Event>> processors = new HashMap<Class<? extends Event>, Processor<? extends Event>>();

	/**
	 * Create the object.
	 */
	public TypeProcessorsImpl() {
		// Empty
	}

	@Override
	public <T extends Event> void addProcessor(Class<T> clazz,
			Processor<T> processor) throws FsmException {
		if (clazz == null)
			throw new NullPointerException("Processor class must not be null");

		if (processor == null)
			throw new NullPointerException("Processor must not be null");

		if (processors.containsKey(clazz))
			throw new FsmException("Preprocessor already defined: " + clazz);

		processors.put(clazz, processor);
	}

	// Input event can be whatever event, the processor is searched based on its
	// type. It should always work since addProcessor() does the checks.
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Event process(Event event) {
		if (event == null)
			throw new NullPointerException("Event must not be null");

		Processor processor = processors.get(event.getClass());
		if (processor == null)
			return event;

		return processor.process(event);
	}
}
