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
	/** The name of the state machine diagram. */
	public String name;

	/** The description of the state machine diagram. */
	public String description;

	/** The author of the state machine diagram. */
	public String author;

	/** The version of the state machine diagram. */
	public String version;

	/** The ID of start state. */
	public int initialstate;

	/** The states. */
	public List<QfsmState> states;

	/** The transitions. */
	public List<QfsmTransition> transitions;

	/** The initial transition. */
	public QfsmInitialTransition itransition;

	public int draw_it;

	public MachineType type;
	public int arrowtype;

	public int numin;
	public int numout;
	public int numbits;
	public int nummooreout;

	public String transfont;
	public int transfontsize;
	public int transfontweight;
	public int transfontitalic;

	public String statefont;
	public int statefontsize;
	public int statefontweight;
	public int statefontitalic;

	public String inputnames;
	public String outputnames;
	public String outputnames_moore;

	/**
	 * The type of the state machine.
	 * 
	 * @author Michal Turek
	 * 
	 * @see Qfsm source code, Machine.h, Machine.type
	 */
	public static enum MachineType {
		// Type: 0: Binary / 1: ASCII / 2: Free Text
		BINARY, ASCII, FREE_TEXT;

		/**
		 * Convert an integer value to the enum.
		 * 
		 * @param value
		 *            the integer value from the data file
		 * @return the coresponding enum value
		 * @throws QfsmException
		 *             if the type is not supported
		 */
		public static MachineType convert(int value) throws QfsmException {
			MachineType[] values = values();

			if (value < 0 || value >= values.length)
				throw new QfsmException("Unsupported machine type: " + value);

			return values()[value];
		}
	}
}
