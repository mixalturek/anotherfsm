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

/**
 * Representation of {@code transition} XML element.
 * 
 * @author Michal Turek
 */
public class QfsmTransition {
	/** The transition type. */
	public TransitionType type;

	/** The X coordinate of the position. */
	public double xpos;

	/** The Y coordinate of the position. */
	public double ypos;

	/** The X coordinate of the end position. */
	public double endx;

	/** The X coordinate of the end position. */
	public double endy;

	/** The X coordinate of the first control point position. */
	public double c1x;

	/** The Y coordinate of the first control point position. */
	public double c1y;

	/** The X coordinate of the second control point position. */
	public double c2x;

	/** The Y coordinate of the second control point position. */
	public double c2y;

	/** The transition is drawn as straight line (not as bezier curve). */
	public boolean straight;

	/** The description of the transition. */
	public String description;

	/** The start state ID. */
	public int startStateId;

	/** The destination state ID. */
	public int destinationStateId;

	/** The input is inverted. */
	public boolean inputInvert;

	/** The input is any. */
	public boolean inputAny;

	/** The input is default. */
	public boolean inputDefault;

	/** The input information. */
	public String inputText;

	/** The output information. */
	public String outputsText;

	/**
	 * The transition type.
	 * 
	 * @author Michal Turek
	 */
	public static enum TransitionType {
		// Type of the transition. 0: Binary / 1: ASCII
		// Note 2 is undocumented in Qfsm sources
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
		public static TransitionType convert(int value) throws QfsmException {
			TransitionType[] values = values();

			if (value < 0 || value >= values.length)
				throw new QfsmException("Unsupported transition type: " + value);

			return values[value];
		}
	}
}
