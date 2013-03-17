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
 * The example defines a simple state machine with <em>connected</em> and
 * <em>disconnected</em> states. The transitions are done on incoming user
 * defined and timeout events.
 * 
 * <h3>Classes and files</h3>
 * 
 * <ul>
 * <li>{@link net.sourceforge.anotherfsm.examples.timeouts.ConnectionStateEvent}
 *            User defined event that causes the transitions.</li>
 * 
 * <li>TimeoutConnection.fsm
 *            Qfsm data file, the painted diagram.</li>
 * <li>TimeoutConnection.xml
 *            Code generator configuration file.</li>
 * <li>generate.sh
 *            Shell script to execute code generator and generate the files.</li>
 * 
 * <li>{@link net.sourceforge.anotherfsm.examples.timeouts.TimeoutConnectionFsm}
 *            Defines structure of the state machine, generated file.</li>
 * <li>{@link net.sourceforge.anotherfsm.examples.timeouts.TimeoutConnectionProcessor}
 *            Defines listeners for the transitions, generated file.</li>
 * 
 * <li>{@link net.sourceforge.anotherfsm.examples.timeouts.Timeouts}
 *            Defines main() method, passes data to the state machine.</li>
 * </ul>
 *
 * @author Michal Turek
 */
package net.sourceforge.anotherfsm.examples.timeouts;