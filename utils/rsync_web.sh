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
# One purpose script to upload website.

# http://sourceforge.net/apps/trac/sourceforge/wiki/Project%20web
# https://sourceforge.net/apps/trac/sourceforge/wiki/Rsync%20over%20SSH

# mkdir mnt
# sshfs mixalturek@web.sourceforge.net:/home/project-web/anotherfsm mnt
# fusermount -u mnt

ant clean web
rsync -aP -e ssh build/web/ mixalturek@web.sourceforge.net:/home/project-web/anotherfsm/htdocs/
