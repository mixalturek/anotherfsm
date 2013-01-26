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
	private String name;

	/** The state description. */
	private String description;

	/** The state code/ID. */
	private int stateId;

	/** The moore outputs of the state. */
	private String mooreOutputs;

	/** The X coordinate of the position. */
	private int drawPosX;

	/** The Y coordinate of the position. */
	private int drawPosY;

	/** The radius of the circle. */
	private int drawRadius;

	/** The color of the state ({@code ARGB & 0x00ffffff}). */
	private int drawColor;

	/** The width of the line. */
	private int drawLineWidth;

	/** The state is final/end state. */
	private boolean finalState;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public String getMooreOutputs() {
		return mooreOutputs;
	}

	public void setMooreOutputs(String mooreOutputs) {
		this.mooreOutputs = mooreOutputs;
	}

	public int getDrawPosX() {
		return drawPosX;
	}

	public void setDrawPosX(int drawPosX) {
		this.drawPosX = drawPosX;
	}

	public int getDrawPosY() {
		return drawPosY;
	}

	public void setDrawPosY(int drawPosY) {
		this.drawPosY = drawPosY;
	}

	public int getDrawRadius() {
		return drawRadius;
	}

	public void setDrawRadius(int drawRadius) {
		this.drawRadius = drawRadius;
	}

	public int getDrawColor() {
		return drawColor;
	}

	public void setDrawColor(int drawColor) {
		this.drawColor = drawColor;
	}

	public int getDrawLineWidth() {
		return drawLineWidth;
	}

	public void setDrawLineWidth(int drawLineWidth) {
		this.drawLineWidth = drawLineWidth;
	}

	public boolean isFinalState() {
		return finalState;
	}

	public void setFinalState(boolean finalState) {
		this.finalState = finalState;
	}
}
