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

import net.sourceforge.anotherfsm.logger.FsmLogger;

/**
 * Processors of the events based on their type. The object can be used in
 * multithreaded environment.
 * 
 * Building of the object is NOT synchronized and should be done just in one
 * thread. Only the processing of the events is synchronized.
 * 
 * @author Michal Turek
 */
class TypeProcessorsImpl implements TypeProcessors {
	/** The logger. */
	protected final FsmLogger logger;

	/** The name of the state machine. */
	private final String name;

	/** The procesors. */
	private final Map<Class<? extends Event>, Processor<? extends Event>> processors = new HashMap<Class<? extends Event>, Processor<? extends Event>>();

	/**
	 * Create the object.
	 * 
	 * @param name
	 *            the name of the event processor
	 */
	public TypeProcessorsImpl(String name) {
		if (name == null)
			throw new NullPointerException("Name must not be null");

		this.name = name;
		logger = AnotherFsm.getInstance().getLogger(getClass(), name);
	}

	@Override
	public String getName() {
		return name;
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

		try {
			Event resultEvent = processor.process(event);

			if (!event.equals(resultEvent) && logger.isInfoEnabled()) {
				logger.info("Event processed: " + event + Transition.TR
						+ resultEvent);
			}

			return resultEvent;
		} catch (RuntimeException e) {
			// Log everything what is possible and re-throw the exception,
			// current thread may stop but it is better than hide a more
			// serious error

			logger.fatal(
					"Unexpected exception occurred probably in client callback code: event "
							+ event + ", thread "
							+ Thread.currentThread().getName() + ", exception "
							+ e.getClass() + ", exception message "
							+ e.getMessage() + ", exception " + e, e);

			throw e;
		}
	}
}
