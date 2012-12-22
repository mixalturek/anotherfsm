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

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * The deterministic state machine.
 * 
 * @author Michal Turek
 */
class DeterministicStateMachine implements StateMachine {
	/** The logger. */
	protected final Logger logger;

	/** The name of the state machine. */
	private final String name;

	/** The transitions. */
	private final Map<State, TransitionMap> stateTransitions = new HashMap<State, TransitionMap>();

	/** The current state. */
	private State currentState = null;

	/** The listeners. */
	private final List<StateListener> stateListeners = new LinkedList<StateListener>();

	/** The listeners. */
	private final List<TransitionListener> transitionListeners = new LinkedList<TransitionListener>();

	/** The preprocessor of the events. */
	private final TypeProcessorsGroup preprocessors = new TypePreprocessors();

	/**
	 * Create the object.
	 * 
	 * @param name
	 *            the name of the state machine
	 */
	public DeterministicStateMachine(String name) {
		this.name = name;
		logger = Logger.getLogger(this.getClass() + "-" + name);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setStartState(State state) throws FsmException {
		if (currentState != null)
			throw new FsmException("Start state already set: " + currentState);

		currentState = state;
		addStateInternal(state);
	}

	@Override
	public void start() throws FsmException {
		if (currentState == null)
			throw new FsmException("Start state not defined");
	}

	@Override
	public void stop() {
		// Do nothing in this class
	}

	@Override
	public void addState(State state) throws FsmException {
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
		addStateInternal(transition.getSource());
		stateTransitions.get(transition.getSource()).addTransition(transition);
		addStateInternal(transition.getDestination());
	}

	@Override
	public void addListener(StateListener listener) {
		stateListeners.add(listener);
	}

	@Override
	public boolean removeListener(StateListener listener) {
		return stateListeners.remove(listener);
	}

	@Override
	public void addListener(TransitionListener listener) {
		transitionListeners.add(listener);
	}

	@Override
	public boolean removeListener(TransitionListener listener) {
		return transitionListeners.remove(listener);
	}

	@Override
	public void addProcessor(Class<? extends Event> clazz,
			Processor<Event> processor) throws FsmException {
		preprocessors.addProcessor(clazz, processor);
	}

	@Override
	public boolean removeProcessor(Class<? extends Event> clazz) {
		return preprocessors.removeProcessor(clazz);
	}

	public void notifyEnter(State previous, Event event, State current) {
		current.notifyEnter(previous, event, current);

		if (current.isFinalState()) {
			for (StateListener listener : stateListeners) {
				listener.onStateEnter(previous, event, current);
				listener.onFinalStateEnter(previous, event, current);
			}
		} else {
			for (StateListener listener : stateListeners) {
				listener.onStateEnter(previous, event, current);
			}
		}
	}

	public void notifyExit(State current, Event event, State next) {
		current.notifyExit(current, event, next);

		if (current.isFinalState()) {
			for (StateListener listener : stateListeners) {
				listener.onStateExit(current, event, next);
				listener.onFinalStateExit(current, event, next);
			}
		} else {
			for (StateListener listener : stateListeners) {
				listener.onStateExit(current, event, next);
			}
		}
	}

	public void notifyTransition(Transition transition, State source,
			Event event, State destination) {
		transition.notifyTransition(source, event, destination);

		for (TransitionListener listener : transitionListeners) {
			listener.onTransition(source, event, destination);
		}
	}

	@Override
	public synchronized State getActiveState() {
		return currentState;
	}

	@Override
	public synchronized Set<State> getActiveStates() {
		Set<State> states = new HashSet<State>();
		if (currentState == null)
			return states;

		states.add(currentState);
		return states;
	}

	@Override
	public synchronized Event process(Event event) {
		// Don't throw exceptions during processing to be fast

		if (currentState == null) {
			logger.error("Current/start state is null");
			return null;
		}

		if (event == null) {
			logger.warn("Event is null");
			return null;
		}

		Event preprocessedEvent = preprocessors.process(event);
		if (preprocessedEvent == null) {
			if (logger.isTraceEnabled())
				logger.trace("Event won't be processed: " + event);

			return null;
		}

		Transition transition = stateTransitions.get(currentState)
				.getTransition(preprocessedEvent);

		if (transition == null) {
			logger.warn("No such transition: " + currentState + ", " + event);
			return null;
		}

		State source = transition.getSource();
		State destination = transition.getDestination();

		String transStr = "";
		if (logger.isInfoEnabled()) {
			if (preprocessedEvent == event) {
				transStr = source + " -> " + preprocessedEvent + " -> "
						+ destination;
			} else {
				transStr = source + " -> " + preprocessedEvent + " (original "
						+ event + ")" + " -> " + destination;
			}

			logger.info("Transition processing started: " + transStr);
		}

		notifyExit(source, preprocessedEvent, destination);
		currentState = destination;
		notifyTransition(transition, source, preprocessedEvent, destination);
		notifyEnter(source, preprocessedEvent, destination);

		if (logger.isInfoEnabled()) {
			logger.info("Transition processing finished: " + transStr);
		}

		return preprocessedEvent;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" + name + ")";
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
}
