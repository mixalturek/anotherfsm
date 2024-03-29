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

package net.sourceforge.anotherfsm.examples.qfsm;

import net.sourceforge.anotherfsm.CharacterEvent;
import net.sourceforge.anotherfsm.EqualsPreprocessor;
import net.sourceforge.anotherfsm.Event;
import net.sourceforge.anotherfsm.FsmException;
import net.sourceforge.anotherfsm.OtherEvent;
import net.sourceforge.anotherfsm.Preprocessor;
import net.sourceforge.anotherfsm.State;
import net.sourceforge.anotherfsm.StateAdapter;
import net.sourceforge.anotherfsm.StateListener;
import net.sourceforge.anotherfsm.TransitionListener;

/**
 * State machine to search hard coded "AnotherFSM" string.
 * 
 * <p>
 * This file was generated using AnotherFSM CodeGenerator:<br />
 * {@code java -classpath anotherfsm-0.2.0-dev.jar net.sourceforge.anotherfsm.qfsm.CodeGenerator --force --config-file SearchString.xml --qfsm-file SearchString.fsm}
 * </p>
 * 
 * @author Michal Turek
 * @version 0.1.0
 */
class SearchStringProcessor extends SearchStringFsm {
	/**
	 * Create the object, define and connect listeners.
	 * 
	 * @param name
	 *            the name of the state machine
	 * @throws FsmException
	 *             if building of state machine fails
	 */
	public SearchStringProcessor(String name) throws FsmException {
		super(name);

		EqualsPreprocessor equalsPreprocessor = new EqualsPreprocessor(name);

		// Don't pass newline characters to the state machine
		equalsPreprocessor.addProcessor(CharacterEvent.instance('\n'),
				new Preprocessor.Processor<CharacterEvent>() {
					@Override
					public Event process(CharacterEvent event) {
						// Null stops the event processing in preprocessor
						return null;
					}
				});

		addPreprocessor(equalsPreprocessor);

		addListener(new StateAdapter(StateListener.Type.LOOP_NO_PROCESS) {
			@Override
			public void onStateEnter(State previous, Event event, State current) {
				// The last state of the string is a final state
				if (current.isFinalState()) {
					logger.info("Whole string successfully entered.");
				}
			}

			@Override
			public void onStateExit(State current, Event event, State next) {
				// TODO Auto-generated method stub
			}
		});

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

		stateA.addListener(new StateAdapter(StateListener.Type.LOOP_NO_PROCESS) {
			@Override
			public void onStateEnter(State previous, Event event, State current) {
				logger.info("Character 'A' entered, good start.");
			}

			@Override
			public void onStateExit(State current, Event event, State next) {
				// TODO Auto-generated method stub
			}
		});

		stateE.addListener(new StateAdapter(StateListener.Type.LOOP_NO_PROCESS) {
			@Override
			public void onStateEnter(State previous, Event event, State current) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onStateExit(State current, Event event, State next) {
				// TODO Auto-generated method stub
			}
		});

		stateF.addListener(new StateAdapter(StateListener.Type.LOOP_NO_PROCESS) {
			@Override
			public void onStateEnter(State previous, Event event, State current) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onStateExit(State current, Event event, State next) {
				// TODO Auto-generated method stub
			}
		});

		stateH.addListener(new StateAdapter(StateListener.Type.LOOP_NO_PROCESS) {
			@Override
			public void onStateEnter(State previous, Event event, State current) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onStateExit(State current, Event event, State next) {
				// TODO Auto-generated method stub
			}
		});

		stateM.addListener(new StateAdapter(StateListener.Type.LOOP_NO_PROCESS) {
			@Override
			public void onStateEnter(State previous, Event event, State current) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onStateExit(State current, Event event, State next) {
				// TODO Auto-generated method stub
			}
		});

		stateN.addListener(new StateAdapter(StateListener.Type.LOOP_NO_PROCESS) {
			@Override
			public void onStateEnter(State previous, Event event, State current) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onStateExit(State current, Event event, State next) {
				// TODO Auto-generated method stub
			}
		});

		stateO.addListener(new StateAdapter(StateListener.Type.LOOP_NO_PROCESS) {
			@Override
			public void onStateEnter(State previous, Event event, State current) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onStateExit(State current, Event event, State next) {
				// TODO Auto-generated method stub
			}
		});

		stateR.addListener(new StateAdapter(StateListener.Type.LOOP_NO_PROCESS) {
			@Override
			public void onStateEnter(State previous, Event event, State current) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onStateExit(State current, Event event, State next) {
				// TODO Auto-generated method stub
			}
		});

		stateS.addListener(new StateAdapter(StateListener.Type.LOOP_NO_PROCESS) {
			@Override
			public void onStateEnter(State previous, Event event, State current) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onStateExit(State current, Event event, State next) {
				// TODO Auto-generated method stub
			}
		});

		stateStart.addListener(new StateAdapter(
				StateListener.Type.LOOP_NO_PROCESS) {
			@Override
			public void onStateEnter(State previous, Event event, State current) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onStateExit(State current, Event event, State next) {
				// TODO Auto-generated method stub
			}
		});

		stateT.addListener(new StateAdapter(StateListener.Type.LOOP_NO_PROCESS) {
			@Override
			public void onStateEnter(State previous, Event event, State current) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onStateExit(State current, Event event, State next) {
				// TODO Auto-generated method stub
			}
		});

		stateA_CharacterEvent_instancen_stateN
				.addListener(new TransitionListener() {
					@Override
					public void onTransition(State source, Event event,
							State destination) {
						// The following code is generated, don't edit it
						// directly

					}
				});

		stateA_OtherEvent_instance_stateStart
				.addListener(new TransitionListener() {
					@Override
					public void onTransition(State source, Event event,
							State destination) {
						// The following code is generated, don't edit it
						// directly

					}
				});

		stateE_CharacterEvent_instancer_stateR
				.addListener(new TransitionListener() {
					@Override
					public void onTransition(State source, Event event,
							State destination) {
						// The following code is generated, don't edit it
						// directly

					}
				});

		stateE_OtherEvent_instance_stateStart
				.addListener(new TransitionListener() {
					@Override
					public void onTransition(State source, Event event,
							State destination) {
						// The following code is generated, don't edit it
						// directly

					}
				});

		stateF_CharacterEvent_instanceS_stateS
				.addListener(new TransitionListener() {
					@Override
					public void onTransition(State source, Event event,
							State destination) {
						// The following code is generated, don't edit it
						// directly

					}
				});

		stateF_OtherEvent_instance_stateStart
				.addListener(new TransitionListener() {
					@Override
					public void onTransition(State source, Event event,
							State destination) {
						// The following code is generated, don't edit it
						// directly

					}
				});

		stateH_CharacterEvent_instancee_stateE
				.addListener(new TransitionListener() {
					@Override
					public void onTransition(State source, Event event,
							State destination) {
						// The following code is generated, don't edit it
						// directly

					}
				});

		stateH_OtherEvent_instance_stateStart
				.addListener(new TransitionListener() {
					@Override
					public void onTransition(State source, Event event,
							State destination) {
						// The following code is generated, don't edit it
						// directly

					}
				});

		stateM_OtherEvent_instance_stateStart
				.addListener(new TransitionListener() {
					@Override
					public void onTransition(State source, Event event,
							State destination) {
						// The following code is generated, don't edit it
						// directly

					}
				});

		stateN_CharacterEvent_instanceo_stateO
				.addListener(new TransitionListener() {
					@Override
					public void onTransition(State source, Event event,
							State destination) {
						// The following code is generated, don't edit it
						// directly

					}
				});

		stateN_OtherEvent_instance_stateStart
				.addListener(new TransitionListener() {
					@Override
					public void onTransition(State source, Event event,
							State destination) {
						// The following code is generated, don't edit it
						// directly

					}
				});

		stateO_CharacterEvent_instancet_stateT
				.addListener(new TransitionListener() {
					@Override
					public void onTransition(State source, Event event,
							State destination) {
						// The following code is generated, don't edit it
						// directly

					}
				});

		stateO_OtherEvent_instance_stateStart
				.addListener(new TransitionListener() {
					@Override
					public void onTransition(State source, Event event,
							State destination) {
						// The following code is generated, don't edit it
						// directly

					}
				});

		stateR_CharacterEvent_instanceF_stateF
				.addListener(new TransitionListener() {
					@Override
					public void onTransition(State source, Event event,
							State destination) {
						// The following code is generated, don't edit it
						// directly
						logger.info("Great, nearly done, please continue.");
					}
				});

		stateR_OtherEvent_instance_stateStart
				.addListener(new TransitionListener() {
					@Override
					public void onTransition(State source, Event event,
							State destination) {
						// The following code is generated, don't edit it
						// directly

					}
				});

		stateS_CharacterEvent_instanceM_stateM
				.addListener(new TransitionListener() {
					@Override
					public void onTransition(State source, Event event,
							State destination) {
						// The following code is generated, don't edit it
						// directly

					}
				});

		stateS_OtherEvent_instance_stateStart
				.addListener(new TransitionListener() {
					@Override
					public void onTransition(State source, Event event,
							State destination) {
						// The following code is generated, don't edit it
						// directly

					}
				});

		stateStart_CharacterEvent_instanceA_stateA
				.addListener(new TransitionListener() {
					@Override
					public void onTransition(State source, Event event,
							State destination) {
						// The following code is generated, don't edit it
						// directly

					}
				});

		stateStart_OtherEvent_instance_stateStart
				.addListener(new TransitionListener() {
					@Override
					public void onTransition(State source, Event event,
							State destination) {
						// The following code is generated, don't edit it
						// directly

					}
				});

		stateT_CharacterEvent_instanceh_stateH
				.addListener(new TransitionListener() {
					@Override
					public void onTransition(State source, Event event,
							State destination) {
						// The following code is generated, don't edit it
						// directly

					}
				});

		stateT_OtherEvent_instance_stateStart
				.addListener(new TransitionListener() {
					@Override
					public void onTransition(State source, Event event,
							State destination) {
						// The following code is generated, don't edit it
						// directly

					}
				});

	}
}
