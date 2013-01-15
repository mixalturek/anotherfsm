<?php
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

header('Content-Type: text/html; charset=utf-8');
define('EXTENSION', isset($_GET['offline']) ? '.html' : '.php');

include_once 'p_func.php';
echo '<?xml version="1.0" encoding="utf-8"?>';
?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="content-language" content="en" />

<title><?php echo PAGE_TITLE;?></title>

<style type="text/css" media="all">@import "style.css";</style>
<style type="text/css" media="all">@import "style_examples.css";</style>
<style type="text/css" media="print">@import "print.css";</style>
<link href="img/website/anotherfsm_16x16.png" rel="shortcut icon" type="image/x-icon" />
</head>

<body>

<div id="header">
<a href="http://anotherfsm.sourceforge.net/">
<pre>
   +---+   +---+   +---+   +---+   +---+   +---+   +---+           +---+   +---+   +---+
-->| <span class="state">A</span> |-->| <span class="state">n</span> |-->| <span class="state">o</span> |-->| <span class="state">t</span> |-->| <span class="state">h</span> |-->| <span class="state">e</span> |-->| <span class="state">r</span> |---------->| <span class="state">F</span> |-->| <span class="state">S</span> |-->| <span class="state">M</span> |
   +---+   +---+   +---+   +---+   +---+   +---+   +---+           +---+   +---+   +---+
                                                                  State Machines for Java
</pre>
</a>

<img id="logo" src="img/website/anotherfsm_48x48.png" width="48" height="48" alt="logo" /></div>
</div>

<?php include_once 'p_menu.php'; ?>

<div id="page">

<h1><?php echo PAGE_TITLE;?></h1>
