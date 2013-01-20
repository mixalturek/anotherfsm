#!/bin/bash
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
# One purpose script to generate offline website.

SERVER_PATH=http://localhost/~woq/anotherfsm

for file in `ls -1 web/*.php | grep -v 'p_.*php'`
do
	newFile=`echo "$file" | cut -f2 -d/ | cut -f1 -d.`.html
	echo "$SERVER_PATH/$file?offline -> build/web/$newFile"
	wget "$SERVER_PATH/$file?offline" --output-document "build/web/$newFile" -o /dev/null
done
