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

package net.sourceforge.anotherfsm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StateTest {
	@Test
	public final void testAddListener() {
		State state = new State("state");
		State stateFinal = new State("state", State.Type.FINAL);
		StateListenerImpl listener = new StateListenerImpl(
				StateListener.Type.LOOP_PROCESS);

		state.addListener(listener);
		stateFinal.addListener(listener);

		assertEquals(listener.enteredNum, 0);
		assertEquals(listener.exitedNum, 0);

		state.notifyEnter(true, null, null, null);

		assertEquals(listener.enteredNum, 1);
		assertEquals(listener.exitedNum, 0);

		state.notifyExit(true, null, null, null);

		assertEquals(listener.enteredNum, 1);
		assertEquals(listener.exitedNum, 1);

		stateFinal.notifyEnter(true, null, null, null);

		assertEquals(listener.enteredNum, 2);
		assertEquals(listener.exitedNum, 1);

		stateFinal.notifyExit(true, null, null, null);

		assertEquals(listener.enteredNum, 2);
		assertEquals(listener.exitedNum, 2);
	}

	@Test
	public final void testGetName() {
		State state = new State("state");
		assertEquals("state", state.getName());

		State stateNonFinal = new State("state", State.Type.DEFAULT);
		assertEquals("state", stateNonFinal.getName());

		State stateFinal = new State("state", State.Type.FINAL);
		assertEquals("state", stateFinal.getName());
	}

	@Test
	public final void testIsFinalState() {
		State stateDefault = new State("state");
		assertFalse(stateDefault.isFinalState());

		State stateNonFinal = new State("state", State.Type.DEFAULT);
		assertFalse(stateNonFinal.isFinalState());

		State stateFinal = new State("state", State.Type.FINAL);
		assertTrue(stateFinal.isFinalState());
	}

	@Test
	public final void testHashCode() {
		State state1 = new State("same");
		State state2 = new State("same");
		State state3 = new State("different");

		assertEquals(state1.hashCode(), state2.hashCode());
		assertTrue(state1.hashCode() != state3.hashCode());
		assertTrue(state2.hashCode() != state3.hashCode());

		State state1Final = new State("same", State.Type.FINAL);
		State state2Final = new State("same", State.Type.FINAL);
		State state3Final = new State("different", State.Type.FINAL);

		assertEquals(state1Final.hashCode(), state2Final.hashCode());
		assertTrue(state1Final.hashCode() != state3Final.hashCode());
		assertTrue(state2Final.hashCode() != state3Final.hashCode());

		// Only name of the state is used for creating hash code.
		assertEquals(state1.hashCode(), state1Final.hashCode());
		assertEquals(state2.hashCode(), state2Final.hashCode());
		assertEquals(state3.hashCode(), state3Final.hashCode());
	}

	@Test
	public final void testEqualsObject() {
		State state1 = new State("same");
		State state2 = new State("same");
		State state3 = new State("different");

		assertEquals(state1, state2);
		assertEquals(state2, state1);
		assertFalse(state1.equals(state3));
		assertFalse(state2.equals(state3));
		assertFalse(state3.equals(state1));
		assertFalse(state3.equals(state2));

		State state1Final = new State("same", State.Type.FINAL);
		State state2Final = new State("same", State.Type.FINAL);
		State state3Final = new State("different", State.Type.FINAL);

		assertEquals(state1, state1);
		assertEquals(state1Final, state1Final);

		// Only name of the states is used during the comparison.
		assertEquals(state1, state1Final);
		assertEquals(state2, state2Final);
		assertEquals(state3, state3Final);
		assertEquals(state1Final, state1);
		assertEquals(state2Final, state2);
		assertEquals(state3Final, state3);
	}

	@Test
	public final void testToString() {
		State state = new State("state");
		assertEquals("state", state.toString());

		State stateNonFinal = new State("state", State.Type.DEFAULT);
		assertEquals("state", stateNonFinal.toString());

		State stateFinal = new State("state", State.Type.FINAL);
		assertEquals("state", stateFinal.toString());
	}
}
