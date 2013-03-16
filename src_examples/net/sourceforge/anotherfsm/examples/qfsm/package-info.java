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

/**
 * This example re-implements the functionality of First example with
 * significant help of code generated from Qfsm diagram.
 * 
 * The code searches "AnotherFSM" string in the user input and exits the
 * application after it is entered. Of course this implementation is an overkill
 * solution for such simple task, remember it's only a demonstration.
 * 
 * <h3>Classes</h3>
 * 
 * <ul>
 * <li>SearchString.fsm
 *            Qfsm data file, the painted diagram.</li>
 * <li>SearchString.xml
 *            Code generator configuration file.</li>
 * 
 * <li>generate.sh
 *            Sample shell script to execute code generator.</li>
 *            
 * <li>SearchString.xml.generated
 *            Generated file, no updates.</li>
 * <li>SearchStringProcessor.java.generated
 *            Generated file, no updates.</li>
 * <li>SearchStringFsm.java.generated
 *            Generated file, no updates.</li>
 * 
 * <li>{@link net.sourceforge.anotherfsm.examples.first.SearchStringFsm}
 *            Defines structure of the state machine.</li>
 * <li>{@link net.sourceforge.anotherfsm.examples.first.SearchStringProcessor}
 *            Defines listeners for the states and transitions.</li>
 * 
 * <li>{@link net.sourceforge.anotherfsm.examples.first.Qfsm}
 *            Defines main() method, passes data to the state machine.</li>
 * </ul>
 *
 * @author Michal Turek
 */
package net.sourceforge.anotherfsm.examples.qfsm;