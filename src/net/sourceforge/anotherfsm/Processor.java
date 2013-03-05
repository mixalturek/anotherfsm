/*
 *  Copyright 2012 Michal Turek, AnotherFSM
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

/**
 * Processor of events.
 * 
 * @author Michal Turek
 */
public interface Processor {
	/**
	 * Get the name of the processor.
	 * 
	 * @return the name
	 */
	public String getName();

	/**
	 * Building of the processor is finished, prepare it to the events
	 * processing. It is expected this method is called just once.
	 * 
	 * @see #close()
	 */
	public void start() throws FsmException;

	/**
	 * Finish processing of the events and free all allocated resources. This
	 * method can be called multiple times.
	 * 
	 * Calling methods of the object after its {@link #close()} should be
	 * avoided. The behavior is not defined and may cause unpredictable
	 * behavior, e.g. {@link NullPointerException}.
	 * 
	 * @see #start()
	 */
	public void close();

	/**
	 * Process the event.
	 * 
	 * @param event
	 *            the input event
	 * @return the processed event
	 * @throws FsmException
	 *             if something fails
	 */
	public Event process(Event event) throws FsmException;
}
