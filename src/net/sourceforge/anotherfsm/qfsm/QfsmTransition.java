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
	/** The description of the transition. */
	public String description;

	/** The ID of the start state. */
	public int from;

	/** The ID of the destination state. */
	public int to;

	/** The input event. */
	public String inputsText;

	public int inputsDefault;
	public int inputsAny;
	public int inputsInvert;

	/** The output. */
	public String outputsText;

	public double c1x;
	public double c1y;
	public double c2x;
	public double c2y;

	public double xpos;
	public double ypos;
	public double endx;
	public double endy;

	public int straight;
	public int type;
}
