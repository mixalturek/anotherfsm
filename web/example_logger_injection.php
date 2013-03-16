<?php
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

define('PAGE_TITLE', 'Logger injection example');
include_once 'p_begin.php';
?>

<?php
ExampleJavadoc('net/sourceforge/anotherfsm/examples/loggerinjection/package-summary.html');
ReadfileJava('src_examples/net/sourceforge/anotherfsm/examples/loggerinjection/package-info.java');
ReadfileJava('src_examples/net/sourceforge/anotherfsm/examples/loggerinjection/Log4jLogger.java');
ReadfileJava('src_examples/net/sourceforge/anotherfsm/examples/loggerinjection/Log4jLoggerFactory.java');
ReadfileJava('src_examples/net/sourceforge/anotherfsm/examples/loggerinjection/LoggerInjectionExample.java');
?>

<h2>Sample outputs</h2>

<pre class="example_output">
0 [main] INFO net.sourceforge.anotherfsm.examples.loggerinjection.LoggerInjectionExample  - Hello world.
13 [main] INFO net.sourceforge.anotherfsm.DeterministicStateMachine.test  - Transition started:  @INITIAL@ -> StartEvent -> state
14 [main] INFO net.sourceforge.anotherfsm.DeterministicStateMachine.test  - Transition finished: @INITIAL@ -> StartEvent -> state
14 [main] INFO net.sourceforge.anotherfsm.DeterministicStateMachine.test  - Transition started:  state -> ContainerEvent(event) -> state
14 [main] INFO net.sourceforge.anotherfsm.DeterministicStateMachine.test  - Transition finished: state -> ContainerEvent(event) -> state
14 [main] DEBUG net.sourceforge.anotherfsm.examples.loggerinjection.LoggerInjectionExample  - End of main()
</pre>

<?php
include_once 'p_end.php';
?>
