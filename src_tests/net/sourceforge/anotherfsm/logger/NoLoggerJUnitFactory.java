/*
 *  Copyright 2012 Michal Turek, AnotherFSM
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
 * Factory of empty logger for JUnit tests. Prefer to not use this class and
 * always log at least something.
 * 
 * @author Michal Turek
 */
public class NoLoggerJUnitFactory implements FsmLoggerFactory {
	@Override
	public FsmLogger getLogger(Class<?> clazz) {
		return NoLoggerJUnit.instance;
	}

	@Override
	public FsmLogger getLogger(Class<?> clazz, String instance) {
		return NoLoggerJUnit.instance;
	}

	@Override
	public FsmLogger getLogger(String name) {
		return NoLoggerJUnit.instance;
	}
}
