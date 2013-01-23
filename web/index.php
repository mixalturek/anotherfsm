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
<li>Deterministic state machine, optionally with timeouts.</li>
<li>Global and local listeners of transitions and state enter/exit.</li>
<li>Type-based and equals-based event preprocessors.</li>
<li>Predefined and user defined events.</li>
<li><?php Web('example_first', 'Separated state machine building, registration of listeners and processing of events.'); ?></li>
<li><?php Web('example_logger_injection', 'Generalized logging, whatever backend can be injected.')?></li>
<li><?php Web('license', 'Apache 2 license, version 2.0.'); ?></li>
<li>Well-designed API, fully documented code, unit tests.</li>
</ul>


<h2>Similar projects</h2>

<ul>
<li>FSM part of Voice Application Framework developped by <?php Blank('http://www.ttc-marconi.com/', 'TTC MARCONI s.r.o.'); ?> as closed proprietary solution not available to public. First version of AnotherFSM is mainly its open source reimplementation, generalized, with better architecture and several new features. The VAF code was used neither in AnotherFSM nor as inspiration. There were only two years old memories at the beginning.</li>

<li><?php Blank('http://doc.qt.digia.com/qt/statemachine-api.html', 'Qt State Machine Framework'); ?>, C++.</li>
</ul>


<h2>Motivation</h2>

<p>I wanted to enjoy programming, you can enjoy it too...</p>


<h2>Motivation for Czech and Slovak developers</h2>

<p><em>I'm sorry, the followign text is only in the Czech language. It would be really difficult for me to translate it and keep the meaning untouched.</em></p>

<p lang="cs">Přestalo svítit světlo. Našroubujeme novou o 10 W silnější žárovku a zkusíme rozsvítit, ale šuplík nejde otevřít a také vypadnou vchodové dveře. No jasně, abychom se dostali do šuplíku, v koupelně přece musí kapat voda!</p>

<p lang="cs">Těžce znovupostavené dveře pro tuhle chvíli spolu s kusem stěny přelepíme do kříže izolepou, snad to na chvíli vydrží, pořádně se spraví až bude čas. Takové zdržení a zdražení by nikdo nepřipustil, museli bychom zároveň vyndat v předsíni tu červenou lodičku z botníku a to je od minulého pátku tabu. Od teď sice chodíme domů oknem, je to nepohodlné, ale bydlíme v přízemí a ta trochu nepohody za opravené dveře stojí.</p>

<p lang="cs">Při každém čtyřicátém druhém příchodu domů bydlíme v tisícím sedmém patře. A také nechtějte vědět, co všechno by se stalo, kdyby při došlápnutí na parapet dokápla kapka vody do umyvadla - hlavně všechno správně načasovat. Pochybujete o svém duševním zdraví, či o duševním zdraví jiných jedinců? Naprogramujte si aspoň jednou za čas něco hezkého, z čeho můžete mít radost...</p>

<?php
include_once 'p_end.php';
?>
