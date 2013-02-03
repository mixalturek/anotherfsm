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

import net.sourceforge.anotherfsm.CharacterEvent;
import net.sourceforge.anotherfsm.EqualsPreprocessor;
import net.sourceforge.anotherfsm.Event;
import net.sourceforge.anotherfsm.FsmException;
import net.sourceforge.anotherfsm.OtherEvent;
import net.sourceforge.anotherfsm.Preprocessor;
import net.sourceforge.anotherfsm.State;
import net.sourceforge.anotherfsm.StateAdapter;
import net.sourceforge.anotherfsm.TransitionListener;

/**
 * Listeners of the state machine. This class is more a demonstration of the
 * features than something useful.
 * 
 * @author Michal Turek
 */
public class SearchFsmProcessor extends SearchFsm {
	/**
	 * Create the object.
	 * 
	 * @param name
	 *            the name of the state machine
	 * @throws FsmException
	 *             if building of state machine fails
	 */
	public SearchFsmProcessor(String name) throws FsmException {
		super(name);

		// Create a new preprocessor that is based on equals() method of events
		EqualsPreprocessor preprocessor = new EqualsPreprocessor(name);

		// Don't pass newline characters to the state machine
		preprocessor.addProcessor(CharacterEvent.instance('\n'),
				new Preprocessor.Processor<CharacterEvent>() {
					@Override
					public Event process(CharacterEvent event) {
						// Null stops the event processing in preprocessor
						return null;
					}
				});

		// Register preprocessor to the state machine
		addPreprocessor(preprocessor);

		// Do something on enter of state A
		stateA.addListener(new StateAdapter() {
			@Override
			public void onStateEnter(State previous, Event event, State current) {
				logger.info("Character 'A' entered, good start.");
			}
		});

		// Do something on transition from state R to state F
		trRtoF.addListener(new TransitionListener() {
			@Override
			public void onTransition(State source, Event event,
					State destination) {
				logger.info("Great, nearly done, please continue.");
			}
		});

		// Do something on any transition
		addListener(new TransitionListener() {
			@Override
			public void onTransition(State source, Event event,
					State destination) {
				// State machine tries to process OtherEvent event if no
				// transition matches
				if (event instanceof OtherEvent) {
					logger.info("Oh bad character, let's start again...");
				}
			}
		});

		// Do something on enter of any state
		addListener(new StateAdapter() {
			@Override
			public void onStateEnter(State previous, Event event, State current) {
				// The last state of the string is a final state
				if (current.isFinalState()) {
					logger.info("Whole string successfully entered.");
				}
			}
		});
	}
}
