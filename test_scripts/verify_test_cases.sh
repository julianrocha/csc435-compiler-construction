echo "VALID PROGRAMS:"
for ul_file in `find ./ul_test_cases/valid -type f -name *.ul`; do
    file=${ul_file%".ul"}
    file=${file#"./ul_test_cases/valid/"}
    echo $file
    java Compiler $ul_file
done