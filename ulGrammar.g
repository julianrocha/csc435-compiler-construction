grammar ulGrammar;

options {
	backtrack = true;
} // added backtracking as per Lecture Note

@header {
        import ast.*;
        import ast.type.*;
		import ast.statement.*;
		import ast.expression.*;
}

@members {
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
program
	returns[Program p]
	@init {List<Function> funcList = new ArrayList<Function>();}
	@after {p = new Program(funcList);}: (function {funcList.add($function.f);})+ EOF;

function
	returns[Function f]:
	functionDecl functionBody {f = new Function($functionDecl.fd, $functionBody.fb);};

functionDecl
	returns[FunctionDeclaration fd]:
	compoundType id OPEN_PAREN formalParameterList CLOSED_PAREN {
                fd = new FunctionDeclaration($compoundType.t, $id.i, $formalParameterList.fpl);
        };

id
	returns[Identifier i]:
	ID {i = new Identifier($ID.text, $ID.line, $ID.pos);};

formalParameterList
	returns[List<FormalParameter> fpl]
	@init {fpl = new ArrayList<FormalParameter>();}:
	fp1 = formalParameter {fpl.add($fp1.fp);} (
		COMMA fpi = formalParameter {fpl.add($fpi.fp);}
	)*
	|;

formalParameter
	returns[FormalParameter fp]:
	compoundType id {fp = new FormalParameter($compoundType.t, $id.i);};

functionBody
	returns[FunctionBody fb]
	@init {
                List<VariableDeclaration> vlist = new ArrayList<VariableDeclaration>();
                List<Statement> slist = new ArrayList<Statement>();
        }
	@after {fb = new FunctionBody(vlist,slist);}:
	OPEN_BRACE (varDecl {vlist.add($varDecl.decl);})* (
		statement {
			if($statement.s != null){{slist.add($statement.s);}}}
	)* CLOSED_BRACE;

varDecl
	returns[VariableDeclaration decl]:
	compoundType id SEMI_COLON {decl = new VariableDeclaration($compoundType.t, $id.i);};

compoundType
	returns[Type t]:
	type {t = $type.t;}
	| type OPEN_BRACKET INT_CONSTANT CLOSED_BRACKET {t = new ArrayType($type.t,Integer.parseInt($INT_CONSTANT.text));
		};

type
	returns[Type t]:
	INT {t = new IntegerType($INT.line,$INT.pos);}
	| FLOAT {t = new FloatType($FLOAT.line,$FLOAT.pos);}
	| CHAR {t = new CharType($CHAR.line, $CHAR.pos);}
	| STRING {t = new StringType($STRING.line, $STRING.pos);}
	| BOOLEAN {t = new BooleanType($BOOLEAN.line, $BOOLEAN.pos);}
	| VOID {t = new VoidType($VOID.line, $VOID.pos);};

statement
	returns[Statement s]:
	SEMI_COLON {s = null;}
	| exprStatement {s = $exprStatement.s;}
	| ifElseStatement {s = $ifElseStatement.s;}
	| ifStatement {s = $ifStatement.s;}
	| whileStatement {s = $whileStatement.s;}
	| printStatement {s = $printStatement.s;}
	| printlnStatement {s = $printlnStatement.s;}
	| returnStatement {s = $returnStatement.s;}
	| assignmentStatement {s = $assignmentStatement.s;}
	| arrayAssignmentStatement {s = $arrayAssignmentStatement.s;};

exprStatement
	returns[ExpressionStatement s]:
	expr SEMI_COLON {s = new ExpressionStatement($expr.e);};
ifElseStatement
	returns[IfElseStatement s]:
	IF OPEN_PAREN expr CLOSED_PAREN ifBlock = block ELSE elseBlock = block {
		s = new IfElseStatement($expr.e,$ifBlock.slist,$elseBlock.slist);
		};
ifStatement
	returns[IfStatement s]:
	IF OPEN_PAREN expr CLOSED_PAREN block;
whileStatement
	returns[WhileStatement s]:
	WHILE OPEN_PAREN expr CLOSED_PAREN block;
printStatement
	returns[PrintStatement s]: PRINT expr SEMI_COLON;
printlnStatement
	returns[PrintlnStatement s]: PRINTLN expr SEMI_COLON;
returnStatement
	returns[ReturnStatement s]: RETURN expr? SEMI_COLON;
assignmentStatement
	returns[AssignmentStatement s]:
	id ASSIGN_EQUAL expr SEMI_COLON;
arrayAssignmentStatement
	returns[ArrayAssignmentStatement s]:
	id OPEN_BRACKET expr CLOSED_BRACKET ASSIGN_EQUAL expr SEMI_COLON;

block
	returns[List<Statement> slist]
	@init {slist = new ArrayList<Statement>();}: OPEN_BRACE (statement {slist.add($statement.s);})* CLOSED_BRACE;

expr
	returns[Expression e]: eqExpr {e = $eqExpr.e;};
// pattern for precedence on page 61 of: http://index-of.es/Programming/Pragmatic%20Programmers/The%20Definitive%20ANTLR%20Reference.pdf

literal
	returns[Expression l]:
	intLiteral {l = $intLiteral.l;}
	| stringLiteral
	| charLiteral
	| floatLiteral
	| booleanLiteral;

intLiteral
	returns[IntegerLiteral l]:
	i = INT_CONSTANT {l = new IntegerLiteral(Integer.parseInt($i.text),$i.line,$i.pos);};
stringLiteral: STRING_CONSTANT;
charLiteral: CHAR_CONSTANT;
floatLiteral: FLOAT_CONSTANT;
booleanLiteral: TRUE | FALSE;

exprList
	returns[Expression e]: expr (COMMA expr)* |;

eqExpr
	returns[Expression e]
	@init {Expression tmp = null;}
	@after {e = tmp;}:
	e1 = ltExpr {tmp=$e1.e;} (
		CMP_EQUAL e2 = ltExpr {tmp = new EqualityExpression(tmp,$e2.e);}
	)*;

ltExpr
	returns[Expression e]
	@init {Expression tmp = null;}
	@after {e = tmp;}:
	e1 = plusMinusExpr {tmp=$e1.e;} (
		LESS_THAN e2 = plusMinusExpr {tmp = new LessThanExpression(tmp,$e2.e);}
	)*;

plusMinusExpr
	returns[Expression e]
	@init {Expression tmp = null;}
	@after {e = tmp;}:
	e1 = multExpr {tmp=$e1.e;} (
		(
			PLUS e2_plus = multExpr {tmp = new AddExpression(tmp,$e2_plus.e);}
			| MINUS e2_minus = multExpr {tmp = new SubtractExpression(tmp,$e2_minus.e);}
		)
	)*;

multExpr
	returns[Expression e]
	@init {Expression tmp = null;}
	@after {e = tmp;}:
	e1 = atom {tmp=$e1.e;} (
		MULTIPLY e2 = atom {tmp = new MultExpression(tmp,$e2.e);}
	)*;

atom
	returns[Expression e]:
	id
	| literal {e = $literal.l;}
	| funcCall
	| arrayRef
	| parenExpr;

funcCall: id OPEN_PAREN exprList CLOSED_PAREN;
arrayRef: id OPEN_BRACKET expr CLOSED_BRACKET;
parenExpr: OPEN_PAREN expr CLOSED_PAREN;

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
TRUE: 'true';
FALSE: 'false';

/* Constants */
fragment DIGIT: ('0' ..'9');
fragment CHARACTER: (
		'a' ..'z'
		| 'A' ..'Z'
		| '0' ..'9'
		| '!'
		| ','
		| '.'
		| ':'
		| '_'
		| '{'
		| '}'
		| ' '
	);

INT_CONSTANT: DIGIT+;
FLOAT_CONSTANT: DIGIT+ '.' DIGIT+;
// Assume we cannot have 0 digits on either side
CHAR_CONSTANT: '\'' CHARACTER '\'';
// Assume we cannot have empty char
STRING_CONSTANT: '"' CHARACTER+ '"';
// Assume we cannot have empty string

/* Identifiers, cannot start with digit */
ID: ('a' ..'z' | 'A' ..'Z' | '_') (
		'a' ..'z'
		| 'A' ..'Z'
		| '_'
		| '0' ..'9'
	)*;

/* These two lines match whitespace and comments 
 and ignore them.
 You want to leave these as
 last
 in the file. 
 Add new lexical rules above 
 */
WS: ( '\t' | ' ' | ('\r' | '\n'))+ { $channel = HIDDEN;};

COMMENT:
	'//' ~('\r' | '\n')* ('\r' | '\n') { $channel = HIDDEN;};
