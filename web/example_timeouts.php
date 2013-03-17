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

define('PAGE_TITLE', 'Timeouts and user defined events');
include_once 'p_begin.php';
?>

<?php
exampleJavadoc('net/sourceforge/anotherfsm/examples/timeouts/package-summary.html');
readfileJava('src_examples/net/sourceforge/anotherfsm/examples/timeouts/package-info.java');
Img('img/examples/timeouts/TimeoutConnection.png', 'State machine diagram used in this example');
readfileJava('src_examples/net/sourceforge/anotherfsm/examples/timeouts/ConnectionStateEvent.java');

// Generated files
// readfileJava('src_examples/net/sourceforge/anotherfsm/examples/timeouts/TimeoutConnectionFsm.java');
// readfileJava('src_examples/net/sourceforge/anotherfsm/examples/timeouts/TimeoutConnectionProcessor.java');

readfileJava('src_examples/net/sourceforge/anotherfsm/examples/timeouts/Timeouts.java');
?>


<h2>Sample output</h2>

<pre class="example_output">
2013-03-17 17:54:50,904 [main]  INFO TimeoutConnectionProcessor.Timeouts - Transition started:  @INITIAL@ -> StartEvent -> disconnected
2013-03-17 17:54:50,906 [main]  INFO TimeoutConnectionProcessor.Timeouts - Transition finished: @INITIAL@ -> StartEvent -> disconnected
2013-03-17 17:54:50,907 [main]  INFO Timeouts - Type a command: [exit|connect|disconnect]
disconnect
2013-03-17 17:54:54,638 [main]  INFO TimeoutConnectionProcessor.Timeouts - Transition started:  disconnected -> ConnectionStateEvent(DISCONNECT) -> disconnected
2013-03-17 17:54:54,639 [main]  INFO TimeoutConnectionProcessor.Timeouts - No action
2013-03-17 17:54:54,639 [main]  INFO TimeoutConnectionProcessor.Timeouts - Transition finished: disconnected -> ConnectionStateEvent(DISCONNECT) -> disconnected
2013-03-17 17:54:54,640 [main]  INFO Timeouts - Type a command: [exit|connect|disconnect]
connect
2013-03-17 17:55:00,453 [main]  INFO TimeoutConnectionProcessor.Timeouts - Transition started:  disconnected -> ConnectionStateEvent(CONNECT) -> connected
2013-03-17 17:55:00,454 [main]  INFO TimeoutConnectionProcessor.Timeouts - Connected
2013-03-17 17:55:00,454 [main]  INFO TimeoutConnectionProcessor.Timeouts - Transition finished: disconnected -> ConnectionStateEvent(CONNECT) -> connected
2013-03-17 17:55:00,455 [main]  INFO Timeouts - Type a command: [exit|connect|disconnect]
connect
2013-03-17 17:55:02,710 [main]  INFO TimeoutConnectionProcessor.Timeouts - Transition started:  connected -> ConnectionStateEvent(CONNECT) -> connected
2013-03-17 17:55:02,710 [main]  INFO TimeoutConnectionProcessor.Timeouts - No action
2013-03-17 17:55:02,711 [main]  INFO TimeoutConnectionProcessor.Timeouts - Transition finished: connected -> ConnectionStateEvent(CONNECT) -> connected
2013-03-17 17:55:02,711 [main]  INFO Timeouts - Type a command: [exit|connect|disconnect]
2013-03-17 17:55:10,454 [TimeoutConnectionProcessor.Timeouts]  INFO TimeoutConnectionProcessor.Timeouts - Transition started:  connected -> TimeoutEvent(LOOP_NO_RESTART, 10000) -> disconnected
2013-03-17 17:55:10,455 [TimeoutConnectionProcessor.Timeouts]  INFO TimeoutConnectionProcessor.Timeouts - Time for connection expired
2013-03-17 17:55:10,456 [TimeoutConnectionProcessor.Timeouts]  INFO TimeoutConnectionProcessor.Timeouts - Transition finished: connected -> TimeoutEvent(LOOP_NO_RESTART, 10000) -> disconnected
connect
2013-03-17 17:55:13,320 [main]  INFO TimeoutConnectionProcessor.Timeouts - Transition started:  disconnected -> ConnectionStateEvent(CONNECT) -> connected
2013-03-17 17:55:13,320 [main]  INFO TimeoutConnectionProcessor.Timeouts - Connected
2013-03-17 17:55:13,321 [main]  INFO TimeoutConnectionProcessor.Timeouts - Transition finished: disconnected -> ConnectionStateEvent(CONNECT) -> connected
2013-03-17 17:55:13,321 [main]  INFO Timeouts - Type a command: [exit|connect|disconnect]
disconnect
2013-03-17 17:55:17,369 [main]  INFO TimeoutConnectionProcessor.Timeouts - Transition started:  connected -> ConnectionStateEvent(DISCONNECT) -> disconnected
2013-03-17 17:55:17,370 [main]  INFO TimeoutConnectionProcessor.Timeouts - Disconnected
2013-03-17 17:55:17,370 [main]  INFO TimeoutConnectionProcessor.Timeouts - Transition finished: connected -> ConnectionStateEvent(DISCONNECT) -> disconnected
2013-03-17 17:55:17,371 [main]  INFO Timeouts - Type a command: [exit|connect|disconnect]
2013-03-17 17:55:27,370 [TimeoutConnectionProcessor.Timeouts]  INFO TimeoutConnectionProcessor.Timeouts - Transition started:  disconnected -> TimeoutEvent(LOOP_RESTART, 10000) -> disconnected
2013-03-17 17:55:27,371 [TimeoutConnectionProcessor.Timeouts]  INFO TimeoutConnectionProcessor.Timeouts - Nobody is connected
2013-03-17 17:55:27,371 [TimeoutConnectionProcessor.Timeouts]  INFO TimeoutConnectionProcessor.Timeouts - Transition finished: disconnected -> TimeoutEvent(LOOP_RESTART, 10000) -> disconnected
not defined
2013-03-17 17:55:31,012 [main]  INFO Timeouts - Type a command: [exit|connect|disconnect]
2013-03-17 17:55:31,011 [main] ERROR Timeouts - Unknown command: not defined
2013-03-17 17:55:37,371 [TimeoutConnectionProcessor.Timeouts]  INFO TimeoutConnectionProcessor.Timeouts - Transition started:  disconnected -> TimeoutEvent(LOOP_RESTART, 10000) -> disconnected
2013-03-17 17:55:37,372 [TimeoutConnectionProcessor.Timeouts]  INFO TimeoutConnectionProcessor.Timeouts - Nobody is connected
2013-03-17 17:55:37,372 [TimeoutConnectionProcessor.Timeouts]  INFO TimeoutConnectionProcessor.Timeouts - Transition finished: disconnected -> TimeoutEvent(LOOP_RESTART, 10000) -> disconnected
exit
2013-03-17 17:55:40,066 [main]  INFO Timeouts - Exiting...
2013-03-17 17:55:40,067 [main] DEBUG Timeouts - End of main()
</pre>


<?php
include_once 'p_end.php';
?>
