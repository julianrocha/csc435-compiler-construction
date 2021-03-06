#
GNAME= ulGrammar
GSRC= $(GNAME).g

all: grammar compiler 

grammar: $(GSRCS)
	@java org.antlr.Tool -fo . $(GSRC) 

compiler: grammar
	@javac *.java -Xlint:unchecked -Xdiags:verbose -d classes

test: grammar compiler
	@./test_scripts/test_script_a4.sh

verify: grammar compiler
	@./test_scripts/verify_test_cases.sh

clean:
	@rm -f $(GNAME)*.java $(GNAME).tokens
	@rm -r -f ./classes/*
	@rm -f ./*.ir
	@rm -f ./*.j
	@rm -f ./*.class
	@echo "Clean!"
