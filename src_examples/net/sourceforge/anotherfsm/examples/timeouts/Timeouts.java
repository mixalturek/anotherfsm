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

package net.sourceforge.anotherfsm.examples.timeouts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import net.sourceforge.anotherfsm.AnotherFsm;
import net.sourceforge.anotherfsm.FsmException;
import net.sourceforge.anotherfsm.StateMachine;
import net.sourceforge.anotherfsm.logger.FsmLogger;
import net.sourceforge.anotherfsm.logger.StdStreamLoggerFactory;

/**
 * The main class, simple parser of user connect/disconnect commands.
 * 
 * @author Michal Turek
 */
public class Timeouts {
	static {
		// Register factory of loggers before any logger is created
		AnotherFsm.setLoggerFactory(new StdStreamLoggerFactory());
	}

	/** The logger object for this class. */
	private final static FsmLogger logger = AnotherFsm
			.getLogger(Timeouts.class);

	/**
	 * The application start function.
	 * 
	 * @param args
	 *            the input arguments, unused
	 */
	public static void main(String[] args) {
		// The state machine with timeouts
		try (StateMachine machine = new TimeoutConnectionProcessor(
				Timeouts.class.getSimpleName())) {

			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));

			// Building done in the constructor, prepare for events processing
			machine.start();

			while (true) {
				logger.info("Type a command: [exit|connect|disconnect]");

				// Ignore empty commands (enter presses)
				String command = in.readLine();

				while (command.isEmpty())
					command = in.readLine();

				// Exit on 'exit' command
				if ("exit".equals(command)) {
					logger.info("Exiting...");
					break;
				}

				// Create the event
				ConnectionStateEvent.ConnectionState state = null;

				try {
					state = ConnectionStateEvent.ConnectionState
							.valueOf(command.toUpperCase());
				} catch (IllegalArgumentException e) {
					logger.error("Unknown command: " + command);
					continue;
				}

				// Process the event
				machine.process(new ConnectionStateEvent(state));
			}
		} catch (FsmException | IOException e) {
			// Process any exception that may occur
			logger.fatal("Unexpected exception occurred", e);
		}

		logger.debug("End of main()");
	}
}
