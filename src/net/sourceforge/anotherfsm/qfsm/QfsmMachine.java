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

package net.sourceforge.anotherfsm.qfsm;

import java.util.List;

/**
 * Representation of {@code machine} XML element.
 * 
 * @author Michal Turek
 */
public class QfsmMachine {
	/** The value of undefined state ID. */
	public static final int UNDEFINED_ID = -1;

	/** The name of the state machine. */
	private String name;

	/** The version of the state machine. */
	private String version;

	/** The author of the state machine. */
	private String author;

	/** The description of the state machine. */
	private String description;

	/** The type of the state machine. */
	private MachineType type;

	/** The number of bits used to code the state. */
	private int numMooreOutputs;

	/** The effective number of encoding bits. */
	private int numEncodingBits;

	/** The number of input bits. */
	private int numInputs;

	/** The number of output bits. */
	private int numOutputs;

	/**
	 * The start/initial state ID, optional.
	 * 
	 * @see #UNDEFINED_ID
	 */
	private int startStateId;

	/** The start/initial state. May be null if start state is not defined. */
	private QfsmState startState;

	/** The font family name used to draw the state names. */
	private String drawStateFont;

	/** The point size of the font used to draw the state names. */
	private int drawStateFontSize;

	/**
	 * Weight of the font used to draw the state names which is one of the
	 * enumerated values from {@code QFont::Weight}.
	 * 
	 * See http://qt-project.org/doc/qt-4.8/qfont.html#Weight-enum.
	 */
	private int drawStateFontWeight;

	/** The font style used to draw the state names is italic. */
	private boolean drawStateFontItalic;

	/** The font family name used to draw the transition conditions. */
	private String drawTransitionFont;

	/** The point size of the font used to draw the transition conditions. */
	private int drawTransitionFontSize;
	/**
	 * Weight of the font used to draw the transition conditions which is one of
	 * the enumerated values from {@code QFont::Weight}.
	 * 
	 * See http://qt-project.org/doc/qt-4.8/qfont.html#Weight-enum.
	 */
	private int drawTransitionFontWeight;

	/** The font style used to draw the transition conditions is italic. */
	private boolean drawTransitionFontItalic;

	/** The arrow type. */
	private ArrowType drawArrowType;

	/** The initial transition should be drawn. */
	private boolean drawDisplayInitialTransition;

	/** The names of the input bits. */
	private String inputNames;

	/** The names of the output bits. */
	private String outputNames;

	/** The names of the moore outputs (state coding). */
	private String outputNamesMoore;

	/** The states. */
	private List<QfsmState> states;

	/** The transitions. */
	private List<QfsmTransition> transitions;

	/**
	 * The initial transition, optional. May be null if initial transition is
	 * not defined.
	 */
	private QfsmInitialTransition initialTransition;

	/**
	 * Get a state with a specified name.
	 * 
	 * @param stateName
	 *            the name
	 * @return the state
	 * @throws QfsmException
	 *             if the state does not exist
	 */
	QfsmState getState(String stateName) throws QfsmException {
		XmlUtils.ensureNotNull(stateName, "stateName");

		for (QfsmState state : states) {
			if (stateName.equals(state.getName()))
				return state;
		}

		throw new QfsmException("State does not exist: " + stateName);
	}

	/**
	 * Get a state with a specified ID.
	 * 
	 * @param stateId
	 *            the state ID
	 * @return the state
	 * @throws QfsmException
	 *             if the state does not exist
	 */
	QfsmState getState(int stateId) throws QfsmException {
		for (QfsmState state : states) {
			if (stateId == state.getStateId())
				return state;
		}

		throw new QfsmException("State does not exist: " + stateId);
	}

	QfsmTransition getTransition(int startStateId, String inputEvent,
			int destinationStateId) throws QfsmException {
		XmlUtils.ensureNotNull(inputEvent, "inputText");

		for (QfsmTransition transition : transitions) {
			if (startStateId == transition.getSourceStateId()
					&& inputEvent.equals(transition.getInputEvent())
					&& destinationStateId == transition.getDestinationStateId()) {
				return transition;
			}
		}

		throw new QfsmException("State does not exist: " + startStateId + ", "
				+ inputEvent + ", " + destinationStateId);
	}

