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

import net.sourceforge.anotherfsm.*;

/**
 * Simple connect/disconnect state machine with timeouts.
 * 
 * <p>
 * This file was generated using AnotherFSM CodeGenerator:<br />
 * {@code java -classpath anotherfsm-0.2.0-dev.jar net.sourceforge.anotherfsm.qfsm.CodeGenerator --force --config-file TimeoutConnection.xml --qfsm-file TimeoutConnection.fsm}
 * </p>
 * 
 * @author Michal Turek
 * @version 0.1.0
 */
class TimeoutConnectionProcessor extends TimeoutConnectionFsm {
	/**
	 * Create the object, define and connect listeners.
	 * 
	 * @param name
	 *            the name of the state machine
	 * @throws FsmException
	 *             if building of state machine fails
	 */
	public TimeoutConnectionProcessor(String name) throws FsmException {
		super(name);





		connected_ConnectionStateEvent_instanceCONNECT_connected.addListener(new TransitionListener() {
			@Override
			public void onTransition(State source, Event event,	State destination) {
				// The following code is generated, don't edit it directly
				logger.info("No action");
			}
		});

		connected_ConnectionStateEvent_instanceDISCONNECT_disconnected.addListener(new TransitionListener() {
			@Override
			public void onTransition(State source, Event event,	State destination) {
				// The following code is generated, don't edit it directly
				logger.info("Disconnected");
			}
		});

		connected_TimeoutEvent_instance10000TimeoutEvent_Type_LOOP_NO_RESTART_disconnected.addListener(new TransitionListener() {
			@Override
			public void onTransition(State source, Event event,	State destination) {
				// The following code is generated, don't edit it directly
				logger.info("Time for connection expired");
			}
		});

		disconnected_ConnectionStateEvent_instanceCONNECT_connected.addListener(new TransitionListener() {
			@Override
			public void onTransition(State source, Event event,	State destination) {
				// The following code is generated, don't edit it directly
				logger.info("Connected");
			}
		});

		disconnected_ConnectionStateEvent_instanceDISCONNECT_disconnected.addListener(new TransitionListener() {
			@Override
			public void onTransition(State source, Event event,	State destination) {
				// The following code is generated, don't edit it directly
				logger.info("No action");
			}
		});

		disconnected_TimeoutEvent_instance10000TimeoutEvent_Type_LOOP_RESTART_disconnected.addListener(new TransitionListener() {
			@Override
			public void onTransition(State source, Event event,	State destination) {
				// The following code is generated, don't edit it directly
				logger.info("Nobody is connected");
			}
		});


	}
}
