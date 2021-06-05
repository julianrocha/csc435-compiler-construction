#!/bin/bash

echo "grammatically_invalid: should output lexical error"
for test_file in ./ul_test_cases/grammatically_invalid/*; do
    echo "$test_file"
    java Compiler $test_file
    echo ""
done

for test_cases in semantically_invalid valid; do
    echo "$test_cases: should output nothing"
    for test_file in ./ul_test_cases/$test_cases/*; do
        echo "$test_file"
        diff <(java Compiler $test_file) $test_file -c
    done
    echo ""
done

echo "valid_not_pretty"
for test_file in ./ul_test_cases/valid_not_pretty/*; do
    echo "$test_file: should output pretty formatted program"
    java Compiler $test_file
    echo ""
done