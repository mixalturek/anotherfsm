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
import java.util.LinkedList;
import java.util.List;
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
public class TypePreprocessor implements Preprocessor {
	/** The logger. */
	protected final FsmLogger logger;

	/** The name of the state machine. */
	private final String name;

	/** The procesors. */
	private final Map<Class<? extends Event>, Processor<? extends Event>> processors = new HashMap<Class<? extends Event>, Processor<? extends Event>>();

	/** The preprocessors of events. */
	private final List<Preprocessor> preprocessors = new LinkedList<Preprocessor>();

	/**
	 * Create the object.
	 * 
	 * @param name
	 *            the name of the event processor
	 */
	public TypePreprocessor(String name) {
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
	public void addPreprocessor(Preprocessor preprocessor) {
		preprocessors.add(preprocessor);
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
	public Event process(Event event) throws FsmException {
		if (event == null)
			throw new NullPointerException("Event must not be null");

		Event preprocessedEvent = preprocessEvent(event);
		if (NullEvent.INSTANCE.equals(preprocessedEvent))
			return preprocessedEvent;

		Processor processor = processors.get(event.getClass());
		if (processor == null)
			return event;

		Event resultEvent = null;

		try {
			resultEvent = processor.process(event);
		} catch (RuntimeException e) {
			AnotherFsm.getInstance().logExceptionInClientCallback(logger, e,
					event);
			throw e;
		}

		if (!event.equals(resultEvent) && logger.isInfoEnabled()) {
			logger.info("Event processed: " + event + Transition.TR
					+ resultEvent);
		}

		return resultEvent;
	}

	/**
	 * Preprocess event using all registered preprocessors (recursive).
	 * 
	 * @param event
	 *            the event
	 * @return the original event a newly generated event or NullEvent to ignore
	 *         the event
	 * @throws FsmException
	 *             if something fails
	 */
	private Event preprocessEvent(Event event) throws FsmException {
		Event preprocessedEvent = event;

		for (Preprocessor preprocessor : preprocessors) {
			preprocessedEvent = preprocessor.process(event);

			if (NullEvent.INSTANCE.equals(preprocessedEvent))
				return preprocessedEvent;
		}

		return preprocessedEvent;
	}

	/**
	 * The real preprocessor of events.
	 * 
	 * It is a responsibility of the client code to throw no runtime exceptions
	 * in callbacks. Any unhandled exception can stop an internal thread and
	 * break whole processing of events. It is generally bad to handle all
	 * possible exceptions to prevent and hide errors so it is not implemented
	 * in the library at all.
	 * 
	 * @author Michal Turek
	 */
	public static interface Processor<T extends Event> {
		/**
		 * Preprocess the event before passing it to the state machine.
		 * 
		 * @param event
		 *            the input event
		 * @return the input event, a newly generated event or NullEvent to
		 *         ignore the event
		 * @see NullEvent
		 */
		public Event process(T event);
	}
}
