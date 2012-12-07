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

package net.sourceforge.anotherfsm.deterministic;

import java.util.HashMap;
import java.util.Map;

import net.sourceforge.anotherfsm.api.Event;
import net.sourceforge.anotherfsm.api.FsmException;
import net.sourceforge.anotherfsm.api.Processor;
import net.sourceforge.anotherfsm.api.ProcessorGroup;

/**
 * Preprocessors of the events based on their type.
 * 
 * @author Michal Turek
 */
public class TypePreprocessors implements ProcessorGroup {
	/** The procesors. */
	private final Map<Class<? extends Event>, Processor<Event>> processors = new HashMap<Class<? extends Event>, Processor<Event>>();

	/**
	 * Create the object.
	 */
	public TypePreprocessors() {
		// Empty
	}

	@Override
	public void addProcessor(Class<? extends Event> clazz,
			Processor<Event> processor) throws FsmException {
		if (processors.containsKey(clazz))
			throw new FsmException("Preprocessor has been already defined: "
					+ clazz);

		processors.put(clazz, processor);
	}

	@Override
	public boolean removeProcessor(Class<? extends Event> clazz) {
		return processors.remove(clazz) != null;
	}

	@Override
	public Event process(Event event) {
		if (event == null)
			return null;

		Processor<Event> processor = processors.get(event.getClass());
		if (processor == null)
			return event;

		return processor.process(event);
	}
}
