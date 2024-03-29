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

import org.junit.Test;

public class CharacterEventTest {
	@Test
	public final void testGetCharacter() {
		assertEquals('A', CharacterEvent.instance('A').getCharacter());
		assertEquals('B', CharacterEvent.instance('B').getCharacter());
	}

	@Test
	public final void testEqualsObject() {
		assertEquals(CharacterEvent.instance('A'), CharacterEvent.instance('A'));
		assertFalse(CharacterEvent.instance('A').equals(
				CharacterEvent.instance('B')));
	}

	@Test
	public final void testToString() {
		assertEquals("CharacterEvent(A)", CharacterEvent.instance('A')
				.toString());
	}
}
