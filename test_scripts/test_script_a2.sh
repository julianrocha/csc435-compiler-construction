#!/bin/bash
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
                echo "FAILED:${short_file}:line ${expected_line} expected but line ${line} given"
            fi
            if [[ ! $msg =~ $expected_msg ]]; then
                echo "FAILED:${short_file}:msg '${expected_msg}' expected but msg '${msg}' given"
            fi
        else
            echo "FAILED:${short_file}:did not throw exception"
        fi 
    done
done
IFS="$OIFS"