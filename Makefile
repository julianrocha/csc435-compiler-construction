#
GNAME= ulGrammar
GSRC= $(GNAME).g

all: grammar compiler 

grammar: $(GSRCS)
	@java org.antlr.Tool -fo . $(GSRC) 

compiler:
	@javac *.java -Xlint:unchecked -Xdiags:verbose

test: grammar compiler
	@./test_scripts/test_script_a2.sh

clean:
	@rm *.class $(GNAME)*.java $(GNAME).tokens ast/*.class ast/type/*.class ast/statement/*.class ast/expression/*.class ast/visitors/*.class
