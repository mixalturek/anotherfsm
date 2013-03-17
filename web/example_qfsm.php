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

define('PAGE_TITLE', 'Qfsm example');
include_once 'p_begin.php';
?>

<p><em>Qfsm</em> is a graphical tool for designing finite state machines that is runnable on various operating systems. You can download it from its home page <?php Blank('http://qfsm.sourceforge.net/'); ?>. This example re-implements the functionality of <?php Web('example_first', 'First example')?> with significant help of code generated from Qfsm diagram.</p>

<?php
exampleJavadoc('net/sourceforge/anotherfsm/examples/qfsm/package-summary.html');
readfileJava('src_examples/net/sourceforge/anotherfsm/examples/qfsm/package-info.java');
?>


<h2>State machine diagram</h2>

<p>To paint a new state machine diagram please execute Qfsm and using menu <i>File - New</i> create a new project. Make sure you select <i>Type - Free Text</i> radio button, only this state machine type is supported by AnotherFSM code generator at the moment. You should also fill the state machine name and other items in the dialog.</p>

<?php
Img('img/examples/qfsm/01_sm.png', 'State machine diagram used in this example');
?>

<p>Ok, the diagram is finished. All states have their name, the input strings and the descriptions of transitions are properly set. You have to define the start state and optionally the final state(s). The descriptions of various items will be used for generating of Javadoc most of the time so consider to set them too. Note nearly no information from the diagram is lost during code generation.</p>


<h2>Code generation</h2>

<p>Let's generate the Java code. The code generator is bundled directly to the AnotherFSM package, it's an executable Java class. The helper shell script bellow demonstrates its usage.</p>

<?php
readFileBash('src_examples/net/sourceforge/anotherfsm/examples/qfsm/generate.sh');
?>

<p>The generated configuration XML template contains the following possibilities. All items are mandatory.</p>

<p>The generator splits state machine to two classes. The base <em>StateMachineClass</em> defines the structure of states, transitions and their connections. On the other hand the child <em>ProcessorClass</em> class defines template for listeners. It is expected only the second one will be updated manually. This is to simplify three-way-merge on state machine update and code re-generation.</p>

<?php
readFileXml('src_examples/net/sourceforge/anotherfsm/examples/qfsm/SearchString.xml.generated');
?>


<p>Java code can be generated after the default configuration is updated. The example script and execution of code generator is described above.</p>

<?php
readFileJava('src_examples/net/sourceforge/anotherfsm/examples/qfsm/SearchStringFsm.java.generated');
readFileJava('src_examples/net/sourceforge/anotherfsm/examples/qfsm/SearchStringProcessor.java.generated');
?>


<h2>Finalization</h2>

<p>Program enter and the application logic is the same as in the <?php Web('example_first', 'First example')?>. The manual update of <em>SearchStringProcessor</em> class (the listeners) consists mostly from deletion of unwanted functions, preprocessor definition and replace TODOs by real code.</p>

<?php
readFileJava('src_examples/net/sourceforge/anotherfsm/examples/qfsm/Qfsm.java');
?>

<h2>Sample output</h2>

<pre class="example_output">
2013-03-16 23:20:08,135 [main]  INFO SearchFsmProcessor.Qfsm - Transition started:  @INITIAL@ -> StartEvent -> Start state
2013-03-16 23:20:08,138 [main]  INFO SearchFsmProcessor.Qfsm - Transition finished: @INITIAL@ -> StartEvent -> Start state
2013-03-16 23:20:08,139 [main]  INFO Qfsm - Type 'AnotherFSM' string to exit.
AnotherFSM
2013-03-16 23:20:15,419 [main]  INFO SearchFsmProcessor.Qfsm - Transition started:  Start state -> CharacterEvent(A) -> State A
2013-03-16 23:20:15,419 [main]  INFO SearchFsmProcessor.Qfsm - Character 'A' entered, good start.
2013-03-16 23:20:15,420 [main]  INFO SearchFsmProcessor.Qfsm - Transition finished: Start state -> CharacterEvent(A) -> State A
2013-03-16 23:20:15,420 [main]  INFO SearchFsmProcessor.Qfsm - Transition started:  State A -> CharacterEvent(n) -> State N
2013-03-16 23:20:15,421 [main]  INFO SearchFsmProcessor.Qfsm - Transition finished: State A -> CharacterEvent(n) -> State N
2013-03-16 23:20:15,421 [main]  INFO SearchFsmProcessor.Qfsm - Transition started:  State N -> CharacterEvent(o) -> State O
2013-03-16 23:20:15,422 [main]  INFO SearchFsmProcessor.Qfsm - Transition finished: State N -> CharacterEvent(o) -> State O
2013-03-16 23:20:15,422 [main]  INFO SearchFsmProcessor.Qfsm - Transition started:  State O -> CharacterEvent(t) -> State T
2013-03-16 23:20:15,423 [main]  INFO SearchFsmProcessor.Qfsm - Transition finished: State O -> CharacterEvent(t) -> State T
2013-03-16 23:20:15,423 [main]  INFO SearchFsmProcessor.Qfsm - Transition started:  State T -> CharacterEvent(h) -> State H
2013-03-16 23:20:15,424 [main]  INFO SearchFsmProcessor.Qfsm - Transition finished: State T -> CharacterEvent(h) -> State H
2013-03-16 23:20:15,425 [main]  INFO SearchFsmProcessor.Qfsm - Transition started:  State H -> CharacterEvent(e) -> State E
2013-03-16 23:20:15,425 [main]  INFO SearchFsmProcessor.Qfsm - Transition finished: State H -> CharacterEvent(e) -> State E
2013-03-16 23:20:15,426 [main]  INFO SearchFsmProcessor.Qfsm - Transition started:  State E -> CharacterEvent(r) -> State R
2013-03-16 23:20:15,427 [main]  INFO SearchFsmProcessor.Qfsm - Transition finished: State E -> CharacterEvent(r) -> State R
2013-03-16 23:20:15,427 [main]  INFO SearchFsmProcessor.Qfsm - Transition started:  State R -> CharacterEvent(F) -> State F
2013-03-16 23:20:15,428 [main]  INFO SearchFsmProcessor.Qfsm - Great, nearly done, please continue.
2013-03-16 23:20:15,429 [main]  INFO SearchFsmProcessor.Qfsm - Transition finished: State R -> CharacterEvent(F) -> State F
2013-03-16 23:20:15,429 [main]  INFO SearchFsmProcessor.Qfsm - Transition started:  State F -> CharacterEvent(S) -> State S
2013-03-16 23:20:15,430 [main]  INFO SearchFsmProcessor.Qfsm - Transition finished: State F -> CharacterEvent(S) -> State S
2013-03-16 23:20:15,430 [main]  INFO SearchFsmProcessor.Qfsm - Transition started:  State S -> CharacterEvent(M) -> State M
2013-03-16 23:20:15,431 [main]  INFO SearchFsmProcessor.Qfsm - Whole string successfully entered.
2013-03-16 23:20:15,431 [main]  INFO SearchFsmProcessor.Qfsm - Transition finished: State S -> CharacterEvent(M) -> State M
2013-03-16 23:20:15,432 [main] DEBUG Qfsm - 'AnotherFSM' string found in input, exiting
2013-03-16 23:20:15,432 [main] DEBUG Qfsm - End of main()
</pre>


<?php
include_once 'p_end.php';
?>
