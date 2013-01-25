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
 * Representation of {@code state} XML element.
 * 
 * @author Michal Turek
 */
public class QfsmState {
	/** The state name. */
	public String name;

	/** The state description. */
	public String description;

	/** The state code/ID. */
	public int stateId;

	/** The moore outputs of the state. */
	public String mooreOutputs;

	/** The X coordinate of the position. */
	public int drawPosX;

	/** The Y coordinate of the position. */
	public int drawPosY;

	/** The radius of the circle. */
	public int drawRadius;

	/** The color of the state ({@code ARGB & 0x00ffffff}). */
	public int drawColor;

	/** The width of the line. */
	public int drawLineWidth;

	/** The state is final/end state. */
	public boolean finalState;
}
