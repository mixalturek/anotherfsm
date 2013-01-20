/*
 *  Copyright 2012 Michal Turek, AnotherFSM
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

/**
 * Unit test.
 * 
 * @author Michal Turek
 */
public class TypeEventTest {

	@Test
	public final void testHashCode() {
		TypeEvent e1 = new TypeEventA();
		TypeEvent e2 = new TypeEventA();
		assertTrue(e1.hashCode() == e2.hashCode());
	}

	@Test
	public final void testEqualsObject() {
		TypeEvent e1 = new TypeEventA();
		TypeEvent e2 = new TypeEventA();
		assertTrue(e1.equals(e2));
	}

	@Test
	public final void testToString() {
		assertTrue(TypeEventA.class.getSimpleName().equals(
				new TypeEventA().toString()));
	}

	@Test
	public final void testHashCode2() {
		TypeEvent e1 = new TypeEventA();
		TypeEvent e2 = new TypeEventA();
		TypeEvent e3 = new TypeEventB();
		assertEquals(e1.hashCode(), e2.hashCode());
		assertFalse(e1.hashCode() == e3.hashCode());
		assertFalse(e2.hashCode() == e3.hashCode());
	}

	@Test
	public final void testEqualsObject2() {
		TypeEvent e1 = new TypeEventA();
		TypeEvent e2 = new TypeEventA();
		TypeEvent e3 = new TypeEventB();
		assertEquals(e1, e2);
		assertFalse(e1.equals(e3));
		assertFalse(e2.equals(e3));
	}
}
