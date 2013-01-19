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

package net.sourceforge.anotherfsm.examples.loggerinjection;

import net.sourceforge.anotherfsm.AnotherFsm;
import net.sourceforge.anotherfsm.Helpers;
import net.sourceforge.anotherfsm.logger.FsmLogger;
import net.sourceforge.anotherfsm.logger.FsmLoggerFactory;

/**
 * Factory for wrapper of log4j's logger.
 * 
 * Note Another FSM supports by default the following factories/backends.
 * 
 * <ul>
 * <li>{@link net.sourceforge.anotherfsm.logger.NoLoggerFactory}
 *            Disable logging at all.</li>
 * <li>@link {@link net.sourceforge.anotherfsm.logger.StdStreamLoggerFactory}
 *            Logging to standard output and standard error output.</li>
 * <li>{@link net.sourceforge.anotherfsm.logger.JavaLoggerFactory}
 *            Logging to java.util.logging subsystem.</li>
 * </ul>
 * 
 * @author Michal Turek
 * 
 * @see AnotherFsm#setLoggerFactory(FsmLoggerFactory)
 */
public class Log4jLoggerFactory implements FsmLoggerFactory {
	@Override
	public FsmLogger getLogger(Class<?> clazz) {
		return new Log4jLogger(clazz);
	}

	@Override
	public FsmLogger getLogger(Class<?> clazz, String instance) {
		// Instance part is not supported by log4j directly
		return new Log4jLogger(clazz.getName()
				+ Helpers.CLASS_INSTANCE_DELIMITER + instance);
	}

	@Override
	public FsmLogger getLogger(String name) {
		return new Log4jLogger(name);
	}
}
