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
 * Introduction to the main features of AnotherFSM library.
 * 
 * The code searches "AnotherFSM" string in the user input and exits the
 * application after it is entered. Of course this implementation is an overkill
 * solution for such simple task, remember it's only a demonstration.
 * 
 * <h3>Classes</h3>
 * 
 * <ul>
 * <li>{@link net.sourceforge.anotherfsm.examples.first.SearchFsm}
 *            Defines structure of the state machine.</li>
 * <li>{@link net.sourceforge.anotherfsm.examples.first.SearchFsmProcessor}
 *            Defines listeners for the states and transitions.</li>
 * <li>{@link net.sourceforge.anotherfsm.examples.first.FirstExample}
 *            Defines main() method, passes data to the state machine.</li>
 * </ul>
 *
 * @author Michal Turek
 */
package net.sourceforge.anotherfsm.examples.first;