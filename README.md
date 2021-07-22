# csc435-compiler-construction
Compiler written in Java for Unnamed Language (UL), a language that looks like C but with much less functionality.

# Dev setup
`CLASSPATH` environment variable used in this project for the following purposes:
- finding compiled classes in the `classes/` dir (IMPORTANT)
- finding `antlr.jar`
- finding `codegen`/`jasmin` stuff

Make sure to add these to your class path. I added the following to my `~/.zshrc` on Macbook and `~/.bashrc` on Linux lab machine:
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
To verify and type check the test cases without compiling them to bytecode and running them, run:
```
make verify
```

To build and run tests, run:
```
$ make test
```

To compile a sample UL program, `my_program.ul`, make sure the `classes/` folder have been added to classpath then, build the compiler and then run:
```
$ java Compiler my_program.ul
```
This will ouput an `.ir` file in the same directory that the command was run from. 