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
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class TransitionTest {

	@Test
	public final void testAddListener() {
		Transition transition = new Transition(new State("source"),
				new TypeEventImpl(), new State("destination"));
		TransitionListenerImpl listener = new TransitionListenerImpl();

		transition.addListener(listener);

		assertEquals(0, listener.transitionsNum);

		transition.notifyTransition(new State("source"), new TypeEventImpl(),
				new State("destination"));

		assertEquals(1, listener.transitionsNum);

		transition.notifyTransition(new State("source"), new TypeEventImpl(),
				new State("destination"));

		assertEquals(2, listener.transitionsNum);
	}

	@Test
	public final void testHashCode() {
		Transition transition1 = new Transition(new State("source"),
				new TypeEventImpl(), new State("destination"));
		Transition transition2 = new Transition(new State("source"),
				new TypeEventImpl(), new State("destination"));
		Transition transition3 = new Transition(new State("source"),
				new TypeEventImpl2(), new State("destination"));

		assertEquals(transition1.hashCode(), transition2.hashCode());
		assertFalse(transition1.hashCode() == transition3.hashCode());
		assertFalse(transition2.hashCode() == transition3.hashCode());
	}

	@Test
	public final void testEqualsObject() {
		Transition transition1 = new Transition(new State("source"),
				new TypeEventImpl(), new State("destination"));
		Transition transition2 = new Transition(new State("source"),
				new TypeEventImpl(), new State("destination"));
		Transition transition3 = new Transition(new State("source"),
				new TypeEventImpl2(), new State("destination"));

		assertEquals(transition1, transition1);
		assertEquals(transition2, transition2);
		assertEquals(transition3, transition3);
		assertEquals(transition1, transition2);
		assertEquals(transition2, transition1);
		assertFalse(transition1.equals(transition3));
		assertFalse(transition2.equals(transition3));
		assertFalse(transition3.equals(transition1));
		assertFalse(transition3.equals(transition2));
	}

	@Test
	public final void testToString() {
		Transition transition = new Transition(new State("source"),
				new TypeEventImpl(), new State("destination"));

		assertEquals("source -> TypeEventImpl() -> destination",
				transition.toString());
	}
}
