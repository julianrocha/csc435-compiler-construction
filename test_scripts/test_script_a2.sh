#!/bin/bash

echo "semantically_invalid: should output descriptive error message"
for test_file in ./ul_test_cases/semantically_invalid/*; do
    echo "$test_file"
    java Compiler $test_file
    echo ""
done

echo ""
echo "valid: should output nothing"
for test_file in ./ul_test_cases/valid/*; do
    echo "$test_file"
    java Compiler $test_file
done