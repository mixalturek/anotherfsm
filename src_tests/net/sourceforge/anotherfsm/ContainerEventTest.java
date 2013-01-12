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

import org.junit.Test;

public class ContainerEventTest {
	@Test
	public final void testEqualsObject() {
		assertEquals(new ContainerEvent<Character>('A'),
				new ContainerEvent<Character>('A'));
		assertEquals(new ContainerEvent<TypeEventA>(new TypeEventA()),
				new ContainerEvent<TypeEventA>(new TypeEventA()));
		assertFalse(new ContainerEvent<TypeEventA>(new TypeEventA())
				.equals(new ContainerEvent<TypeEventB>(new TypeEventB())));
		assertFalse(new ContainerEvent<Object>(new Object())
				.equals(new ContainerEvent<Object>(new Object())));
	}

	@Test
	public final void testToString() {
		assertEquals("ContainerEvent(A)",
				new ContainerEvent<Character>('A').toString());
		assertEquals("ContainerEvent(TypeEventA)",
				new ContainerEvent<TypeEventA>(new TypeEventA()).toString());
	}
}
