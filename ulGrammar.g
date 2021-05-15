grammar ulGrammar;
				
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

/*
 * This is a subset of the ulGrammar to show you how
 * to make new production rules.
 * You will need to:
 *  - change type to be compoundType and include appropriate productions
 *  - introduce optional formalParameters
 *  - change functionBody to include variable declarations and statements 
 */

program : function+ EOF
	;

function: functionDecl functionBody
	;

functionDecl: compoundType identifier '(' formalParameters? ')' // not sure about ?
	;

formalParameters: compoundType ID moreFormals*;

moreFormals: ',' compoundType ID;

functionBody: '{' varDecl* statement* '}'
	;

varDecl: compoundType ID ';';

identifier : ID
	;

compoundType    : TYPE |
                  TYPE '['INT_CONSTANT']'
	        ;

literal : INT_CONSTANT |
          STRING_CONSTANT |
          CHAR_CONSTANT |
          FLOAT_CONSTANT |
          TRUE |
          FALSE
        ;

// statment and expr productions are causing errors

statement:      ';' |
                expr ';' |
                IF '(' expr ')' block (ELSE block)? |   // not sure about ?
                WHILE '(' expr ')' block |
                PRINT expr ';' |
                PRINTLN expr ';' |
                RETURN expr? ';' |
                ID '=' expr ';' |
                ID '[' expr ']' '=' expr ';'
        ;

block: '{' statement* '}';

expr:   //expr OP expr |
        ID '[' expr ']' |
        ID '(' expr ')' |
        ID |
        literal |
        '(' expr ')'
        ;

OP:     '==' |
        '<' |
        '+' |
        '-' |
        '*'
        ;

/* Lexer */
	 
IF	: 'if';

ELSE    : 'else';

WHILE   : 'while';

PRINT   : 'print';

PRINTLN : 'println';

RETURN  : 'return';

/* Constants NEEDS TO BE TESTED */
INT_CONSTANT : ('0'..'9')+;
STRING_CONSTANT : '"' ('a'..'b'|'A'..'Z'|'0'..'9'|'!'|','|'.'|':'|'_'|'{'|'}'|' ')* '"'; // can we have empty string literal: ""
CHAR_CONSTANT : '\'' ('a'..'b'|'A'..'Z'|'0'..'9'|'!'|','|'.'|':'|'_'|'{'|'}'|' ') '\'';    // can we have empty char literal: ''
FLOAT_CONSTANT : ('0'..'9')+ '.' ('0'..'9')+;
TRUE : 'true';
FALSE : 'false';

/* Type keywords */
TYPE	: 'int' | 'float' | 'char' | 'string' | 'boolean' | 'void'
	;

/* Identifiers cannot start with digit */
ID	: ('a'..'z'|'A'..'Z'|'_')('a'..'z'|'A'..'Z'|'_'|'0'..'9')*
	;

/* These two lines match whitespace and comments 
 * and ignore them.
 * You want to leave these as last in the file.  
 * Add new lexical rules above 
 */
WS      : ( '\t' | ' ' | ('\r' | '\n') )+ { $channel = HIDDEN;}
        ;

COMMENT : '//' ~('\r' | '\n')* ('\r' | '\n') { $channel = HIDDEN;}
        ;
