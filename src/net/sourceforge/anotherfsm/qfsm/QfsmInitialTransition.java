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
 * Representation of {@code itransition} XML element.
 * 
 * @author Michal Turek
 */
public class QfsmInitialTransition {
	/** The X coordinate of the position. */
	private double drawPosX;

	/** The Y coordinate of the position. */
	private double drawPosY;

	/** The X coordinate of the end position. */
	private double drawEndPosX;

	/** The X coordinate of the end position. */
	private double drawEndPosY;

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
}