	/**
	 * Evaluate state IDs to object references.
	 * 
	 * @throws QfsmException
	 *             if some of the states does not exist
	 */
	void evaluateStates() throws QfsmException {
		if (startStateId == UNDEFINED_ID)
			startState = null;
		else
			startState = getState(startStateId);

		for (QfsmTransition transition : transitions) {
			transition.setSourceState(getState(transition.getSourceStateId()));
			transition.setDestinationState(getState(transition
					.getDestinationStateId()));
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MachineType getType() {
		return type;
	}

	public void setType(MachineType type) {
		this.type = type;
	}

	public int getNumMooreOutputs() {
		return numMooreOutputs;
	}

	public void setNumMooreOutputs(int numMooreOutputs) {
		this.numMooreOutputs = numMooreOutputs;
	}

	public int getNumEncodingBits() {
		return numEncodingBits;
	}

	public void setNumEncodingBits(int numEncodingBits) {
		this.numEncodingBits = numEncodingBits;
	}

	public int getNumInputs() {
		return numInputs;
	}

	public void setNumInputs(int numInputs) {
		this.numInputs = numInputs;
	}

	public int getNumOutputs() {
		return numOutputs;
	}

	public void setNumOutputs(int numOutputs) {
		this.numOutputs = numOutputs;
	}

	public int getStartStateId() {
		return startStateId;
	}

	public void setStartStateId(int startStateId) {
		this.startStateId = startStateId;
	}

	public QfsmState getStartState() {
		return startState;
	}

	public void setStartState(QfsmState startState) {
		this.startState = startState;
	}

	public String getDrawStateFont() {
		return drawStateFont;
	}

	public void setDrawStateFont(String drawStateFont) {
		this.drawStateFont = drawStateFont;
	}

	public int getDrawStateFontSize() {
		return drawStateFontSize;
	}

	public void setDrawStateFontSize(int drawStateFontSize) {
		this.drawStateFontSize = drawStateFontSize;
	}

	public int getDrawStateFontWeight() {
		return drawStateFontWeight;
	}

	public void setDrawStateFontWeight(int drawStateFontWeight) {
		this.drawStateFontWeight = drawStateFontWeight;
	}

	public boolean isDrawStateFontItalic() {
		return drawStateFontItalic;
	}

	public void setDrawStateFontItalic(boolean drawStateFontItalic) {
		this.drawStateFontItalic = drawStateFontItalic;
	}

	public String getDrawTransitionFont() {
		return drawTransitionFont;
	}

	public void setDrawTransitionFont(String drawTransitionFont) {
		this.drawTransitionFont = drawTransitionFont;
	}

	public int getDrawTransitionFontSize() {
		return drawTransitionFontSize;
	}

	public void setDrawTransitionFontSize(int drawTransitionFontSize) {
		this.drawTransitionFontSize = drawTransitionFontSize;
	}

	public int getDrawTransitionFontWeight() {
		return drawTransitionFontWeight;
	}

	public void setDrawTransitionFontWeight(int drawTransitionFontWeight) {
		this.drawTransitionFontWeight = drawTransitionFontWeight;
	}

	public boolean isDrawTransitionFontItalic() {
		return drawTransitionFontItalic;
	}

	public void setDrawTransitionFontItalic(boolean drawTransitionFontItalic) {
		this.drawTransitionFontItalic = drawTransitionFontItalic;
	}

	public ArrowType getDrawArrowType() {
		return drawArrowType;
	}

	public void setDrawArrowType(ArrowType drawArrowType) {
		this.drawArrowType = drawArrowType;
	}

	public boolean isDrawDisplayInitialTransition() {
		return drawDisplayInitialTransition;
	}

	public void setDrawDisplayInitialTransition(
			boolean drawDisplayInitialTransition) {
		this.drawDisplayInitialTransition = drawDisplayInitialTransition;
	}

	public String getInputNames() {
		return inputNames;
	}

	public void setInputNames(String inputNames) {
		this.inputNames = inputNames;
	}

	public String getOutputNames() {
		return outputNames;
	}

	public void setOutputNames(String outputNames) {
		this.outputNames = outputNames;
	}

	public String getOutputNamesMoore() {
		return outputNamesMoore;
	}

	public void setOutputNamesMoore(String outputNamesMoore) {
		this.outputNamesMoore = outputNamesMoore;
	}

	public List<QfsmState> getStates() {
		return states;
	}

	public void setStates(List<QfsmState> states) {
		this.states = states;
	}

	public List<QfsmTransition> getTransitions() {
		return transitions;
	}

	public void setTransitions(List<QfsmTransition> transitions) {
		this.transitions = transitions;
	}

	public QfsmInitialTransition getInitialTransition() {
		return initialTransition;
	}

	public void setInitialTransition(QfsmInitialTransition initialTransition) {
		this.initialTransition = initialTransition;
	}

	/**
	 * The state machine type.
	 * 
	 * @author Michal Turek
	 */
	public static enum MachineType {
		// Type: 0: Binary / 1: ASCII / 2: Free Text
		BINARY, ASCII, FREE_TEXT;

		/**
		 * Convert an integer value to the enum.
		 * 
		 * @param value
		 *            the integer value from the data file
		 * @return the corresponding enum value
		 * @throws QfsmException
		 *             if the value is not supported
		 */
		public static MachineType convert(int value) throws QfsmException {
			MachineType[] values = values();

			if (value < 0 || value >= values.length)
				throw new QfsmException("Unsupported machine type: " + value);

			return values[value];
		}
	}

	/**
	 * The arrow type.
	 * 
	 * @author Michal Turek
	 */
	public static enum ArrowType {
		// Arrow type: 0: line arrow / 1: solid arrow,
		// MachinePropertiesDlgImpl.cpp
		LINE, FILLED, WHITE, FILLED_POINTED, WHITE_POINTED;

		/**
		 * Convert an integer value to the enum.
		 * 
		 * @param value
		 *            the integer value from the data file
		 * @return the corresponding enum value
		 * @throws QfsmException
		 *             if the value is not supported
		 */
		public static ArrowType convert(int value) throws QfsmException {
			ArrowType[] values = values();

			if (value < 0 || value >= values.length)
				throw new QfsmException("Unsupported arrow type: " + value);

			return values[value];
		}
	}
}
