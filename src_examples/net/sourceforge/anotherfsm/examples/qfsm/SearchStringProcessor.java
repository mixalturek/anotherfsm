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

import net.sourceforge.anotherfsm.*;

/**
 * State machine to search hard coded "AnotherFSM" string.
 * 
 * @author Michal Turek
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

		stateA.addListener(new StateAdapter(StateListener.Type.LOOP_NO_PROCESS) {
			@Override
			public void onStateEnter(State previous, Event event, State current) {
				// TODO Auto-generated method stub
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

		stateStart.addListener(new StateAdapter(StateListener.Type.LOOP_NO_PROCESS) {
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


		stateA_CharacterEvent_instancen_stateN.addListener(new TransitionListener() {
			@Override
			public void onTransition(State source, Event event,	State destination) {
				// TODO Auto-generated method stub
			}
		});

		stateA_OtherEvent_instance_stateStart.addListener(new TransitionListener() {
			@Override
			public void onTransition(State source, Event event,	State destination) {
				// TODO Auto-generated method stub
			}
		});

		stateE_CharacterEvent_instancer_stateR.addListener(new TransitionListener() {
			@Override
			public void onTransition(State source, Event event,	State destination) {
				// TODO Auto-generated method stub
			}
		});

		stateE_OtherEvent_instance_stateStart.addListener(new TransitionListener() {
			@Override
			public void onTransition(State source, Event event,	State destination) {
				// TODO Auto-generated method stub
			}
		});

		stateF_CharacterEvent_instanceS_stateS.addListener(new TransitionListener() {
			@Override
			public void onTransition(State source, Event event,	State destination) {
				// TODO Auto-generated method stub
			}
		});

		stateF_OtherEvent_instance_stateStart.addListener(new TransitionListener() {
			@Override
			public void onTransition(State source, Event event,	State destination) {
				// TODO Auto-generated method stub
			}
		});

		stateH_CharacterEvent_instancee_stateE.addListener(new TransitionListener() {
			@Override
			public void onTransition(State source, Event event,	State destination) {
				// TODO Auto-generated method stub
			}
		});

		stateH_OtherEvent_instance_stateStart.addListener(new TransitionListener() {
			@Override
			public void onTransition(State source, Event event,	State destination) {
				// TODO Auto-generated method stub
			}
		});

		stateM_OtherEvent_instance_stateStart.addListener(new TransitionListener() {
			@Override
			public void onTransition(State source, Event event,	State destination) {
				// TODO Auto-generated method stub
			}
		});

		stateN_CharacterEvent_instanceo_stateO.addListener(new TransitionListener() {
			@Override
			public void onTransition(State source, Event event,	State destination) {
				// TODO Auto-generated method stub
			}
		});

		stateN_OtherEvent_instance_stateStart.addListener(new TransitionListener() {
			@Override
			public void onTransition(State source, Event event,	State destination) {
				// TODO Auto-generated method stub
			}
		});

		stateO_CharacterEvent_instancet_stateT.addListener(new TransitionListener() {
			@Override
			public void onTransition(State source, Event event,	State destination) {
				// TODO Auto-generated method stub
			}
		});

		stateO_OtherEvent_instance_stateStart.addListener(new TransitionListener() {
			@Override
			public void onTransition(State source, Event event,	State destination) {
				// TODO Auto-generated method stub
			}
		});

		stateR_CharacterEvent_instanceF_stateF.addListener(new TransitionListener() {
			@Override
			public void onTransition(State source, Event event,	State destination) {
				// TODO Auto-generated method stub
			}
		});

		stateR_OtherEvent_instance_stateStart.addListener(new TransitionListener() {
			@Override
			public void onTransition(State source, Event event,	State destination) {
				// TODO Auto-generated method stub
			}
		});

		stateS_CharacterEvent_instanceM_stateM.addListener(new TransitionListener() {
			@Override
			public void onTransition(State source, Event event,	State destination) {
				// TODO Auto-generated method stub
			}
		});

		stateS_OtherEvent_instance_stateStart.addListener(new TransitionListener() {
			@Override
			public void onTransition(State source, Event event,	State destination) {
				// TODO Auto-generated method stub
			}
		});

		stateStart_CharacterEvent_instanceA_stateA.addListener(new TransitionListener() {
			@Override
			public void onTransition(State source, Event event,	State destination) {
				// TODO Auto-generated method stub
			}
		});

		stateStart_OtherEvent_instance_stateStart.addListener(new TransitionListener() {
			@Override
			public void onTransition(State source, Event event,	State destination) {
				// TODO Auto-generated method stub
			}
		});

		stateT_CharacterEvent_instanceh_stateH.addListener(new TransitionListener() {
			@Override
			public void onTransition(State source, Event event,	State destination) {
				// TODO Auto-generated method stub
			}
		});

		stateT_OtherEvent_instance_stateStart.addListener(new TransitionListener() {
			@Override
			public void onTransition(State source, Event event,	State destination) {
				// TODO Auto-generated method stub
			}
		});


	}
}

