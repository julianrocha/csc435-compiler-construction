#
GNAME= ulGrammar
GSRC= $(GNAME).g

all: grammar compiler test

grammar: $(GSRCS)
	@java org.antlr.Tool -fo . $(GSRC) 

compiler:
	@javac *.java -Xlint:unchecked -Xdiags:verbose


GRAMMATICALLY_INVALID_FILES := $(shell ls ./ul_test_cases/grammatically_invalid/)
SEMANTICALLY_INVALID_FILES := $(shell ls ./ul_test_cases/semantically_invalid/)
VALID_FILES := $(shell ls ./ul_test_cases/valid/)

test: grammar compiler
	@echo "\nGRAMMATICALLY INVALID:\n"
	@for file in $(GRAMMATICALLY_INVALID_FILES); do \
		echo $$file ; \
		java Compiler ul_test_cases/grammatically_invalid/$$file ; \
		echo "" ; \
	done
	@echo "\nSEMANTICALLY INVALID:\n"
	@for file in $(SEMANTICALLY_INVALID_FILES); do \
		echo $$file ; \
		java Compiler ul_test_cases/semantically_invalid/$$file ; \
		echo "" ; \
	done
	@echo "\nVALID:\n"
		@for file in $(VALID_FILES); do \
		echo $$file ; \
		java Compiler ul_test_cases/valid/$$file ; \
		echo "" ; \
	done

clean:
	@rm *.class $(GNAME)*.java $(GNAME).tokens

print: grammar compiler
	@java Compiler ul_test_cases/valid/just_main.ul
 
