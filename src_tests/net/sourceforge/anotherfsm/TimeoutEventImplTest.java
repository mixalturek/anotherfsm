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

public class TimeoutEventImplTest {
	@Test
	public final void hashCodeTest() {
		assertEquals(TimeoutEvent.HASH_CODE, new TimeoutEventImpl(1,
				TimeoutEvent.Type.DONT_RESTART_TIMEOUT_ON_LOOP).hashCode());

		assertEquals(TimeoutEvent.HASH_CODE,
				new NonstandardTimeoutEvent().hashCode());
	}

	@Test
	public final void equalsTest() {
		assertEquals(new TimeoutEventImpl(1,
				TimeoutEvent.Type.DONT_RESTART_TIMEOUT_ON_LOOP),
				new TimeoutEventImpl(2,
						TimeoutEvent.Type.RESTART_TIMEOUT_ON_LOOP));

		assertEquals(new NonstandardTimeoutEvent(), new TimeoutEventImpl(2,
				TimeoutEvent.Type.RESTART_TIMEOUT_ON_LOOP));

		assertEquals(new TimeoutEventImpl(2,
				TimeoutEvent.Type.RESTART_TIMEOUT_ON_LOOP),
				new NonstandardTimeoutEvent());
	}
}
