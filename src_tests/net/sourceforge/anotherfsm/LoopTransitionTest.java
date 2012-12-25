/*
 *  Copyright 2012 Michal Turek, Another FSM
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

import org.junit.Test;

public class LoopTransitionTest {
	@Test
	public final void testGetState() {
		LoopTransition transition = new LoopTransition(new State("state"),
				new TypeEventA());

		assertEquals(new State("state"), transition.getState());
		assertEquals(new State("state"), transition.getSource());
		assertEquals(new State("state"), transition.getDestination());
		assertEquals(transition.getSource(), transition.getDestination());
	}

	@Test
	public final void testToString() {
		assertEquals("state <-> TypeEventA", new LoopTransition(new State(
				"state"), new TypeEventA()).toString());
	}
}