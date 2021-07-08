echo "VALID PROGRAMS:"
for file in `find ./ul_test_cases/valid -type f -name *.ul`; do
    echo "$file"
    java Compiler $file
done