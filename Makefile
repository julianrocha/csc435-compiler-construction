#
GNAME= ulGrammar
GSRC= $(GNAME).g

all: grammar compiler 

grammar: $(GSRCS)
	@java org.antlr.Tool -fo . $(GSRC) 

compiler: grammar
	@javac *.java -Xlint:unchecked -Xdiags:verbose -d classes

test: grammar compiler
	@./test_scripts/test_script_a2.sh

clean:
	@rm $(GNAME)*.java $(GNAME).tokens
	@find ./classes -name '*.class' -delete
