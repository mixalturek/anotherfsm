/*
 *  Copyright 2013 Michal Turek, Another FSM
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
 * Example to show how to inject unsupported logger to Another FSM.
 * Log4j (http://logging.apache.org/log4j/1.2/) will be used for demonstration.
 *
 * <h3>Classes</h3>
 * 
 * <ul>
 * <li>{@link net.sourceforge.anotherfsm.examples.loggerinjection.Log4jLogger}
 *            Wrapper of log4j's logger.</li>
 * <li>{@link net.sourceforge.anotherfsm.examples.loggerinjection.Log4jLoggerFactory}
 *            Factory for wrapper of log4j's logger.</li>
 * <li>{@link net.sourceforge.anotherfsm.examples.loggerinjection.LoggerInjectionExample}
 *            Defines main() method, log sample messages using log4j wrapper.</li>
 * </ul>
 *
 * @author Michal Turek
 */
package net.sourceforge.anotherfsm.examples.loggerinjection;