grammar ulGrammar;

// TODO: Consdier running locally with grun profiler for better debugging

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
        | IF OPEN_PAREN expr CLOSED_PAREN block ELSE block
        | IF OPEN_PAREN expr CLOSED_PAREN block
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
        ltExpr ( CMP_EQUAL ltExpr)* // pattern for precedence on page 61 of: http://index-of.es/Programming/Pragmatic%20Programmers/The%20Definitive%20ANTLR%20Reference.pdf
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
ltExpr:
        plusMinusExpr (LESS_THAN plusMinusExpr)*
        ;

plusMinusExpr:
        multExpr ((PLUS | MINUS) multExpr)*
        ;

multExpr:
        atom ( MULTIPLY atom)*
        ;

atom:
        ID
        | ID OPEN_PAREN exprList CLOSED_PAREN
        | ID OPEN_BRACKET expr CLOSED_BRACKET
        | literal
        | OPEN_PAREN expr CLOSED_PAREN
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

/* Boolean constants */
TRUE : 'true';
FALSE : 'false';

/* Constants NEEDS TO BE TESTED */
fragment DIGIT: ('0'..'9');
fragment CHARACTER: ('a'..'z'|'A'..'Z'|'0'..'9'|'!'|','|'.'|':'|'_'|'{'|'}'|' ');

INT_CONSTANT : DIGIT+;
FLOAT_CONSTANT : DIGIT+ '.' DIGIT+; // Assume we cannot have 0 digits on either side
CHAR_CONSTANT : '\'' CHARACTER '\'';    // Assume we cannot have empty char
STRING_CONSTANT : '"' CHARACTER+ '"'; // Assume we cannot have empty string



/* Identifiers, cannot start with digit */
ID: ('a'..'z'|'A'..'Z'|'_')('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;

/* These two lines match whitespace and comments 
 * and ignore them.
 * You want to leave these as last in the file.  
 * Add new lexical rules above 
 */
WS: ( '\t' | ' ' | ('\r' | '\n') )+ { $channel = HIDDEN;};

COMMENT: '//' ~('\r' | '\n')* ('\r' | '\n') { $channel = HIDDEN;};
