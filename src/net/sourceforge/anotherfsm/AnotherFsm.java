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

import net.sourceforge.anotherfsm.logger.FsmLogger;
import net.sourceforge.anotherfsm.logger.FsmLoggerFactory;

/**
 * Factory of classes, the main class in this library. Method init() must be
 * called before use.
 * 
 * @author Michal Turek
 * 
 * @see #init(FsmLoggerFactory)
 */
public class AnotherFsm {
	/**
	 * The version of the library. The string has format
	 * "major.minor.release-note".
	 */
	public static final String VERSION = "0.1.0-dev";

	/** The delimiter of class names and instances. */
	public static final String CLASS_INSTANCE_DELIMITER = ".";

	/** The singleton instance. */
	private static AnotherFsm instance;

	/** The factory of loggers. */
	private final FsmLoggerFactory loggerFactory;

	/**
	 * Initialize the library. This method must be called before all other
	 * methods and just once.
	 * 
	 * @param loggerFactory
	 *            the logger factory that will be used for creating loggers
	 */
	public static void init(FsmLoggerFactory loggerFactory) {
		instance = new AnotherFsm(loggerFactory);
	}

	/**
	 * Get the singleton instance. The class must be initialized before the
	 * first call.
	 * 
	 * @return the singleton instance.
	 * @see #init(FsmLoggerFactory)
	 */
	public static AnotherFsm getInstance() {
		// Don't check the value, it's documented.
		return instance;
	}

	/**
	 * Create the object.
	 * 
	 * @param loggerFactory
	 *            the logger factory that will be used for creating loggers
	 */
	private AnotherFsm(FsmLoggerFactory loggerFactory) {
		if (loggerFactory == null)
			throw new NullPointerException("Logger factory must not be null");

		this.loggerFactory = loggerFactory;
	}

	/**
	 * Get logger for a specific class.
	 * 
	 * @param clazz
	 *            the class
	 * @return the logger
	 */
	public FsmLogger getLogger(Class<Object> clazz) {
		return loggerFactory.getLogger(clazz);
	}

	/**
	 * Get logger for a specific class and instance.
	 * 
	 * @param clazz
	 *            the class
	 * @param instance
	 *            the class instance
	 * @return the logger
	 */
	public FsmLogger getLogger(Class<?> clazz, String instance) {
		return loggerFactory.getLogger(clazz, instance);
	}

	/**
	 * Get logger for a specific logger with name.
	 * 
	 * @param name
	 *            the logger name
	 * @return the logger
	 */
	public FsmLogger getLogger(String loggerName) {
		return loggerFactory.getLogger(loggerName);
	}

	/**
	 * Create an instance of event processor based on the type. The object can
	 * be used in multithreaded environment.
	 * 
	 * Building of the object is NOT synchronized and should be done just in one
	 * thread. Only the processing of the events is synchronized.
	 * 
	 * @param name
	 *            the name of the event processor
	 * @return the event processors
	 */
	public TypeProcessors genTypeProcessors(String name) {
		return new TypeProcessorsImpl(name);
	}

	/**
	 * Create an instance of "all other events" event.
	 * 
	 * @return the event
	 */
	public OtherEvent genOtherEvent() {
		return OtherEventImpl.INSTANCE;
	}

	/**
	 * Create an instance of deterministic state machine. The object is NOT
	 * thread safe.
	 * 
	 * @param name
	 *            the name of the state machine
	 * @return the created state machine
	 */
	public StateMachine genDeterministicStateMachine(String name) {
		return new DeterministicStateMachine(name);
	}

	/**
	 * Create an instance of deterministic state machine for use in
	 * multithreaded environment.
	 * 
	 * Building of the object is NOT synchronized and should be done just in one
	 * thread. Only the processing of the events is synchronized.
	 * 
	 * @param name
	 *            the name of the state machine
	 * @return the created state machine
	 * 
	 * @see StateMachine#process(Event)
	 * @see StateMachine#getActiveState()
	 * @see StateMachine#getActiveStates()
	 * @see StateMachine#isInFinalState()
	 */
	public StateMachine genSynchronizedStateMachine(String name) {
		return new SynchronizedStateMachine(name);
	}

	/**
	 * Create an instance of timeout event.
	 * 
	 * @param timeout
	 *            the timeout in milliseconds, must be positive
	 * @param type
	 *            the type of the event
	 * @return the event
	 * 
	 * @see #genTimeoutStateMachine(String)
	 */
	public TimeoutEvent genTimeoutEvent(long timeout, TimeoutEvent.Type type) {
		return new TimeoutEventImpl(timeout, type);
	}

	/**
	 * Create an instance of deterministic state machine with timeouts for
	 * states. The object is thread safe and can be used in multithreaded
	 * environment.
	 * 
	 * Building of the object is NOT synchronized and should be done just in one
	 * thread. Only the processing of the events is synchronized.
	 * 
	 * Note creating of this object also executes an additional thread for a
	 * timer and generating of timeout events. Code of callbacks/listeners for
	 * timeout events is executed under this thread.
	 * 
	 * @param name
	 *            the name of the state machine
	 * @return the created state machine
	 * 
	 * @see #genTimeoutEvent(long, TimeoutEvent.Type)
	 */
	public StateMachine genTimeoutStateMachine(String name) {
		return new TimeoutStateMachine(name);
	}
}
