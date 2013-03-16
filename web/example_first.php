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

define('PAGE_TITLE', 'First example');
include_once 'p_begin.php';
?>

<?php
ExampleJavadoc('net/sourceforge/anotherfsm/examples/first/package-summary.html');
ReadfileJava('src_examples/net/sourceforge/anotherfsm/examples/first/package-info.java');
ReadfileJava('src_examples/net/sourceforge/anotherfsm/examples/first/SearchFsm.java');
ReadfileJava('src_examples/net/sourceforge/anotherfsm/examples/first/SearchFsmProcessor.java');
ReadfileJava('src_examples/net/sourceforge/anotherfsm/examples/first/FirstExample.java');
?>

<h2>Sample outputs</h2>

<pre class="example_output">
2013-01-18 18:35:56,137 [main]  INFO SearchFsmProcessor.FirstExample - Transition started:  @INITIAL@ -> StartEvent -> Start state
2013-01-18 18:35:56,144 [main]  INFO SearchFsmProcessor.FirstExample - Transition finished: @INITIAL@ -> StartEvent -> Start state
2013-01-18 18:35:56,144 [main]  INFO FirstExample - Type 'AnotherFSM' string to exit.
AnotherFSM
2013-01-18 18:36:06,265 [main]  INFO SearchFsmProcessor.FirstExample - Transition started:  Start state -> CharacterEvent(A) -> State A
2013-01-18 18:36:06,265 [main]  INFO SearchFsmProcessor.FirstExample - Character 'A' entered, good start.
2013-01-18 18:36:06,265 [main]  INFO SearchFsmProcessor.FirstExample - Transition finished: Start state -> CharacterEvent(A) -> State A
2013-01-18 18:36:06,266 [main]  INFO SearchFsmProcessor.FirstExample - Transition started:  State A -> CharacterEvent(n) -> State N
2013-01-18 18:36:06,266 [main]  INFO SearchFsmProcessor.FirstExample - Transition finished: State A -> CharacterEvent(n) -> State N
2013-01-18 18:36:06,266 [main]  INFO SearchFsmProcessor.FirstExample - Transition started:  State N -> CharacterEvent(o) -> State O
2013-01-18 18:36:06,266 [main]  INFO SearchFsmProcessor.FirstExample - Transition finished: State N -> CharacterEvent(o) -> State O
2013-01-18 18:36:06,267 [main]  INFO SearchFsmProcessor.FirstExample - Transition started:  State O -> CharacterEvent(t) -> State T
2013-01-18 18:36:06,267 [main]  INFO SearchFsmProcessor.FirstExample - Transition finished: State O -> CharacterEvent(t) -> State T
2013-01-18 18:36:06,267 [main]  INFO SearchFsmProcessor.FirstExample - Transition started:  State T -> CharacterEvent(h) -> State H
2013-01-18 18:36:06,267 [main]  INFO SearchFsmProcessor.FirstExample - Transition finished: State T -> CharacterEvent(h) -> State H
2013-01-18 18:36:06,268 [main]  INFO SearchFsmProcessor.FirstExample - Transition started:  State H -> CharacterEvent(e) -> State E
2013-01-18 18:36:06,268 [main]  INFO SearchFsmProcessor.FirstExample - Transition finished: State H -> CharacterEvent(e) -> State E
2013-01-18 18:36:06,268 [main]  INFO SearchFsmProcessor.FirstExample - Transition started:  State E -> CharacterEvent(r) -> State R
2013-01-18 18:36:06,268 [main]  INFO SearchFsmProcessor.FirstExample - Transition finished: State E -> CharacterEvent(r) -> State R
2013-01-18 18:36:06,269 [main]  INFO SearchFsmProcessor.FirstExample - Transition started:  State R -> CharacterEvent(F) -> State F
2013-01-18 18:36:06,269 [main]  INFO SearchFsmProcessor.FirstExample - Great, nearly done, please continue.
2013-01-18 18:36:06,269 [main]  INFO SearchFsmProcessor.FirstExample - Transition finished: State R -> CharacterEvent(F) -> State F
2013-01-18 18:36:06,269 [main]  INFO SearchFsmProcessor.FirstExample - Transition started:  State F -> CharacterEvent(S) -> State S
2013-01-18 18:36:06,270 [main]  INFO SearchFsmProcessor.FirstExample - Transition finished: State F -> CharacterEvent(S) -> State S
2013-01-18 18:36:06,270 [main]  INFO SearchFsmProcessor.FirstExample - Transition started:  State S -> CharacterEvent(M) -> State M
2013-01-18 18:36:06,270 [main]  INFO SearchFsmProcessor.FirstExample - Whole string successfully entered.
2013-01-18 18:36:06,270 [main]  INFO SearchFsmProcessor.FirstExample - Transition finished: State S -> CharacterEvent(M) -> State M
2013-01-18 18:36:06,271 [main] DEBUG FirstExample - 'AnotherFSM' string found in input, exiting
2013-01-18 18:36:06,271 [main] DEBUG FirstExample - End of main()
</pre>


