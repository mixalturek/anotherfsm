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

package net.sourceforge.anotherfsm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class NondeterministicTransitionMapTest {
	@Test
	public void testAddTransition() {
		NondeterministicTransitionMap map = new NondeterministicTransitionMap();

		try {
			assertEquals(0, map.getTransitions().size());

			map.addTransition(new Transition(new State("Start"),
					new TypeEventA(), new State("End")));
			assertEquals(1, map.getTransitions().size());

			map.addTransition(new Transition(new State("Start"),
					new TypeEventB(), new State("End")));
			assertEquals(2, map.getTransitions().size());

			map.addTransition(new Transition(new State("Start"),
					new TypeEventA(), new State("Different End")));
			assertEquals(3, map.getTransitions().size());

			map.addTransition(new Transition(new State("Start"),
					new TypeEventA(), new State("One more End")));
			assertEquals(4, map.getTransitions().size());
		} catch (FsmException e) {
			fail("Should not be executed: " + e);
		}

		try {
			map.addTransition(new Transition(new State("Start"),
					new TypeEventA(), new State("End")));

			fail("Should not be executed");
		} catch (FsmException e) {
			assertEquals(4, map.getTransitions().size());

			assertTrue(e.getMessage()
					.startsWith("Transition already defined: "));
		}
	}

	@Test
	public void testGetTransitions() {
		NondeterministicTransitionMap map = new NondeterministicTransitionMap();

		try {
			assertEquals(0, map.getTransitions(new TypeEventA()).size());

			map.addTransition(new Transition(new State("Start"),
					new TypeEventA(), new State("End")));
			assertEquals(1, map.getTransitions(new TypeEventA()).size());

			map.addTransition(new Transition(new State("Start"),
					new TypeEventB(), new State("End")));
			assertEquals(1, map.getTransitions(new TypeEventA()).size());
			assertEquals(1, map.getTransitions(new TypeEventB()).size());

			map.addTransition(new Transition(new State("Start"),
					new TypeEventA(), new State("Different End")));
			assertEquals(2, map.getTransitions(new TypeEventA()).size());

			map.addTransition(new Transition(new State("Start"),
					new TypeEventA(), new State("One more End")));
			assertEquals(3, map.getTransitions(new TypeEventA()).size());

			map.addTransition(new Transition(new State("Start"),
					new TypeEventB(), new State("Different End")));
			assertEquals(3, map.getTransitions(new TypeEventA()).size());
			assertEquals(2, map.getTransitions(new TypeEventB()).size());
		} catch (FsmException e) {
			fail("Should not be executed: " + e);
		}
	}

	@Test
	public void testToString() {
		NondeterministicTransitionMap map = new NondeterministicTransitionMap();

		try {
			assertEquals("", map.toString());

			map.addTransition(new Transition(new State("Start"),
					new TypeEventA(), new State("End")));
			String oneTransition = map.toString();

			assertFalse(oneTransition.isEmpty());

			map.addTransition(new Transition(new State("Start"),
					new TypeEventB(), new State("End")));
			String twoTransitions = map.toString();

			assertFalse(twoTransitions.isEmpty());
			assertTrue(twoTransitions.startsWith(oneTransition));
		} catch (FsmException e) {
			fail("Should not be executed: " + e);
		}
	}
}
