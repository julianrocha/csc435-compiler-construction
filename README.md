# csc435-compiler-construction
Compiler written in Java for Unnamed Language (UL), a language that looks like C but with much less functionality.

# Dev setup
`CLASSPATH` environment variable is used to find the `antlr.jar` and for jasmin/codegen stuff. I added the following to my `~/.zshrc` on Macbook and `~/.bashrc` on Linux lab machine:
```
# ~/.zshrc
source /Users/julianrocha/code/csc435-compiler-construction/setclasspath_macbook
# ~/.bashrc
source /home/julianrocha/Desktop/csc435-compiler-construction/setclasspath_linuxbox
```

# Building, Testing, and Running
To build the compiler, run:
```
$ make
```
To deleted compiled executables, run:
```
$ make clean
```
To build and run tests, run:
```
$ make test
```
Alternatively, the tests can be run directly without re-building by running the test script directly:
```
$ ./test_script.sh
```
To compile a sample UL program, `my_program.ul` in the same directory as `Compiler.class`, first build the compiler and then run:
```
$ java Compiler my_program.ul
```