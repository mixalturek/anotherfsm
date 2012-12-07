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

package net.sourceforge.anotherfsm.deterministic;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sourceforge.anotherfsm.api.Event;
import net.sourceforge.anotherfsm.api.FsmException;
import net.sourceforge.anotherfsm.api.Processor;
import net.sourceforge.anotherfsm.api.ProcessorGroup;
import net.sourceforge.anotherfsm.api.State;
import net.sourceforge.anotherfsm.api.StateListener;
import net.sourceforge.anotherfsm.api.StateMachine;
import net.sourceforge.anotherfsm.api.Transition;
import net.sourceforge.anotherfsm.api.TransitionListener;

import org.apache.log4j.Logger;

/**
 * The deterministic state machine.
 * 
 * @author Michal Turek
 */
public class DeterministicStateMachine implements StateMachine {
	/** The logger. */
	protected final Logger logger;

	/** The name of the state machine. */
	private final String name;

	/** The transitions. */
	private final Map<State, TransitionMap> stateTransitions = new HashMap<State, TransitionMap>();

	/** The current state. */
	private State currentState;

	/** The listeners. */
	private final List<StateListener> stateListeners = new LinkedList<StateListener>();

	/** The listeners. */
	private final List<TransitionListener> transitionListeners = new LinkedList<TransitionListener>();

	/** The preprocessor of the events. */
	private final ProcessorGroup preprocessors = new TypePreprocessors();

	/**
	 * Create the object.
	 * 
	 * @param name
	 */
	public DeterministicStateMachine(String name) {
		this.name = name;
		logger = Logger.getLogger(this.getClass() + "-" + name);// TODO: test
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setStartState(State state) throws FsmException {
		if (currentState != null)
			throw new FsmException("Start state has been already set: "
					+ currentState);

		currentState = state;
		addState(state);
	}

	@Override
	public void start() {
		// Do nothing in deterministic FSM
	}

	@Override
	public void stop() {
		// Do nothing in deterministic FSM
	}

	@Override
	public void addState(State state) {
		if (stateTransitions.containsKey(state))
			return;

		stateTransitions.put(state, new TransitionMap());
	}

	@Override
	public void addTransition(Transition transition) throws FsmException {
		addState(transition.getSource());
		addState(transition.getDestination());

		stateTransitions.get(transition.getSource()).addTransition(transition);
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
	public synchronized State getCurrentState() {
		return currentState;
	}

	@Override
	public synchronized Event process(Event event) throws FsmException {
		if (event == null)
			throw new FsmException("Input event is null");

		Event preprocessedEvent = preprocessors.process(event);
		if (preprocessedEvent == null) {
			if (logger.isTraceEnabled())
				logger.trace("Event won't be processed: " + event);

			return null;
		}

		Transition transition = stateTransitions.get(currentState)
				.getTransition(preprocessedEvent, currentState);

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
}
