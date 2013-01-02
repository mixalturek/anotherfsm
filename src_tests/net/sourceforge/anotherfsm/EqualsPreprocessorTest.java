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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import net.sourceforge.anotherfsm.logger.StdStreamLoggerFactory;

import org.junit.BeforeClass;
import org.junit.Test;

public class EqualsPreprocessorTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		AnotherFsm.init(new StdStreamLoggerFactory());
	}

	@Test
	public final void testAddProcessor() {
		EqualsPreprocessor processor = new EqualsPreprocessor("processor");
		try {
			processor.addProcessor(new TypeEventA(),
					new Preprocessor.Processor<TypeEventA>() {
						@Override
						public Event process(TypeEventA event) {
							return new TypeEventB();
						}
					});
		} catch (FsmException e) {
			fail("Should not be executed");
		}

		try {
			processor.addProcessor(new TypeEventA(),
					new Preprocessor.Processor<TypeEventA>() {
						@Override
						public Event process(TypeEventA event) {
							return new TypeEventB();
						}
					});
			fail("Should not be executed");
		} catch (FsmException e) {
			// Do nothing
		}

		try {
			processor.addProcessor(new TypeEventA(), null);
			fail("Should not be executed");
		} catch (FsmException e) {
			fail("Should not be executed");
		} catch (NullPointerException e) {
			// Do nothing
		}

		try {
			processor.addProcessor(null,
					new Preprocessor.Processor<TypeEventA>() {
						@Override
						public Event process(TypeEventA event) {
							return new TypeEventB();
						}
					});
			fail("Should not be executed");
		} catch (FsmException e) {
			fail("Should not be executed");
		} catch (NullPointerException e) {
			// Do nothing
		}
	}

	@Test
	public final void testProcess() {
		EqualsPreprocessor processor = new EqualsPreprocessor("processor");
		Event processedEvent = null;

		try {
			processor.addProcessor(new TypeEventA(),
					new Preprocessor.Processor<TypeEventA>() {
						@Override
						public Event process(TypeEventA event) {
							return new TypeEventB();
						}
					});

			processor.addProcessor(new TypeEventB(),
					new Preprocessor.Processor<TypeEventB>() {
						@Override
						public Event process(TypeEventB event) {
							return null;
						}
					});
		} catch (FsmException e) {
			fail("Should not be executed");
		}

		try {
			processedEvent = processor.process(null);
			fail("Should not be executed");
		} catch (FsmException e) {
			fail("Should not be executed");
		} catch (NullPointerException e) {
			// Do nothing
		}

		try {
			processedEvent = processor.process(new TypeEventA());
			assertEquals(new TypeEventB(), processedEvent);

			processedEvent = processor.process(new TypeEventB());
			assertNull(processedEvent);
		} catch (FsmException e) {
			fail("Should not be executed");
		}
	}

	@Test
	public final void testProcessNoProcessor() {
		EqualsPreprocessor processor = new EqualsPreprocessor("processor");
		Event processedEvent = null;

		try {
			processedEvent = processor.process(new TypeEventA());
			assertEquals(new TypeEventA(), processedEvent);

			processedEvent = processor.process(new TypeEventB());
			assertEquals(new TypeEventB(), processedEvent);
		} catch (FsmException e) {
			fail("Should not be executed");
		}
	}
}
