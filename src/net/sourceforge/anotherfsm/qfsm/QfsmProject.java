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
 * Representation of {@code qfsmproject} XML element.
 * 
 * @author Michal Turek
 */
// TODO: remove the class
public class QfsmProject {
	/** The application that created the file, {@code Qfsm} for Qfsm tool. */
	private String author;

	/** The version of the application that created the file. */
	private String version;

	/** The state machine. */
	private QfsmMachine machine;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public QfsmMachine getMachine() {
		return machine;
	}

	public void setMachine(QfsmMachine machine) {
		this.machine = machine;
	}
}
