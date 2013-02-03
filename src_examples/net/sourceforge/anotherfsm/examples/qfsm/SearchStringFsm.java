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
class SearchStringFsm extends DeterministicStateMachine {
	/**
	 * The start state.
	 */
	public final State stateStart;

	/**
	 * "A" processed.
	 */
	public final State stateA;

	/**
	 * "An" processed.
	 */
	public final State stateN;

	/**
	 * "Ano" processed.
	 */
	public final State stateO;

	/**
	 * "Anot" processed.
	 */
	public final State stateT;

	/**
	 * "Anoth" processed.
	 */
	public final State stateH;

	/**
	 * "Anothe" processed.
	 */
	public final State stateE;

	/**
	 * "Another" processed.
	 */
	public final State stateR;

	/**
	 * "AnotherF" processed.
	 */
	public final State stateF;

	/**
	 * "AnotherFS" processed.
	 */
	public final State stateS;

	/**
	 * "AnotherFSM" processed.
	 */
	public final State stateM;


	/**
	 * 
	 */
	public final Transition stateStart_CharacterEvent_instanceA_stateA;

	/**
	 * 
	 */
	public final Transition stateStart_OtherEvent_instance_stateStart;

	/**
	 * 
	 */
	public final Transition stateA_CharacterEvent_instancen_stateN;

	/**
	 * 
	 */
	public final Transition stateA_OtherEvent_instance_stateStart;

	/**
	 * 
	 */
	public final Transition stateN_OtherEvent_instance_stateStart;

	/**
	 * 
	 */
	public final Transition stateN_CharacterEvent_instanceo_stateO;

	/**
	 * 
	 */
	public final Transition stateO_OtherEvent_instance_stateStart;

	/**
	 * 
	 */
	public final Transition stateO_CharacterEvent_instancet_stateT;

	/**
	 * 
	 */
	public final Transition stateT_OtherEvent_instance_stateStart;

	/**
	 * 
	 */
	public final Transition stateT_CharacterEvent_instanceh_stateH;

	/**
	 * 
	 */
	public final Transition stateH_OtherEvent_instance_stateStart;

	/**
	 * 
	 */
	public final Transition stateH_CharacterEvent_instancee_stateE;

	/**
	 * 
	 */
	public final Transition stateE_OtherEvent_instance_stateStart;

	/**
	 * 
	 */
	public final Transition stateE_CharacterEvent_instancer_stateR;

	/**
	 * 
	 */
	public final Transition stateR_OtherEvent_instance_stateStart;

	/**
	 * 
	 */
	public final Transition stateR_CharacterEvent_instanceF_stateF;

	/**
	 * 
	 */
	public final Transition stateF_OtherEvent_instance_stateStart;

	/**
	 * 
	 */
	public final Transition stateF_CharacterEvent_instanceS_stateS;

	/**
	 * 
	 */
	public final Transition stateS_OtherEvent_instance_stateStart;

	/**
	 * 
	 */
	public final Transition stateS_CharacterEvent_instanceM_stateM;

	/**
	 * 
	 */
	public final Transition stateM_OtherEvent_instance_stateStart;



