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

import java.util.LinkedList;
import java.util.List;

import net.sourceforge.anotherfsm.logger.FsmLogger;

/**
 * Adapter of processor, helper class.
 * 
 * @author Michal Turek
 */
abstract class ProcessorAdapter implements Processor {
	/** The logger. */
	protected final FsmLogger logger;

	/** The name of the preprocessor. */
	private final String name;

	/** The preprocessors of events. */
	private final List<Preprocessor> preprocessors = new LinkedList<Preprocessor>();

	/**
	 * Create the object.
	 * 
	 * @param name
	 *            the name of the event processor
	 */
	public ProcessorAdapter(String name) {
		Helpers.ensureNotNull(name, "name");

		this.name = name;
		logger = AnotherFsm.getLogger(getClass(), name);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void addPreprocessor(Preprocessor preprocessor) {
		Helpers.ensureNotNull(preprocessor, "preprocessor");

		preprocessors.add(preprocessor);
	}

	/**
	 * Prepare this object and recursively all registered preprocessors for
	 * processing of the events.
	 */
	@Override
	public void start() throws FsmException {
		for (Preprocessor preprocessor : preprocessors)
			preprocessor.start();
	}

	/**
	 * Finish processing of the events and free all allocated resources in this
	 * object and recursively in all registered preprocessors.
	 */
	@Override
	public void close() {
		for (Preprocessor preprocessor : preprocessors)
			preprocessor.close();
	}

	/**
	 * Preprocess event using all registered preprocessors (recursive). Helper
	 * method.
	 * 
	 * @param event
	 *            the event
	 * @return the original event, a newly generated event or null to ignore the
	 *         event
	 * @throws FsmException
	 *             if something fails
	 */
	protected Event preprocessEvent(Event event) throws FsmException {
		Helpers.ensureNotNull(event, "event");

		Event preprocessedEvent = event;

		for (Preprocessor preprocessor : preprocessors) {
			preprocessedEvent = preprocessor.process(preprocessedEvent);

			if (preprocessedEvent == null)
				return null;
		}

		return preprocessedEvent;
	}
}
