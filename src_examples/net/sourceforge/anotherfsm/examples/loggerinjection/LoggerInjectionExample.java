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

package net.sourceforge.anotherfsm.examples.loggerinjection;

import net.sourceforge.anotherfsm.AnotherFsm;
import net.sourceforge.anotherfsm.ContainerEvent;
import net.sourceforge.anotherfsm.DeterministicStateMachine;
import net.sourceforge.anotherfsm.FsmException;
import net.sourceforge.anotherfsm.State;
import net.sourceforge.anotherfsm.StateMachine;
import net.sourceforge.anotherfsm.Transition;
import net.sourceforge.anotherfsm.logger.FsmLogger;

import org.apache.log4j.BasicConfigurator;

/**
 * Log a message using log4j library.
 * 
 * @author Michal Turek
 */
public class LoggerInjectionExample {
	static {
		// Register factory of loggers before any logger is created
		AnotherFsm.setLoggerFactory(new Log4jLoggerFactory());

		// Configure log4j somehow, this may be at the beginning of main()
		BasicConfigurator.configure();
	}

	/** The logger object for this class. */
	private final static FsmLogger logger = AnotherFsm
			.getLogger(LoggerInjectionExample.class);

	/**
	 * The application start function.
	 * 
	 * @param args
	 *            the input arguments, unused
	 */
	public static void main(String[] args) {
		// Log something using the log4j wrapper
		logger.info("Hello world.");

		// Create state machine to demonstrate that log4j is used internally
		try (StateMachine machine = new DeterministicStateMachine("test")) {
			State state = new State("state");
			Transition transition = new Transition(state,
					new ContainerEvent<String>("event"));

			machine.addState(state);
			machine.addTransition(transition);
			machine.setStartState(state);

			// Messages should be logged using log4j here
			machine.start();
			machine.process(new ContainerEvent<String>("event"));
		} catch (FsmException e) {
			// Process any exception that may occur
			logger.fatal("Unexpected exception occurred", e);
		}

		logger.debug("End of main()");
	}
}
