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

	/** The delimiter for logging transition. */
	protected final String TR = " -> ";

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
	private final TypeProcessors preprocessors = new TypeProcessorsImpl();

	/**
	 * Create the object.
	 * 
	 * @param name
	 *            the name of the state machine
	 */
	public DeterministicStateMachine(String name) {
		if (name == null)
			throw new NullPointerException("Name must not be null");

		this.name = name;
		logger = Logger.getLogger(this.getClass() + "-" + name);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setStartState(State state) throws FsmException {
		if (state == null)
			throw new NullPointerException("State must not be null");

		if (currentState != null)
			throw new FsmException("Start state already set: " + currentState);

		currentState = state;
		addStateInternal(state);
	}

	@Override
	public void start() throws FsmException {
		if (currentState == null)
			throw new FsmException("Start state not defined");

		notifyEnter(currentState, new StartEvent(), currentState);
	}

	@Override
	public void close() throws IOException {
		// Do nothing in this class
	}

	@Override
	public void addState(State state) throws FsmException {
		if (state == null)
			throw new NullPointerException("State must not be null");

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
		if (transition == null)
			throw new NullPointerException("Transition must not be null");

		addStateInternal(transition.getSource());
		stateTransitions.get(transition.getSource()).addTransition(transition);
		addStateInternal(transition.getDestination());
	}

	@Override
	public void addListener(StateListener listener) {
		if (listener == null)
			throw new NullPointerException("Listener must not be null");

		stateListeners.add(listener);
	}

	@Override
	public void addListener(TransitionListener listener) {
		if (listener == null)
			throw new NullPointerException("Listener must not be null");

		transitionListeners.add(listener);
	}

	@Override
	public <T extends Event> void addProcessor(Class<T> clazz,
			Processor<T> processor) throws FsmException {
		if (clazz == null)
			throw new NullPointerException("Event class must not be null");

		if (processor == null)
			throw new NullPointerException("Processor must not be null");

		preprocessors.addProcessor(clazz, processor);
	}

	void notifyEnter(State previous, Event event, State current) {
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

	void notifyExit(State current, Event event, State next) {
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

	void notifyTransition(Transition transition, State source, Event event,
			State destination) {
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
	public synchronized Event process(Event event) throws FsmException {
		if (event == null) {
			String msg = "Event must not be null: " + currentState + TR + event;
			logger.error(msg);
			throw new NullPointerException(msg);
		}

		if (currentState == null) {
			String msg = "Current/start state is null: " + currentState + TR
					+ event;

			logger.error(msg);
			throw new FsmException(msg);
		}

		// No catching of runtime exception is here, it's responsibility of the
		// preprocessor to not throw them
		Event preprocessedEvent = preprocessors.process(event);
		if (preprocessedEvent == null) {
			if (logger.isInfoEnabled()) {
				logger.info("Event only preprocessed: " + currentState + TR
						+ event);
			}

			// Null is correct, this is not error state
			return null;
		}

		Transition transition = stateTransitions.get(currentState)
				.getTransition(preprocessedEvent);

		if (transition == null) {
			String msg = "No such transition: " + currentState + TR + event;
			logger.warn(msg);
			throw new FsmException(msg);
		}

		State source = transition.getSource();
		State destination = transition.getDestination();

		String transStr = "";
		if (logger.isInfoEnabled()) {
			// == is correct, equals may not consider an updated parameter
			if (preprocessedEvent == event) {
				transStr = source + TR + preprocessedEvent + TR + destination;
			} else {
				transStr = source + TR + event + "/" + preprocessedEvent + TR
						+ destination;
			}

			logger.info("Transition started:  " + transStr);
		}

		// No catching of runtime exception is here, it's responsibility of the
		// callbacks to not throw them
		notifyExit(source, preprocessedEvent, destination);
		currentState = destination;
		notifyTransition(transition, source, preprocessedEvent, destination);
		notifyEnter(source, preprocessedEvent, destination);

		if (logger.isInfoEnabled()) {
			logger.info("Transition finished: " + transStr);
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
