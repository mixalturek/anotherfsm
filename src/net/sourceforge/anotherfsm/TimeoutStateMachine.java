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

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Deterministic state machine with timeouts for states. The object is thread
 * safe and can be used in multithreaded environment.
 * 
 * Building of the object is NOT synchronized and should be done just in one
 * thread. Only the processing of the events is synchronized.
 * 
 * Note creating of this object also executes an additional thread for a timer
 * and generating of timeout events. Code of callbacks/listeners for timeout
 * events is executed under this thread.
 * 
 * @author Michal Turek
 * 
 * @see AnotherFsm#genTimeoutEvent(long, TimeoutEvent.Type)
 * @see AnotherFsm#genTimeoutStateMachine(String)
 */
class TimeoutStateMachine extends SynchronizedStateMachine {
	/** The timer for scheduling timeout transitions. */
	private Timer timer = null;

	/**
	 * The unique ID of the last state enter to forbid timeout in incorrect
	 * state. Instance of any object is enough since every two objects are
	 * different.
	 */
	private Object lastStateEnterID = new Object();

	/**
	 * Create the state machine.
	 * 
	 * @param name
	 *            the name of the state machine
	 */
	public TimeoutStateMachine(String name) {
		super(name);
	}

	@Override
	public synchronized void start() throws FsmException {
		// The timer must be created first, super.start() generates start event
		if (timer != null)
			throw new FsmException(
					"Timer already running, state machine probably started twice");

		timer = new Timer(getClass().getSimpleName() + "-" + getName(), false);

		try {
			super.start();
		} catch (FsmException e) {
			if (timer != null) {
				timer.cancel();
				timer = null;
			}
			throw e;
		}
	}

	@Override
	public synchronized void close() throws IOException {
		try {
			super.close();
		} finally {
			if (timer != null) {
				timer.cancel();
				timer = null;
			} else {
				logger.warn("Timer is not running while closing, state machine probably not started yet");
				// Don't throw exceptions while destroying
			}
		}
	}

	@Override
	public synchronized void notifyEnter(State previous, Event event,
			State current) throws FsmException {
		super.notifyEnter(previous, event, current);

		if (timer == null) {
			String msg = "Timer is not running while processing, state machine probably not started yet";
			logger.error(msg);
			throw new FsmException(msg);
		}

		scheduleTimeoutTransition(previous.equals(current));
	}

	/**
	 * Schedule a new timeout transition, a state was entered.
	 * 
	 * @param loopTransition
	 *            the transition was loop transition
	 */
	private synchronized void scheduleTimeoutTransition(boolean loopTransition) {
		Transition timeoutTransition = getTransition(getActiveState(),
				TimeoutEventImpl.INSTANCE);

		if (timeoutTransition == null) {
			cancelExistingTimeoutTask();
			return; // Correct
		}

		TimeoutEvent timeoutEvent = (TimeoutEvent) timeoutTransition.getEvent();

		if (loopTransition
				&& timeoutEvent.getType() == TimeoutEvent.Type.LOOP_NO_RESTART) {
			return; // Correct
		}

		cancelExistingTimeoutTask();

		timer.schedule(new TimeoutTask(timeoutTransition, lastStateEnterID),
				timeoutEvent.getTimeout());
	}

	/**
	 * Cancel the existing timeout task, it must be never processed.
	 */
	private synchronized void cancelExistingTimeoutTask() {
		lastStateEnterID = new Object();

		// Existing timeout task can be cancelled here
	}

	/**
	 * Process the timeout transition.
	 * 
	 * @param timeoutTransition
	 *            the transition with TimeoutEvent that should be processed
	 * @param stateEnterID
	 *            the unique ID of the state enter for this task
	 */
	private synchronized void proccessTimeoutTransition(
			Transition timeoutTransition, Object stateEnterID) {
		if (stateEnterID != lastStateEnterID)
			return; // Correct

		try {
			process(timeoutTransition.getEvent());
		} catch (FsmException e) {
			logger.error("Processing of timeout failed: " + timeoutTransition);
			// No other possibility to signal error inside the timer thread
		}
	}

	/**
	 * The timeout task for scheduling the timeout transitions.
	 * 
	 * @author Michal Turek
	 */
	private class TimeoutTask extends TimerTask {
		/** The transition with TimeoutEvent that should be processed. */
		private final Transition timeoutTransition;

		/** The unique ID of the state enter for this task. */
		private final Object stateEnterID;

		/**
		 * Create the object.
		 * 
		 * @param timeoutTransition
		 *            the transition with TimeoutEvent that should be processed
		 * @param stateEnterID
		 *            the unique ID of the state enter for this task
		 */
		public TimeoutTask(Transition timeoutTransition, Object stateEnterID) {
			this.timeoutTransition = timeoutTransition;
			this.stateEnterID = stateEnterID;
		}

		@Override
		public void run() {
			proccessTimeoutTransition(timeoutTransition, stateEnterID);
		}
	}
}
