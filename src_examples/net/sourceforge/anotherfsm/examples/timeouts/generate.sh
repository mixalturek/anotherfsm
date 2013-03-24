#!/bin/bash -ex
#
# Copyright 2013 Michal Turek, AnotherFSM
#
#     http://anotherfsm.sourceforge.net/
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
# One purpose script to generate Java code from Qfsm data file.


# Example of the script execution
# pushd src_examples/net/sourceforge/anotherfsm/examples/timeouts ; ./generate.sh ; popd


# Relative path to AnotherFSM root directory
ROOT_DIR=../../../../../..

# Java classpath (development/production)
CLASSPATH=$ROOT_DIR/build/classes
# CLASSPATH=$ROOT_DIR/build/anotherfsm-*.jar 


# echo -e "\n\nGenerating XML configuration file..."
# java -classpath $CLASSPATH \
#	net.sourceforge.anotherfsm.qfsm.CodeGenerator \
#	--force \
#	--template TimeoutConnection.xml.generated


echo -e "\n\nGenerating Java code..."
java -classpath $CLASSPATH \
	net.sourceforge.anotherfsm.qfsm.CodeGenerator \
	--force \
	--config-file TimeoutConnection.xml \
	--qfsm-file TimeoutConnection.fsm
