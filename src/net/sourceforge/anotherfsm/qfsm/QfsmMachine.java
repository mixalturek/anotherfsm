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
	public String name;

	/** The version of the state machine. */
	public String version;

	/** The author of the state machine. */
	public String author;

	/** The description of the state machine. */
	public String description;

	/** The type of the state machine. */
	public MachineType type;

	/** The number of bits used to code the state. */
	public int numMooreOutputs;

	/** The effective number of encoding bits. */
	public int numEncodingBits;

	/** The number of input bits. */
	public int numInputs;

	/** The number of output bits. */
	public int numOutputs;

	/** The start/initial state ID. */
	public int startStateId;

	/** The font family name used to draw the state names. */
	public String drawStateFont;

	/** The point size of the font used to draw the state names. */
	public int drawStateFontSize;

	/**
	 * Weight of the font used to draw the state names which is one of the
	 * enumerated values from {@code QFont::Weight}.
	 * 
	 * @see {@link http://qt-project.org/doc/qt-4.8/qfont.html#Weight-enum}
	 */
	public int drawStateFontWeight;

	/** The font style used to draw the state names is italic. */
	public boolean drawStateFontItalic;

	/** The font family name used to draw the transition conditions. */
	public String drawTransitionFont;

	/** The point size of the font used to draw the transition conditions. */
	public int drawTransitionFontSize;
	/**
	 * Weight of the font used to draw the transition conditions which is one of
	 * the enumerated values from {@code QFont::Weight}.
	 * 
	 * @see {@link http://qt-project.org/doc/qt-4.8/qfont.html#Weight-enum}
	 */
	public int drawTransitionFontWeight;

	/** The font style used to draw the transition conditions is italic. */
	public int drawTransitionFontItalic;

	/** The arrow type. */
	public ArrowType draArrowType;

	/** The initial transition should be drawn. */
	public boolean drawDisplayInitialTransition;

	/** The names of the input bits. */
	public String inputNames;

	/** The names of the output bits. */
	public String outputNames;

	/** The names of the moore outputs (state coding). */
	public String outputNamesMoore;

	/** The states. */
	public List<QfsmState> states;

	/** The transitions. */
	public List<QfsmTransition> transitions;

	/** The initial transition, optional (won't be null). */
	public QfsmInitialTransition initialTransition;

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
		// Arrow type: 0: line arrow / 1: solid arrow
		LINE, SOLID;

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
