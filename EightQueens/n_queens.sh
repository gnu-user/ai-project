#!/bin/bash
###############################################################################
#
# A helpful script that simply takes the arguments and executes the NQueens
# jarfile as the command exceeds the maximum length for sharcnet if done
# directly with sqsub.
# 
# Copyright (C) 2013, Jonathan Gillett
# All rights reserved.
#
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.
#
###############################################################################

java -d64 -Xms1024m -Xmx4096m -jar /home/jgillett/ai-project/EightQueens/bin/NQueens.jar "$@"
