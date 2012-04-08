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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test.
 * 
 * @author Michal Turek
 */
public class SimpleEventTest {

	@Test
	public final void testHashCode() {
		SimpleEvent e1 = new SimpleEvent();
		SimpleEvent e2 = new SimpleEvent();
		assertTrue(e1.hashCode() == e2.hashCode());
	}

	@Test
	public final void testEqualsObject() {
		SimpleEvent e1 = new SimpleEvent();
		SimpleEvent e2 = new SimpleEvent();
		assertTrue(e1.equals(e2));
	}

	@Test
	public final void testToString() {
		assertTrue("SimpleEvent()".equals(new SimpleEvent().toString()));
	}

	@Test
	public final void testHashCode2() {
		SimpleEvent e1 = new SimpleEvent();
		TestEvent e2 = new TestEvent();
		assertFalse(e1.hashCode() == e2.hashCode());
	}

	@Test
	public final void testEqualsObject2() {
		SimpleEvent e1 = new SimpleEvent();
		TestEvent e2 = new TestEvent();
		assertFalse(e1.equals(e2));
	}

	@Test
	public final void testToString2() {
		assertTrue("TestEvent()".equals(new TestEvent().toString()));
	}

	private class TestEvent extends SimpleEvent {
		// Empty.
	}
}
