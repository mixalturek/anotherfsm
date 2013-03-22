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

/**
 * Group of processors. The incoming event will be passed to all of them in the
 * order of their registrations.
 * 
 * @author Michal Turek
 */
public class ProcesorGroup implements Processor {
	/** The inner processors. */
	List<Processor> processors = new LinkedList<Processor>();

	/**
	 * Create the object.
	 */
	public ProcesorGroup() {
		// Empty
	}

	@Override
	public String getName() {
		StringBuilder builder = new StringBuilder();
		builder.append(getClass().getSimpleName() + "(");

		for (Processor processor : processors)
			builder.append(processor.getName() + ", ");

		builder.append(")");
		return builder.toString();
	}

	@Override
	public void start() throws FsmException {
		for (Processor processor : processors)
			processor.start();
	}

	@Override
	public void close() {
		for (Processor processor : processors)
			processor.close();
	}

	@Override
	public Event process(Event event) throws FsmException {
		for (Processor processor : processors)
			processor.process(event);

		return event;
	}

	public void addProcessor(Processor processor) {
		processors.add(processor);
	}
}
