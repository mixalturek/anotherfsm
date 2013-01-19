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

// Against spam
function Email($m)
{
	$m = str_replace('@', ' (at) ', $m);
	echo "<span class=\"m\">&lt;$m&gt;</span>";
}

// Insert image
function Img($path, $alt)
{
	if(file_exists($path))
	{
		$path_big = str_replace("_sm", '', $path);
		$img = getimagesize($path);

		if($path != $path_big && file_exists($path_big))// With link to bigger
		{
			echo "<div class=\"img\"><a href=\"$path_big\"><img src=\"$path\" $img[3] alt=\"\" /><br />$alt</a></div>\n";
		}
		else
		{
			echo "<div class=\"img\"><img src=\"$path\" $img[3] alt=\"\" /><br />$alt</div>\n";
		}
	}
}

// Link to web
function Web($addr, $text, $roll = '')
{
	if(file_exists($addr.'.php'))
		echo "<a href=\"$addr".EXTENSION."$roll\">$text</a>";
	else
		echo "<span class=\"invalid_link\">$text</span>";
}

// Link to foreign website
function Blank($addr, $text = '')
{
	if($text)
		echo "<a href=\"$addr\" class=\"blank\" title=\"$addr\">$text</a>";
	else
		echo "<a href=\"$addr\" class=\"blank\">$addr</a>";
}

// Link for download file
function Down($path)
{
	if(file_exists($path))
	{
		$size = filesize($path);

		if(strlen($size) > 6)// MB
			printf("<a href=\"$path\" class=\"down\">%0.1f MB</a>", $size / 1048576);
		else if(strlen($size) > 3)// kB
			printf("<a href=\"$path\" class=\"down\">%0.1f kB</a>", $size / 1024);
		else// B
			echo "<a href=\"$path\" class=\"down\">$size B</a>";
	}
}

// Menu item
function MenuItem($addr, $text)
{
	echo (basename($_SERVER['PHP_SELF']) == "$addr.php")
		? "<span class=\"active\">$text</span>" : Web($addr, $text);
}

// Highligh examples, http://qbnz.com/highlighter/geshi-doc.html
include_once '/usr/share/php-geshi/geshi.php';
function ReadfileJava($filename)
{
	$fileContent = file_get_contents("../$filename");
	
	// Replace tabs using 4 spaces (the same tab width as defined in Eclipse)
	$fileContent = str_replace('	', '    ', $fileContent);

	$geshi = new GeSHi($fileContent, 'java');
	
	// Enable CSS
	$geshi->enable_classes();
	
	// Header text
	$geshi->set_header_content($filename);
	
	// Anotations
	$geshi->add_keyword_group('Anotation', '', true, array('@Override'));
	
	// Another FSM main classes
	$geshi->add_keyword_group('AnotherFsm', '', true, genKeywords('../src/net/sourceforge/anotherfsm/'));
	$geshi->set_url_for_keyword_group('AnotherFsm', 'doc/net/sourceforge/anotherfsm/{FNAME}.html');
	
	$geshi->add_keyword_group('AnotherFsm_logger', '', true, genKeywords('../src/net/sourceforge/anotherfsm/logger/'));
	$geshi->set_url_for_keyword_group('AnotherFsm_logger', 'doc/net/sourceforge/anotherfsm/logger/{FNAME}.html');
	
	// Disable unwanted highlighting (smaller HTML)
	$geshi->set_keyword_group_highlighting(3, false);
	$geshi->set_methods_highlighting(false);
	$geshi->set_symbols_highlighting(false);
	$geshi->set_escape_characters_highlighting(false);
	
	// Dump CSS
	// echo $geshi->get_stylesheet();
	
	echo $geshi->parse_code();
}

// Generate Another FSM keywords from .java file names
function genKeywords($directory)
{
	$keywords = array();

	if ($handle = opendir($directory))
	{
		while (false !== ($entry = readdir($handle)))
		{
			if (!is_dir("$directory/$entry"))
				$keywords[] = str_replace('.java', '', $entry);
		}

		closedir($handle);
	}

	// print_r($keywords);
	return $keywords;
}

// Display link to Javadoc of example
function ExampleJavadoc($link)
{
	echo "<p class=\"example_javadoc\">\n";
	Blank("doc_full/$link", 'Javadoc for this example.');
	echo "\n</p>\n";
}
?>