<pre class="example_output">
2013-01-18 18:36:28,590 [main]  INFO SearchFsmProcessor.FirstExample - Transition started:  @INITIAL@ -> StartEvent -> Start state
2013-01-18 18:36:28,600 [main]  INFO SearchFsmProcessor.FirstExample - Transition finished: @INITIAL@ -> StartEvent -> Start state
2013-01-18 18:36:28,600 [main]  INFO FirstExample - Type 'AnotherFSM' string to exit.
Ano
2013-01-18 18:36:33,742 [main]  INFO SearchFsmProcessor.FirstExample - Transition started:  Start state -> CharacterEvent(A) -> State A
2013-01-18 18:36:33,743 [main]  INFO SearchFsmProcessor.FirstExample - Character 'A' entered, good start.
2013-01-18 18:36:33,743 [main]  INFO SearchFsmProcessor.FirstExample - Transition finished: Start state -> CharacterEvent(A) -> State A
2013-01-18 18:36:33,743 [main]  INFO SearchFsmProcessor.FirstExample - Transition started:  State A -> CharacterEvent(n) -> State N
2013-01-18 18:36:33,744 [main]  INFO SearchFsmProcessor.FirstExample - Transition finished: State A -> CharacterEvent(n) -> State N
2013-01-18 18:36:33,744 [main]  INFO SearchFsmProcessor.FirstExample - Transition started:  State N -> CharacterEvent(o) -> State O
2013-01-18 18:36:33,744 [main]  INFO SearchFsmProcessor.FirstExample - Transition finished: State N -> CharacterEvent(o) -> State O
2013-01-18 18:36:33,744 [main]  INFO EqualsPreprocessor.FirstExample - Event preprocessed: CharacterEvent(
) -> null
X
2013-01-18 18:36:44,280 [main]  INFO SearchFsmProcessor.FirstExample - Transition started:  State O -> CharacterEvent(X)/OtherEvent(CharacterEvent(X)) -> Start state
2013-01-18 18:36:44,281 [main]  INFO SearchFsmProcessor.FirstExample - Oh bad character, let's start again...
2013-01-18 18:36:44,281 [main]  INFO SearchFsmProcessor.FirstExample - Transition finished: State O -> CharacterEvent(X)/OtherEvent(CharacterEvent(X)) -> Start state
2013-01-18 18:36:44,281 [main]  INFO EqualsPreprocessor.FirstExample - Event preprocessed: CharacterEvent(
) -> null
Y
2013-01-18 18:36:53,689 [main]  INFO SearchFsmProcessor.FirstExample - Transition started:  Start state -> CharacterEvent(Y)/OtherEvent(CharacterEvent(Y)) -> Start state
2013-01-18 18:36:53,690 [main]  INFO SearchFsmProcessor.FirstExample - Oh bad character, let's start again...
2013-01-18 18:36:53,690 [main]  INFO SearchFsmProcessor.FirstExample - Transition finished: Start state -> CharacterEvent(Y)/OtherEvent(CharacterEvent(Y)) -> Start state
2013-01-18 18:36:53,690 [main]  INFO EqualsPreprocessor.FirstExample - Event preprocessed: CharacterEvent(
) -> null
...
</pre>


<?php
include_once 'p_end.php';
?>
