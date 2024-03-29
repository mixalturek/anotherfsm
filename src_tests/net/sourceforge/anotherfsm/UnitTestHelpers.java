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
import static org.junit.Assert.fail;

/**
 * Helper methods for unit tests.
 * 
 * @author Michal Turek
 */
public class UnitTestHelpers {
	/** Delta for double comparison. */
	private static final double DELTA = 1e-15;

	/** Expected delay for communication with a thread, in milliseconds. */
	private static final long THREAD_COMMUNICATION_DELAY = 30;

	/**
	 * Get the number of currently living threads in this process.
	 * 
	 * @return the number of threads
	 */
	public static int getNumberOfLivingThreads() {
		return Thread.getAllStackTraces().size();
	}

	/**
	 * Sleep this thread for a predefined delay that should be enough to process
	 * a communication with threads.
	 */
	public static void sleepThreadCommunicationDelay() {
		try {
			Thread.sleep(THREAD_COMMUNICATION_DELAY);
		} catch (InterruptedException e) {
			fail("Should not be executed: " + e);
		}
	}

	/**
	 * Check that a tread is running.
	 * 
	 * @param name
	 *            the thread name
	 * @return true if thread with the specified name is running, otherwise
	 *         false
	 */
	public static boolean isThreadRunning(String name) {
		for (Thread thread : Thread.getAllStackTraces().keySet()) {
			if (name.equals(thread.getName()))
				return true;
		}

		return false;
	}

	/**
	 * Assert equals for double value.
	 * 
	 * @param expected
	 *            the expected value
	 * @param actual
	 *            the actual value
	 */
	public static void assertDoubleEquals(double expected, double actual) {
		assertEquals(expected, actual, DELTA);
	}
}
