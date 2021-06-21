#!/bin/bash

echo "INVALID PROGRAMS:"
OIFS="$IFS"
IFS=$'\n'
for exception_dir in ./ul_test_cases/semantically_invalid/*; do
    expected_msg=${exception_dir##*/}
    echo "Testing : $expected_msg"
    for file in `find "${exception_dir}" -type f -name *.ul`; do
        short_file=${file##*/}
        if [[ $file =~ ^.*/([0-9]+)_.*\.ul$ ]]; then
            expected_line="${BASH_REMATCH[1]}" 
        else
            echo "BAD TEST:${short_file}:must have line number"
        fi
        OUTPUT=$(java Compiler $file) 
        if [[ $OUTPUT =~ ^Error:([0-9]+):([0-9]+):(.*)$ ]]; then
            line="${BASH_REMATCH[1]}"
            offset="${BASH_REMATCH[2]}"
            msg="${BASH_REMATCH[3]}"
            if [ "$line" != "$expected_line" ]; then
                echo "FAILED:${short_file}"
                echo "  expected line: ${expected_line}"
                echo "  actual line  : ${line}"
            fi
            if [[ ! $msg =~ $expected_msg ]]; then
                echo "FAILED:${short_file}"
                echo "  expected msg: ${expected_msg}"
                echo "  actual msg  : ${msg}"
            fi
        else
            echo "FAILED:${short_file}"
            echo "  ${OUTPUT}"
        fi 
    done
done
IFS="$OIFS"

echo "VALID PROGRAMS:"
for file in `find ./ul_test_cases/valid -type f -name *.ul`; do
    echo "$file"
    java Compiler $file
done