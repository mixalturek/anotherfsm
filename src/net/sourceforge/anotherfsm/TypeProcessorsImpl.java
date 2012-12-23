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
public class TypeProcessorsImpl implements TypeProcessors {
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
		if (processors.containsKey(clazz))
			throw new FsmException("Preprocessor already defined: " + clazz);

		processors.put(clazz, processor);
	}

	@Override
	public Event process(Event event) {
		if (event == null)
			return null;

		// FIXME: Don't know how to solve the compiler error without raw type
		// It should always work, since addProcessor() does the checks
		@SuppressWarnings("unchecked")
		Processor<Event> processor = (Processor<Event>) processors.get(event
				.getClass());
		if (processor == null)
			return event;

		return processor.process(event);
	}
}
