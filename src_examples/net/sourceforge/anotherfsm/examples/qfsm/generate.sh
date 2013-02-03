#!/bin/bash -e

# pushd src_examples/net/sourceforge/anotherfsm/examples/qfsm; ./generate.sh ; popd

java -classpath ../../../../../../build/anotherfsm-0.2.0-dev.jar net.sourceforge.anotherfsm.qfsm.CodeGenerator \
	--verbose \
	--force \
	--config-file SearchString.xml \
	--qfsm-file SearchString.fsm
