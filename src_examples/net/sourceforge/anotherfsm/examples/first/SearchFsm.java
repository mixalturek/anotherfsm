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

import net.sourceforge.anotherfsm.CharacterEvent;
import net.sourceforge.anotherfsm.DeterministicStateMachine;
import net.sourceforge.anotherfsm.FsmException;
import net.sourceforge.anotherfsm.OtherEvent;
import net.sourceforge.anotherfsm.State;
import net.sourceforge.anotherfsm.Transition;

/**
 * State machine to search hard coded "AnotherFSM" string. This class will be
 * generated from state machine schema after the editor and/or generator is
 * implemented.
 * 
 * Note the state machine is not fully correct. Every state should also contain
 * transition on 'A' character to {@link #stateA} but this was ignored for
 * simplicity.
 * 
 * @author Michal Turek
 */
class SearchFsm extends DeterministicStateMachine {
	// The states
	public final State stateStart;
	public final State stateA;
	public final State stateN;
	public final State stateO;
	public final State stateT;
	public final State stateH;
	public final State stateE;
	public final State stateR;
	public final State stateF;
	public final State stateS;
	public final State stateM;

	// The transitions to search "AnotherFSM" string
	public final Transition trStartToA;
	public final Transition trAtoN;
	public final Transition trNtoO;
	public final Transition trOtoT;
	public final Transition trTtoH;
	public final Transition trHtoE;
	public final Transition trEtoR;
	public final Transition trRtoF;
	public final Transition trFtoS;
	public final Transition trStoM;

	// The transitions to return searching to the beginning on bad input
	public final Transition trStartOther;
	public final Transition trAOther;
	public final Transition trNOther;
	public final Transition trOOther;
	public final Transition trTOther;
	public final Transition trHOther;
	public final Transition trEOther;
	public final Transition trROther;
	public final Transition trFOther;
	public final Transition trSOther;
	public final Transition trMOther;

	/**
	 * Create the object, build the state machine.
	 * 
	 * @param name
	 *            the name of the state machine
	 * @throws FsmException
	 *             if building of state machine fails
	 */
	public SearchFsm(String name) throws FsmException {
		super(name);

		// Create the states
		stateStart = new State("Start state");
		stateA = new State("State A");
		stateN = new State("State N");
		stateO = new State("State O");
		stateT = new State("State T");
		stateH = new State("State H");
		stateE = new State("State E");
		stateR = new State("State R");
		stateF = new State("State F");
		stateS = new State("State S");
		stateM = new State("State M", State.Type.FINAL);

		// Create the transitions for searching
		trStartToA = new Transition(stateStart, new CharacterEvent('A'), stateA);
		trAtoN = new Transition(stateA, new CharacterEvent('n'), stateN);
		trNtoO = new Transition(stateN, new CharacterEvent('o'), stateO);
		trOtoT = new Transition(stateO, new CharacterEvent('t'), stateT);
		trTtoH = new Transition(stateT, new CharacterEvent('h'), stateH);
		trHtoE = new Transition(stateH, new CharacterEvent('e'), stateE);
		trEtoR = new Transition(stateE, new CharacterEvent('r'), stateR);
		trRtoF = new Transition(stateR, new CharacterEvent('F'), stateF);
		trFtoS = new Transition(stateF, new CharacterEvent('S'), stateS);
		trStoM = new Transition(stateS, new CharacterEvent('M'), stateM);

		// Create the transitions for bad input
		trStartOther = new Transition(stateStart, OtherEvent.instance,
				stateStart);
		trAOther = new Transition(stateA, OtherEvent.instance, stateStart);
		trNOther = new Transition(stateN, OtherEvent.instance, stateStart);
		trOOther = new Transition(stateO, OtherEvent.instance, stateStart);
		trTOther = new Transition(stateT, OtherEvent.instance, stateStart);
		trHOther = new Transition(stateH, OtherEvent.instance, stateStart);
		trEOther = new Transition(stateE, OtherEvent.instance, stateStart);
		trROther = new Transition(stateR, OtherEvent.instance, stateStart);
		trFOther = new Transition(stateF, OtherEvent.instance, stateStart);
		trSOther = new Transition(stateS, OtherEvent.instance, stateStart);
		trMOther = new Transition(stateM, OtherEvent.instance, stateStart);

		// Register states
		addState(stateStart);
		addState(stateA);
		addState(stateN);
		addState(stateO);
		addState(stateT);
		addState(stateH);
		addState(stateE);
		addState(stateR);
		addState(stateF);
		addState(stateS);
		addState(stateM);

		// Register transitions for searching
		addTransition(trStartToA);
		addTransition(trAtoN);
		addTransition(trNtoO);
		addTransition(trOtoT);
		addTransition(trTtoH);
		addTransition(trHtoE);
		addTransition(trEtoR);
		addTransition(trRtoF);
		addTransition(trFtoS);
		addTransition(trStoM);

		// Register transitions for bad input
		addTransition(trStartOther);
		addTransition(trAOther);
		addTransition(trNOther);
		addTransition(trOOther);
		addTransition(trTOther);
		addTransition(trHOther);
		addTransition(trEOther);
		addTransition(trROther);
		addTransition(trFOther);
		addTransition(trSOther);
		addTransition(trMOther);

		// Define the start state
		setStartState(stateStart);
	}
}
