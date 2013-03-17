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
class TimeoutConnectionFsm extends TimeoutStateMachine {
	/**
	 * The client is connected.
	 */
	public final State connected;

	/**
	 * The client is disconnected.
	 */
	public final State disconnected;


	/**
	 * Transition from {@code connected} to {@code connected} on {@code ConnectionStateEvent.instanceCONNECT} event.
	 */
	public final Transition connected_ConnectionStateEvent_instanceCONNECT_connected;

	/**
	 * Transition from {@code connected} to {@code disconnected} on {@code ConnectionStateEvent.instanceDISCONNECT} event.
	 */
	public final Transition connected_ConnectionStateEvent_instanceDISCONNECT_disconnected;

	/**
	 * Transition from {@code connected} to {@code disconnected} on {@code TimeoutEvent.instance(10000,TimeoutEvent.Type.LOOP_NO_RESTART)} event.
	 */
	public final Transition connected_TimeoutEvent_instance10000TimeoutEvent_Type_LOOP_NO_RESTART_disconnected;

	/**
	 * Transition from {@code disconnected} to {@code connected} on {@code ConnectionStateEvent.instanceCONNECT} event.
	 */
	public final Transition disconnected_ConnectionStateEvent_instanceCONNECT_connected;

	/**
	 * Transition from {@code disconnected} to {@code disconnected} on {@code ConnectionStateEvent.instanceDISCONNECT} event.
	 */
	public final Transition disconnected_ConnectionStateEvent_instanceDISCONNECT_disconnected;

	/**
	 * Transition from {@code disconnected} to {@code disconnected} on {@code TimeoutEvent.instance(10000,TimeoutEvent.Type.LOOP_RESTART)} event.
	 */
	public final Transition disconnected_TimeoutEvent_instance10000TimeoutEvent_Type_LOOP_RESTART_disconnected;



	/**
	 * Create the object, build the state machine.
	 * 
	 * @param name
	 *            the name of the state machine
	 * @throws FsmException
	 *             if building of state machine fails
	 */
	public TimeoutConnectionFsm(String name) throws FsmException {
		super(name);

		connected = new State("connected");
		addState(connected);

		disconnected = new State("disconnected");
		addState(disconnected);


		connected_ConnectionStateEvent_instanceCONNECT_connected = new Transition(connected, ConnectionStateEvent.instanceCONNECT, connected);
		addTransition(connected_ConnectionStateEvent_instanceCONNECT_connected);

		connected_ConnectionStateEvent_instanceDISCONNECT_disconnected = new Transition(connected, ConnectionStateEvent.instanceDISCONNECT, disconnected);
		addTransition(connected_ConnectionStateEvent_instanceDISCONNECT_disconnected);

		connected_TimeoutEvent_instance10000TimeoutEvent_Type_LOOP_NO_RESTART_disconnected = new Transition(connected, TimeoutEvent.instance(10000,TimeoutEvent.Type.LOOP_NO_RESTART), disconnected);
		addTransition(connected_TimeoutEvent_instance10000TimeoutEvent_Type_LOOP_NO_RESTART_disconnected);

		disconnected_ConnectionStateEvent_instanceCONNECT_connected = new Transition(disconnected, ConnectionStateEvent.instanceCONNECT, connected);
		addTransition(disconnected_ConnectionStateEvent_instanceCONNECT_connected);

		disconnected_ConnectionStateEvent_instanceDISCONNECT_disconnected = new Transition(disconnected, ConnectionStateEvent.instanceDISCONNECT, disconnected);
		addTransition(disconnected_ConnectionStateEvent_instanceDISCONNECT_disconnected);

		disconnected_TimeoutEvent_instance10000TimeoutEvent_Type_LOOP_RESTART_disconnected = new Transition(disconnected, TimeoutEvent.instance(10000,TimeoutEvent.Type.LOOP_RESTART), disconnected);
		addTransition(disconnected_TimeoutEvent_instance10000TimeoutEvent_Type_LOOP_RESTART_disconnected);


		setStartState(disconnected);
	}
}
