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

import java.util.Timer;
import java.util.TimerTask;

import net.sourceforge.anotherfsm.TimeoutEvent.Type;

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
 * @see TimeoutEvent
 */
public class TimeoutStateMachine extends SynchronizedStateMachine {
	/** The timer for scheduling timeout transitions. */
	private Timer timer = null;

	/**
	 * The unique ID of the last state enter to forbid timeout in incorrect
	 * state. Instance of any object is enough since every two objects are
	 * different. Updated during all state enters.
	 */
	private Object lastStateEnterId = new Object();

	/**
	 * The unique ID of the last state enter to forbid timeout in incorrect
	 * state. Instance of any object is enough since every two objects are
	 * different. Updated only during non-loop state enters.
	 */
	private Object lastStateEnterIdNonLoop = new Object();

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
		if (timer != null) {
			throw new FsmException(
					"Timer already running, state machine probably started twice");
		}

		timer = new Timer(getClass().getSimpleName()
				+ Helpers.CLASS_INSTANCE_DELIMITER + getName(), false);

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
	public synchronized void close() {
		try {
			super.close();
		} finally {
			if (timer != null) {
				timer.cancel();
				timer = null;
			}
		}
	}

	@Override
	synchronized void notifyEnter(State previous, Event event, State current)
			throws FsmException {
		super.notifyEnter(previous, event, current);

		if (timer == null) {
			String msg = "Timer is not running while processing, state machine probably not started yet";
			logger.error(msg);
			throw new FsmException(msg);
		}

		boolean loopTransition = previous.equals(current);

		lastStateEnterId = new Object();

		if (!loopTransition)
			lastStateEnterIdNonLoop = new Object();

		scheduleTimeoutTransition(loopTransition,
				TimeoutEvent.instance_LOOP_RESTART);
		scheduleTimeoutTransition(loopTransition,
				TimeoutEvent.instance_LOOP_NO_RESTART);
	}

	/**
	 * Schedule a new timeout transition, a state was entered.
	 * 
	 * @param loopTransition
	 *            the transition was loop transition
	 * @param event
	 *            the timeout event
	 */
	private synchronized void scheduleTimeoutTransition(boolean loopTransition,
			TimeoutEvent event) {
		Transition timeoutTransition = getTransition(getActiveState(), event);

		if (timeoutTransition == null) {
			return; // Correct
		}

		TimeoutEvent timeoutEvent = (TimeoutEvent) timeoutTransition.getEvent();

		if (loopTransition
				&& timeoutEvent.getType() == TimeoutEvent.Type.LOOP_NO_RESTART) {
			return; // Correct
		}

		timer.schedule(new TimeoutTask(timeoutTransition, lastStateEnterId,
				lastStateEnterIdNonLoop), timeoutEvent.getTimeout());
	}

	/**
	 * Process the timeout transition.
	 * 
	 * @param timeoutTransition
	 *            the transition with TimeoutEvent that should be processed
	 * @param stateEnterId
	 *            the unique ID of the state enter
	 * @param stateEnterIdNonLoop
	 *            the unique ID of the non-loop state enter
	 */
	private synchronized void proccessTimeoutTransition(
			Transition timeoutTransition, Object stateEnterId,
			Object stateEnterIdNonLoop) {
		// Different state before timeout occurred -> ignore the timeout
		if (stateEnterIdNonLoop != lastStateEnterIdNonLoop) {
			return;
		}

		// Still in the same state, loop transitions occurred, but loop restarts
		// the timeout -> ignore the timeout
		if (stateEnterId != lastStateEnterId
				&& ((TimeoutEvent) timeoutTransition.getEvent()).getType() == Type.LOOP_RESTART) {
			return;
		}

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

		/** The unique ID of the state enter. */
		private final Object stateEnterId;

		/** The unique ID of the non-loop state enter. */
		private final Object stateEnterIdNonLoop;

		/**
		 * Create the object.
		 * 
		 * @param timeoutTransition
		 *            the transition with TimeoutEvent that should be processed
		 * @param stateEnterId
		 *            the unique ID of the state enter
		 * @param stateEnterIdNonLoop
		 *            the unique ID of the non-loop state enter
		 */
		public TimeoutTask(Transition timeoutTransition, Object stateEnterId,
				Object stateEnterIdNonLoop) {
			Helpers.ensureNotNull(timeoutTransition, "timeoutTransition");
			Helpers.ensureNotNull(stateEnterId, "stateEnterId");

			this.timeoutTransition = timeoutTransition;
			this.stateEnterId = stateEnterId;
			this.stateEnterIdNonLoop = stateEnterIdNonLoop;
		}

		@Override
		public void run() {
			proccessTimeoutTransition(timeoutTransition, stateEnterId,
					stateEnterIdNonLoop);
		}
	}
}
