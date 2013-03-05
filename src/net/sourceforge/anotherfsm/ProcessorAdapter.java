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
	public void start() throws FsmException {
		// Do nothing by default
	}

	@Override
	public void close() {
		// Do nothing by default
	}
}
