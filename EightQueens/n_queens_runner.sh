#!/bin/bash
###############################################################################
#
# A helpful script that is a runner for executing a series of sharcnet jobs for
# the N Queens Problem.
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

OUTPUT_DIR=""
LOG_DIR="/scratch/jgillett/log/n_queens/"
MUTATION_RATES=(0.01 $(seq 0.05 0.05 1.0))


# Make sure the user provided the path
if [[ -z "$1" ]]
then
    echo "Usage: $0 {output directory}" 1>&2
    echo "Example: $0 n_queens/" 1>&2
    exit 1
fi

# Make sure the directories exist
if [[ -d "$1" ]]
then
    OUTPUT_DIR="$1"
else
    echo "Directory(s) provided do not exist!" 1>&2
    exit 1
fi

# Make sure the output directory has no files in it
if [[ "$(ls -A $OUTPUT_DIR)" ]]
then
     echo "Output directory contains files, choose a different directory!" 1>&2
     exit 1
fi

# Make a log directory for the sharcnet job execution logs
if [[ ! -d "${LOG_DIR}" ]]
then
    mkdir -p $LOG_DIR
fi


# Execute multiple jobs for each N Queens problem
for run_num in {1..30}
do
    for queen in {8..16}
    do
        # Execute the job for variable mutation rate
        log_file=q_${queen}_variable_${run_num}.log
        sqsub -q serial -r 2d --mpp 6G -o ${LOG_DIR}/${log_file} n_queens.sh -o "${OUTPUT_DIR}" -r ${run_num} -q ${queen} -s 0

        # Execute the job for each of the fixed mutation rates
        for rate in ${MUTATION_RATES[@]}
        do
            log_file=q_${queen}_${rate}_${run_num}.log
            sqsub -q serial -r 2d --mpp 6G -o ${LOG_DIR}/${log_file} n_queens.sh -o "${OUTPUT_DIR}" -r ${run_num} -m ${rate} -q ${queen} -s 0
        done
    done
done

