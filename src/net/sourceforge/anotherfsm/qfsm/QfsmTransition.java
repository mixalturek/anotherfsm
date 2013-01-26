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
	private TransitionType type;

	/** The X coordinate of the position. */
	private double drawPosX;

	/** The Y coordinate of the position. */
	private double drawPosY;

	/** The X coordinate of the end position. */
	private double drawEndPosX;

	/** The X coordinate of the end position. */
	private double drawEndPosY;

	/** The X coordinate of the first control point position. */
	private double drawBezier1PosX;

	/** The Y coordinate of the first control point position. */
	private double drawBezier1PosY;

	/** The X coordinate of the second control point position. */
	private double drawBezier2PosX;

	/** The Y coordinate of the second control point position. */
	private double drawBezier2PosY;

	/** The transition is drawn as straight line (not as bezier curve). */
	private boolean drawStraight;

	/** The description of the transition. */
	private String description;

	/** The source state ID. */
	private int sourceStateId;

	/** The destination state ID. */
	private int destinationStateId;

	/** The input is inverted. */
	private boolean inputInverted;

	/** The input is any. */
	private boolean inputAny;

	/** The input is default. */
	private boolean inputDefault;

	/** The input information. */
	private String inputEvent;

	/** The output information. */
	private String outputText;

	public TransitionType getType() {
		return type;
	}

	public void setType(TransitionType type) {
		this.type = type;
	}

	public double getDrawPosX() {
		return drawPosX;
	}

	public void setDrawPosX(double drawPosX) {
		this.drawPosX = drawPosX;
	}

	public double getDrawPosY() {
		return drawPosY;
	}

	public void setDrawPosY(double drawPosY) {
		this.drawPosY = drawPosY;
	}

	public double getDrawEndPosX() {
		return drawEndPosX;
	}

	public void setDrawEndPosX(double drawEndPosX) {
		this.drawEndPosX = drawEndPosX;
	}

	public double getDrawEndPosY() {
		return drawEndPosY;
	}

	public void setDrawEndPosY(double drawEndPosY) {
		this.drawEndPosY = drawEndPosY;
	}

	public double getDrawBezier1PosX() {
		return drawBezier1PosX;
	}

	public void setDrawBezier1PosX(double drawBezier1PosX) {
		this.drawBezier1PosX = drawBezier1PosX;
	}

	public double getDrawBezier1PosY() {
		return drawBezier1PosY;
	}

	public void setDrawBezier1PosY(double drawBezier1PosY) {
		this.drawBezier1PosY = drawBezier1PosY;
	}

	public double getDrawBezier2PosX() {
		return drawBezier2PosX;
	}

	public void setDrawBezier2PosX(double drawBezier2PosX) {
		this.drawBezier2PosX = drawBezier2PosX;
	}

	public double getDrawBezier2PosY() {
		return drawBezier2PosY;
	}

	public void setDrawBezier2PosY(double drawBezier2PosY) {
		this.drawBezier2PosY = drawBezier2PosY;
	}

	public boolean isDrawStraight() {
		return drawStraight;
	}

	public void setDrawStraight(boolean drawStraight) {
		this.drawStraight = drawStraight;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getSourceStateId() {
		return sourceStateId;
	}

	public void setSourceStateId(int sourceStateId) {
		this.sourceStateId = sourceStateId;
	}

	public int getDestinationStateId() {
		return destinationStateId;
	}

	public void setDestinationStateId(int destinationStateId) {
		this.destinationStateId = destinationStateId;
	}

	public boolean isInputInverted() {
		return inputInverted;
	}

	public void setInputInverted(boolean inputInverted) {
		this.inputInverted = inputInverted;
	}

	public boolean isInputAny() {
		return inputAny;
	}

	public void setInputAny(boolean inputAny) {
		this.inputAny = inputAny;
	}

	public boolean isInputDefault() {
		return inputDefault;
	}

	public void setInputDefault(boolean inputDefault) {
		this.inputDefault = inputDefault;
	}

	public String getInputEvent() {
		return inputEvent;
	}

	public void setInputEvent(String inputEvent) {
		this.inputEvent = inputEvent;
	}

	public String getOutputText() {
		return outputText;
	}

	public void setOutputText(String outputText) {
		this.outputText = outputText;
	}

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
