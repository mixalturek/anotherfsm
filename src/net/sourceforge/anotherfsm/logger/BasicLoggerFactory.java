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

package net.sourceforge.anotherfsm.logger;


/**
 * Factory of loggers that pass the messages to standard output and standard
 * error streams.
 * 
 * @author Michal Turek
 */
public class BasicLoggerFactory implements FsmLoggerFactory {
	@Override
	public FsmLogger getLogger(Class<?> clazz) {
		return BasicLogger.instance;
	}

	@Override
	public FsmLogger getLogger(Class<?> clazz, String instance) {
		return BasicLogger.instance;
	}

	@Override
	public FsmLogger getLogger(String name) {
		return BasicLogger.instance;
	}
}
