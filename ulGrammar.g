grammar ulGrammar;

options { backtrack=true;} // added backtracking as per Lecture Note
				
@members
{
protected void mismatch (IntStream input, int ttype, BitSet follow)
        throws RecognitionException
{
        throw new MismatchedTokenException(ttype, input);
}
public Object recoverFromMismatchedSet (IntStream input,
                                      RecognitionException e,
                                      BitSet follow)
        throws RecognitionException
{
        reportError(e);
        throw e;
}
}

@rulecatch {
        catch (RecognitionException ex) {
                reportError(ex);
                throw ex;
        }
}

/* Parser Rules: */

// EOF indicates this is the start rule (root of tree)
program :
        function+ EOF
	;

function:
        functionDecl functionBody
	;

functionDecl:
        compoundType ID OPEN_PAREN formalParameters CLOSED_PAREN
	;

formalParameters:
        compoundType ID moreFormals*
        |
        ;

moreFormals:
        COMMA compoundType ID
        ;

functionBody:
        OPEN_BRACE varDecl* statement* CLOSED_BRACE
	;

varDecl:
        compoundType ID SEMI_COLON
        ;

compoundType:
        type
        | type OPEN_BRACKET INT_CONSTANT CLOSED_BRACKET
	;

type:
        INT
        | FLOAT
        | CHAR
        | STRING
        | BOOLEAN
        | VOID
        ;

statement:
        SEMI_COLON
        | expr SEMI_COLON
        | IF OPEN_PAREN expr CLOSED_PAREN block
        | IF OPEN_PAREN expr CLOSED_PAREN block ELSE block
        | WHILE OPEN_PAREN expr CLOSED_PAREN block
        | PRINT expr SEMI_COLON
        | PRINTLN expr SEMI_COLON
        | RETURN expr? SEMI_COLON
        | ID ASSIGN_EQUAL expr SEMI_COLON
        | ID OPEN_BRACKET expr CLOSED_BRACKET ASSIGN_EQUAL expr SEMI_COLON
        ;

block:
        OPEN_BRACE statement* CLOSED_BRACE
        ;

expr:  
        multExpr ( op multExpr)* // pattern to handle left-recursion https://meri-stuff.blogspot.com/2011/09/antlr-tutorial-expression-language.html
        | ID OPEN_BRACKET expr CLOSED_BRACKET
        | ID OPEN_PAREN exprList CLOSED_PAREN
        | ID
        | literal
        | OPEN_PAREN expr CLOSED_PAREN
        ;

literal:
        INT_CONSTANT
        | STRING_CONSTANT
        | CHAR_CONSTANT
        | FLOAT_CONSTANT
        | TRUE
        | FALSE
        ;

exprList:
        expr exprMore*
        |
        ;

exprMore:
        COMMA expr
        ;


/* Helper rules: */

multExpr:
        atom ( op atom)*
        ;

atom:
        ID
        | literal
        ;

// TODO: I don't think this handles precedence properly, need to test
op:
        CMP_EQUAL
        | LESS_THAN
        | PLUS
        | MINUS
        | MULTIPLY
        ;

/* Lexer: */

/* Operators */
CMP_EQUAL: '==';
LESS_THAN: '<';
PLUS: '+';
MINUS: '-';
MULTIPLY: '*';

/* Structural Keywords */
IF: 'if';
ELSE: 'else';
WHILE: 'while';
PRINT: 'print';
PRINTLN: 'println';
RETURN: 'return';

/* Type keywords */
INT: 'int';
FLOAT: 'float';
CHAR: 'char';
STRING: 'string';
BOOLEAN: 'boolean';
VOID: 'void';

/* Punctuation Symbols */
OPEN_PAREN: '(';
CLOSED_PAREN: ')';
COMMA: ',';
SEMI_COLON: ';';
ASSIGN_EQUAL: '=';
OPEN_BRACKET: '[';
CLOSED_BRACKET: ']';
OPEN_BRACE: '{';
CLOSED_BRACE: '}';

/* Constants NEEDS TO BE TESTED */
INT_CONSTANT : ('0'..'9')+;
STRING_CONSTANT : '"' ('a'..'b'|'A'..'Z'|'0'..'9'|'!'|','|'.'|':'|'_'|'{'|'}'|' ')+ '"'; // Assume we cannot have empty string
CHAR_CONSTANT : '\'' ('a'..'b'|'A'..'Z'|'0'..'9'|'!'|','|'.'|':'|'_'|'{'|'}'|' ') '\'';    // Assume we cannot have empty char
FLOAT_CONSTANT : ('0'..'9')+ '.' ('0'..'9')+; // Assume we cannot have 0 digits on either side
TRUE : 'true';
FALSE : 'false';

/* Identifiers, cannot start with digit */
ID: ('a'..'z'|'A'..'Z'|'_')('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;

/* These two lines match whitespace and comments 
 * and ignore them.
 * You want to leave these as last in the file.  
 * Add new lexical rules above 
 */
WS: ( '\t' | ' ' | ('\r' | '\n') )+ { $channel = HIDDEN;};

COMMENT: '//' ~('\r' | '\n')* ('\r' | '\n') { $channel = HIDDEN;};
