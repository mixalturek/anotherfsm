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

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * The deterministic state machine. This class is NOT thread safe.
 * 
 * @author Michal Turek
 * @see SynchronizedStateMachine
 */
public class DeterministicStateMachine extends ProcessorAdapter implements
		StateMachine {
	/** The transitions. */
	private final Map<State, TransitionMap> stateTransitions = new HashMap<State, TransitionMap>();

	/** The current state. */
	private State currentState = null;

	/** The listeners. */
	private final List<StateListener> stateListeners = new LinkedList<StateListener>();

	/** The listeners. */
	private final List<TransitionListener> transitionListeners = new LinkedList<TransitionListener>();

	/**
	 * Create the object.
	 * 
	 * @param name
	 *            the name of the state machine
	 */
	public DeterministicStateMachine(String name) {
		super(name);
	}

	@Override
	public void setStartState(State state) throws FsmException {
		Helpers.ensureNotNull(state, "start state");

		if (currentState != null)
			throw new FsmException("Start state already set: " + currentState);

		currentState = state;
		addStateInternal(state);
	}

	@Override
	public void start() throws FsmException {
		super.start();

		if (currentState == null)
			throw new FsmException("Start state not defined");

		State source = new State(INITIAL_STATE_NAME);
		Event event = new StartEvent();

		String transStr = source + Transition.TR + event + Transition.TR
				+ currentState;

		logger.info("Transition started:  " + transStr);
		notifyEnter(new State(INITIAL_STATE_NAME), new StartEvent(),
				currentState);
		logger.info("Transition finished: " + transStr);
	}

	@Override
	public void close() {
		super.close();
	}

	@Override
	public void addState(State state) throws FsmException {
		Helpers.ensureNotNull(state, "state");

		if (stateTransitions.containsKey(state))
			throw new FsmException("State redefined: " + state);

		addStateInternal(state);
	}

	/**
	 * Add a new state to the state machine. If the state is already defined, no
	 * exception will be thrown.
	 * 
	 * @param state
	 *            the state
	 */
	private void addStateInternal(State state) {
		if (stateTransitions.containsKey(state))
			return;

		stateTransitions.put(state, new TransitionMap());
	}

	@Override
	public void addTransition(Transition transition) throws FsmException {
		Helpers.ensureNotNull(transition, "transition");

		addStateInternal(transition.getSource());
		stateTransitions.get(transition.getSource()).addTransition(transition);
		addStateInternal(transition.getDestination());
	}

	@Override
	public void addListener(StateListener listener) {
		Helpers.ensureNotNull(listener, "listener");

		stateListeners.add(listener);
	}

	@Override
	public void addListener(TransitionListener listener) {
		Helpers.ensureNotNull(listener, "listener");

		transitionListeners.add(listener);
	}

	/**
	 * Process all state enter callbacks.
	 * 
	 * @param previous
	 *            the previous state
	 * @param event
	 *            the event
	 * @param current
	 *            the current state
	 * @throws FsmException
	 *             if something fails
	 */
	void notifyEnter(State previous, Event event, State current)
			throws FsmException {
		boolean loopTransition = previous.equals(current);

		try {
			current.notifyEnter(loopTransition, previous, event, current);

			for (StateListener listener : stateListeners) {
				if (loopTransition
						&& listener.getType() == StateListener.Type.LOOP_NO_PROCESS) {
					continue;
				}

				listener.onStateEnter(previous, event, current);
			}
		} catch (RuntimeException e) {
			Helpers.logExceptionInClientCallback(logger, e, event);
			throw e;
		}
	}

	/**
	 * Process all state exit callbacks.
	 * 
	 * @param current
	 *            the current state
	 * @param event
	 *            the event
	 * @param next
	 *            the next state
	 * @throws FsmException
	 *             if something fails
	 */
	void notifyExit(State current, Event event, State next) throws FsmException {
		boolean loopTransition = current.equals(next);

		try {
			current.notifyExit(loopTransition, current, event, next);

			for (StateListener listener : stateListeners) {
				if (loopTransition
						&& listener.getType() == StateListener.Type.LOOP_NO_PROCESS) {
					continue;
				}

				listener.onStateExit(current, event, next);
			}
		} catch (RuntimeException e) {
			Helpers.logExceptionInClientCallback(logger, e, event);
			throw e;
		}
	}

	/**
	 * Process all transition callbacks.
	 * 
	 * @param transition
	 *            the transition
	 * @param source
	 *            the source state
	 * @param event
	 *            the event
	 * @param destination
	 *            the destination state
	 * @throws FsmException
	 *             if something fails
	 */
	void notifyTransition(Transition transition, State source, Event event,
			State destination) throws FsmException {
		try {
			transition.notifyTransition(source, event, destination);

			for (TransitionListener listener : transitionListeners) {
				listener.onTransition(source, event, destination);

			}
		} catch (RuntimeException e) {
			Helpers.logExceptionInClientCallback(logger, e, event);
			throw e;
		}
	}

	@Override
	public State getActiveState() {
		return currentState;
	}

	@Override
	public Set<State> getActiveStates() {
		Set<State> states = new HashSet<State>();
		if (currentState == null)
			return states;

		states.add(currentState);
		return states;
	}

	@Override
	public Event process(Event event) throws FsmException {
		processCheck(event);

		Event preprocessedEvent = preprocessEvent(event);
		if (preprocessedEvent == null)
			return null;

		Transition transition = stateTransitions.get(currentState)
				.getTransition(preprocessedEvent);

		if (transition == null) {
			preprocessedEvent = new OtherEvent(preprocessedEvent);
			transition = stateTransitions.get(currentState).getTransition(
					preprocessedEvent);

			if (transition == null) {
				String msg = "No such transition: " + currentState
						+ Transition.TR + preprocessedEvent;
				logger.warn(msg);
				throw new FsmException(msg);
			}
		}

		return processInternal(transition, preprocessedEvent, event);
	}

	/**
	 * Check the input event and the internal state before processing the event.
	 * 
	 * @param event
	 *            the input event
	 * @throws FsmException
	 *             if the event can't be processed for any reason
	 */
	private void processCheck(Event event) throws FsmException {
		if (event == null) {
			String msg = "Event must not be null: " + currentState
					+ Transition.TR + "null";
			logger.error(msg);
			throw new NullPointerException(msg);
		}

		if (currentState == null) {
			String msg = "Current/start state is null: " + currentState
					+ Transition.TR + event;

			logger.error(msg);
			throw new FsmException(msg);
		}
	}

	/**
	 * Real processing of the event.
	 * 
	 * @param transition
	 *            the transition that should be processed
	 * @param eventToProcess
	 *            the event that should be processed
	 * @param matchedEvent
	 *            the event that matched, for logging purposes
	 * @return the final processed event
	 * @throws FsmException
	 *             if something fails
	 */
	private Event processInternal(Transition transition, Event eventToProcess,
			Event matchedEvent) throws FsmException {
		State source = transition.getSource();
		State destination = transition.getDestination();

		String transStr = "";
		if (logger.isInfoEnabled()) {
			// == is correct, equals may not consider an updated parameter
			if (eventToProcess == matchedEvent) {
				transStr = source + Transition.TR + eventToProcess
						+ Transition.TR + destination;
			} else {
				transStr = source + Transition.TR + matchedEvent + "/"
						+ eventToProcess + Transition.TR + destination;
			}

			logger.info("Transition started:  " + transStr);
		}

		notifyExit(source, eventToProcess, destination);
		currentState = destination;
		notifyTransition(transition, source, eventToProcess, destination);
		notifyEnter(source, eventToProcess, destination);

		if (logger.isInfoEnabled()) {
			logger.info("Transition finished: " + transStr);
		}

		return eventToProcess;
	}

	@Override
	public Set<State> getStates() {
		return stateTransitions.keySet();
	}

	@Override
	public Set<Transition> getTransitions() {
		Set<Transition> transitions = new HashSet<Transition>();

		for (Entry<State, TransitionMap> map : stateTransitions.entrySet()) {
			transitions.addAll(map.getValue().getTransitions());
		}

		return transitions;
	}

	/**
	 * Get transition of a specified event type defined for a state.
	 * 
	 * @param state
	 *            the state, it must be defined in the state machine
	 * @param event
	 *            the event
	 * @return the transition or null if not defined
	 */
	protected Transition getTransition(State state, Event event) {
		Helpers.ensureNotNull(state, "state");
		Helpers.ensureNotNull(event, "event");

		return stateTransitions.get(state).getTransition(event);
	}

	@Override
	public boolean isInFinalState() {
		return currentState.isFinalState();
	}
}
