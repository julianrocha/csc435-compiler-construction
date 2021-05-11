# csc435-compiler-construction
Compiler written in Java for Unnamed Language) (UL), a language that looks like C but with much less functionality.

# Dev setup
`CLASSPATH` environment variable is used to find the `antlr.jar`. I added the following to my `~/.zshrc` on Macbook and `~/.bashrc` on Linux lab machine:
```
# ~/.zshrc
export CLASSPATH=/Users/julianrocha/code/csc435-compiler-construction/antlr.jar:$CLASSPATH
# ~/.bashrc
export CLASSPATH=/home/julianrocha/Desktop/csc435-compiler-construction/antlr.jar:$CLASSPATH
``
