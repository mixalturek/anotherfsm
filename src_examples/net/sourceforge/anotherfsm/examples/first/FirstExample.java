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

package net.sourceforge.anotherfsm.examples.first;

import java.io.IOException;

import net.sourceforge.anotherfsm.AnotherFsm;
import net.sourceforge.anotherfsm.CharacterEvent;
import net.sourceforge.anotherfsm.FsmException;
import net.sourceforge.anotherfsm.StateMachine;
import net.sourceforge.anotherfsm.logger.FsmLogger;
import net.sourceforge.anotherfsm.logger.StdStreamLoggerFactory;

/**
 * Search "AnotherFSM" string in the input from user and exit the application
 * after it is entered.
 * 
 * @author Michal Turek
 */
public class FirstExample {
	static {
		// Register factory of loggers before any logger is created
		AnotherFsm.setLoggerFactory(new StdStreamLoggerFactory());
	}

	/** The logger object for this class. */
	private final static FsmLogger logger = AnotherFsm
			.getLogger(FirstExample.class);

	/**
	 * The application start function.
	 * 
	 * @param args
	 *            the input arguments, unused
	 */
	public static void main(String[] args) {
		// Create instance of the state machine
		try (StateMachine machine = new SearchFsmProcessor(
				FirstExample.class.getSimpleName())) {
			// Building done in the constructor, prepare for events processing
			machine.start();

			logger.info("Type 'AnotherFSM' string to exit.");

			while (true) {
				// Read a character from user and pass it to the state machine
				char c = (char) System.in.read();
				machine.process(CharacterEvent.instance(c));

				// Exit after a final state is entered
				if (machine.isInFinalState()) {
					logger.debug("'AnotherFSM' string found in input, exiting");
					break;
				}
			}
		} catch (FsmException | IOException e) {
			// Process any exception that may occur
			logger.fatal("Unexpected exception occurred", e);
		}

		logger.debug("End of main()");
	}
}
