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

define('PAGE_TITLE', 'AnotherFSM');
include_once 'p_begin.php';
?>

<p><em>AnotherFSM</em> is yet another implementation of state machines, a library for Java programming language.</p>

<h2>Features</h2>

<ul>
<li>Deterministic state machine, optionally with <?php Web('example_timeouts', 'timeouts'); ?>.</li>
<li>Global and local listeners of transitions and state enter/exit.</li>
<li>Type-based and equals-based event preprocessors.</li>
<li>Predefined and <?php Web('example_timeouts', 'user defined events'); ?>.</li>
<li><?php Web('example_first', 'Separated state machine building, registration of listeners and processing of events.'); ?></li>
<li>Support for <?php Blank('http://qfsm.sourceforge.net/', 'Qfsm'); ?> graphical design tool with <?php Web('example_qfsm', 'code generation'); ?>.</li>
<li><?php Web('example_logger_injection', 'Generalized logging, whatever backend can be injected.')?></li>
<li>Suitable for event driven development.</li>
<li>Well-designed API, fully documented code, unit tests with high coverage.</li>
<li><?php Web('license', 'Apache 2 license, version 2.0.'); ?></li>
</ul>


<h2>News</h2>

<h3>1 April 2013</h3>
<ul>
<li>Version 0.2.0 released, <?php Web('changelog', 'ChangeLog'); ?>, <?php Blank('http://sourceforge.net/projects/anotherfsm/files/', 'download');?>.</li>
</ul>

<h3>22 January 2013</h3>
<ul>
<li>First 0.1.0 release, <?php Blank('http://sourceforge.net/projects/anotherfsm/files/', 'download');?>.</li>
</ul>

<h3>7 December 2012</h3>
<ul>
<li>Serious work on implementation started.</li>
</ul>

<h3>7 April 2012</h3>
<ul>
<li>The project registered at <?php Blank('http://www.sourceforge.net/', 'SourceForge.net'); ?>, no updates for a long time.</li>
</ul>


<h2>Similar projects</h2>

<ul>
<li>FSM part of Voice Application Framework developped by <?php Blank('http://www.ttc-marconi.com/', 'TTC MARCONI s.r.o.'); ?> as closed proprietary solution not available to public. First version of AnotherFSM is mainly its open source reimplementation, generalized, with better architecture and several new features. The VAF code was used neither in AnotherFSM nor as inspiration. There were only two years old memories at the beginning.</li>

<li><?php Blank('http://qt-project.org/doc/qt-5.0/qtcore/statemachine-api.html', 'Qt State Machine Framework'); ?>, C++.</li>
</ul>


<h2>Motivation</h2>

<p>I wanted to enjoy programming, you can enjoy it too...</p>


<?php
include_once 'p_end.php';
?>
