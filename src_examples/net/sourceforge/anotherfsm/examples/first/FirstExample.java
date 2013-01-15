/*
 *  Copyright 2013 Michal Turek, Another FSM
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
import net.sourceforge.anotherfsm.Event;
import net.sourceforge.anotherfsm.FsmException;
import net.sourceforge.anotherfsm.State;
import net.sourceforge.anotherfsm.StateAdapter;
import net.sourceforge.anotherfsm.logger.FsmLogger;
import net.sourceforge.anotherfsm.logger.StdStreamLoggerFactory;

/**
 * Search "AnotherFSM" string in the input from user.
 * 
 * @author Michal Turek
 */
public class FirstExample {
	static {
		AnotherFsm.setLoggerFactory(new StdStreamLoggerFactory());
	}

	/** The logger object. */
	private final static FsmLogger logger = AnotherFsm
			.getLogger(FirstExample.class);

	/** State machine to search a string. */
	private final SearchFsm machine;

	/**
	 * Execute the application.
	 * 
	 * @param args
	 *            the input arguments, unused
	 */
	public static void main(String[] args) {
		try {
			FirstExample example = new FirstExample();
			example.run();
		} catch (FsmException | IOException e) {
			logger.fatal("Unexpected exception occurred", e);
		}
	}

	/**
	 * Create the object.
	 * 
	 * @throws FsmException
	 *             if building of state machine fails
	 */
	private FirstExample() throws FsmException {
		machine = new SearchFsm();

		machine.stateA.addListener(new StateAdapter() {
			@Override
			public void onStateEnter(State previous, Event event, State current) {
				logger.info("State 'A' entered");
			}
		});

		machine.addListener(new StateAdapter() {
			@Override
			public void onStateEnter(State previous, Event event, State current) {
				if (current.isFinalState())
					logger.info("Final state entered: " + current);
			}
		});
	}

	/**
	 * Execute the processing.
	 * 
	 * @throws IOException
	 *             if reading data from standard input fails
	 * @throws FsmException
	 *             if a state machine operation fails
	 */
	public void run() throws IOException, FsmException {
		machine.start();

		logger.info("Type 'AnotherFSM' string to exit");

		while (true) {
			char c = (char) System.in.read();
			if (c != '\n')
				machine.process(new CharacterEvent(c));

			if (machine.isInFinalState()) {
				logger.debug("Final state entered, breaking the read loop");
				break;
			}
		}
	}
}