	/**
	 * Create the object, build the state machine.
	 * 
	 * @param name
	 *            the name of the state machine
	 * @throws FsmException
	 *             if building of state machine fails
	 */
	public SearchStringFsm(String name) throws FsmException {
		super(name);

		stateStart = new State("stateStart");
		addState(stateStart);

		stateA = new State("stateA");
		addState(stateA);

		stateN = new State("stateN");
		addState(stateN);

		stateO = new State("stateO");
		addState(stateO);

		stateT = new State("stateT");
		addState(stateT);

		stateH = new State("stateH");
		addState(stateH);

		stateE = new State("stateE");
		addState(stateE);

		stateR = new State("stateR");
		addState(stateR);

		stateF = new State("stateF");
		addState(stateF);

		stateS = new State("stateS");
		addState(stateS);

		stateM = new State("stateM", State.Type.FINAL);
		addState(stateM);


		stateStart_CharacterEvent_instanceA_stateA = new Transition(stateStart, CharacterEvent.instance('A'), stateA);
		addTransition(stateStart_CharacterEvent_instanceA_stateA);

		stateStart_OtherEvent_instance_stateStart = new Transition(stateStart, OtherEvent.instance, stateStart);
		addTransition(stateStart_OtherEvent_instance_stateStart);

		stateA_CharacterEvent_instancen_stateN = new Transition(stateA, CharacterEvent.instance('n'), stateN);
		addTransition(stateA_CharacterEvent_instancen_stateN);

		stateA_OtherEvent_instance_stateStart = new Transition(stateA, OtherEvent.instance, stateStart);
		addTransition(stateA_OtherEvent_instance_stateStart);

		stateN_OtherEvent_instance_stateStart = new Transition(stateN, OtherEvent.instance, stateStart);
		addTransition(stateN_OtherEvent_instance_stateStart);

		stateN_CharacterEvent_instanceo_stateO = new Transition(stateN, CharacterEvent.instance('o'), stateO);
		addTransition(stateN_CharacterEvent_instanceo_stateO);

		stateO_OtherEvent_instance_stateStart = new Transition(stateO, OtherEvent.instance, stateStart);
		addTransition(stateO_OtherEvent_instance_stateStart);

		stateO_CharacterEvent_instancet_stateT = new Transition(stateO, CharacterEvent.instance('t'), stateT);
		addTransition(stateO_CharacterEvent_instancet_stateT);

		stateT_OtherEvent_instance_stateStart = new Transition(stateT, OtherEvent.instance, stateStart);
		addTransition(stateT_OtherEvent_instance_stateStart);

		stateT_CharacterEvent_instanceh_stateH = new Transition(stateT, CharacterEvent.instance('h'), stateH);
		addTransition(stateT_CharacterEvent_instanceh_stateH);

		stateH_OtherEvent_instance_stateStart = new Transition(stateH, OtherEvent.instance, stateStart);
		addTransition(stateH_OtherEvent_instance_stateStart);

		stateH_CharacterEvent_instancee_stateE = new Transition(stateH, CharacterEvent.instance('e'), stateE);
		addTransition(stateH_CharacterEvent_instancee_stateE);

		stateE_OtherEvent_instance_stateStart = new Transition(stateE, OtherEvent.instance, stateStart);
		addTransition(stateE_OtherEvent_instance_stateStart);

		stateE_CharacterEvent_instancer_stateR = new Transition(stateE, CharacterEvent.instance('r'), stateR);
		addTransition(stateE_CharacterEvent_instancer_stateR);

		stateR_OtherEvent_instance_stateStart = new Transition(stateR, OtherEvent.instance, stateStart);
		addTransition(stateR_OtherEvent_instance_stateStart);

		stateR_CharacterEvent_instanceF_stateF = new Transition(stateR, CharacterEvent.instance('F'), stateF);
		addTransition(stateR_CharacterEvent_instanceF_stateF);

		stateF_OtherEvent_instance_stateStart = new Transition(stateF, OtherEvent.instance, stateStart);
		addTransition(stateF_OtherEvent_instance_stateStart);

		stateF_CharacterEvent_instanceS_stateS = new Transition(stateF, CharacterEvent.instance('S'), stateS);
		addTransition(stateF_CharacterEvent_instanceS_stateS);

		stateS_OtherEvent_instance_stateStart = new Transition(stateS, OtherEvent.instance, stateStart);
		addTransition(stateS_OtherEvent_instance_stateStart);

		stateS_CharacterEvent_instanceM_stateM = new Transition(stateS, CharacterEvent.instance('M'), stateM);
		addTransition(stateS_CharacterEvent_instanceM_stateM);

		stateM_OtherEvent_instance_stateStart = new Transition(stateM, OtherEvent.instance, stateStart);
		addTransition(stateM_OtherEvent_instance_stateStart);


		setStartState(stateStart);
	}
}

