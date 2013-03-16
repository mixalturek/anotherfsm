#!/bin/bash -ex

# pushd src_examples/net/sourceforge/anotherfsm/examples/qfsm ; ./generate.sh ; popd

ROOT_DIR=../../../../../..
CLASSPATH=$ROOT_DIR/build/classes
# CLASSPATH=$ROOT_DIR/build/anotherfsm-*.jar 

java -classpath $CLASSPATH \
	net.sourceforge.anotherfsm.qfsm.CodeGenerator \
	--force \
	--config-file SearchString.xml \
	--qfsm-file SearchString.fsm
