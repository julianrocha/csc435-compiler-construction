package ast.visitors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import ast.*;
import ast.expression.*;
import ast.statement.*;
import ast.type.*;

public class TypeCheckVisitor implements Visitor {

	private HashMap<String, FuncTypeValue> funcEnv;
	private String currFunc;
	private HashMap<String, Type> varEnv;

	// Singletons
	private static BooleanType BOOLEAN_TYPE = new BooleanType();
	private static CharType CHAR_TYPE = new CharType();
	private static FloatType FLOAT_TYPE = new FloatType();
	private static IntegerType INTEGER_TYPE = new IntegerType();
	private static StringType STRING_TYPE = new StringType();
	private static VoidType VOID_TYPE = new VoidType();

	public TypeCheckVisitor() {
		this.funcEnv = new HashMap<String, FuncTypeValue>();
		this.currFunc = null;
		this.varEnv = new HashMap<String, Type>();
	}

	private Type check_binary_expr(Expression lhsExpr, Expression rhsExpr, ArrayList<Type> permittedTypes, String op,
			int line, int offset) {
		Type lhs = (Type) lhsExpr.accept(this);
		Type rhs = (Type) rhsExpr.accept(this);
		if (!lhs.equals(rhs))
			throw new SemanticException(line, offset, "both sides of '" + op + "' must be the same type");
		for (Type t : permittedTypes) {
			if (t.equals(lhs))
				return lhs;
		}
		throw new SemanticException(line, offset, "types '" + lhs.toString() + "' do not make sense with '" + op + "'");
	}

	private void visit_block(List<Statement> slist) {
		for (Statement s : slist) {
			s.accept(this);
		}
	}

	@Override
	public Object visit(Function function) {
		currFunc = function.funcDecl.id.toString();
		varEnv.clear();
		for (FormalParameter fp : function.funcDecl.formalParameterList) {
			fp.accept(this);
		}
		for (VariableDeclaration vd : function.funcBody.vlist) {
			vd.accept(this);
		}
		for (Statement s : function.funcBody.slist) {
			s.accept(this);
		}
		return null;
	}

	@Override
	public Object visit(Program program) {
		funcEnv.clear();
		for (Function f : program.funcList) {
			f.funcDecl.accept(this);
		}
		if (!funcEnv.containsKey("main"))
			throw new SemanticException(0, 0, "program must contain 'main' function");
		FuncTypeValue main = funcEnv.get("main");

		if (!main.rType.equals(VOID_TYPE))
			throw new SemanticException(main.rType.line, main.rType.offset, "'main' must have a return type of 'void'");

		if (main.paramTypes.size() != 0)
			throw new SemanticException(main.paramTypes.get(0).line, main.paramTypes.get(0).offset,
					"'main' must take no parameters");

		for (Function f : program.funcList) {
			f.accept(this);
		}
		return null;
	}

	@Override
	public Object visit(FunctionBody functionBody) {
		return null;
	}

	@Override
	public Object visit(FunctionDeclaration functionDeclaration) {
		String fName = functionDeclaration.id.toString();
		if (funcEnv.containsKey(fName))
			throw new SemanticException(functionDeclaration.id.line, functionDeclaration.id.offset,
					"previous declaration of function '" + fName + "' already exists");

		List<Type> paramTypes = new ArrayList<Type>();
		for (FormalParameter fp : functionDeclaration.formalParameterList) {
			paramTypes.add(fp.type);
		}
		funcEnv.put(fName, new FuncTypeValue(functionDeclaration.type, paramTypes));
		return null;
	}

	@Override
	public Object visit(FormalParameter formalParameter) {
		String paramName = formalParameter.id.toString();
		if (varEnv.containsKey(paramName))
			throw new SemanticException(formalParameter.id.line, formalParameter.id.offset,
					"previous declaration of parameter '" + paramName + "' already exists");
		if (formalParameter.type.equals(VOID_TYPE))
			throw new SemanticException(formalParameter.type.line, formalParameter.type.offset,
					"parameter '" + paramName + "' can not have type 'void'");
		varEnv.put(paramName, formalParameter.type);
		return null;
	}

	@Override
	public Object visit(Identifier identifier) {
		if (!varEnv.containsKey(identifier.toString()))
			throw new SemanticException(identifier.line, identifier.offset,
					"use of undeclared variable '" + identifier.toString() + "'");
		return varEnv.get(identifier.toString());
	}

	@Override
	public Object visit(VariableDeclaration variableDeclaration) {
		String varName = variableDeclaration.id.toString();
		if (varEnv.containsKey(varName))
			throw new SemanticException(variableDeclaration.id.line, variableDeclaration.id.offset,
					"previous declaration of variable '" + varName + "' already exists");
		if (variableDeclaration.type.equals(new VoidType()))
			throw new SemanticException(variableDeclaration.type.line, variableDeclaration.type.offset,
					"variable '" + varName + "' can not have type 'void'");
		varEnv.put(varName, variableDeclaration.type);
		return null;
	}

	@Override
	public Object visit(ArrayType type) {
		return null;
	}

	@Override
	public Object visit(CharType type) {
		return null;
	}

	@Override
	public Object visit(FloatType type) {
		return null;
	}

	@Override
	public Object visit(IntegerType type) {
		return null;
	}

	@Override
	public Object visit(StringType type) {
		return null;
	}

	@Override
	public Object visit(VoidType type) {
		return null;
	}

	@Override
	public Object visit(BooleanType booleanType) {
		return null;
	}

	@Override
	public Object visit(ExpressionStatement expressionStatement) {
		expressionStatement.expr.accept(this);
		return null;
	}

	@Override
	public Object visit(IfElseStatement ifElseStatement) {
		Type t = (Type) ifElseStatement.ifExpr.accept(this);
		if (!t.equals(BOOLEAN_TYPE))
			throw new SemanticException(ifElseStatement.ifExpr.line, ifElseStatement.ifExpr.offset,
					"if statement condition must have type 'boolean'");
		visit_block(ifElseStatement.ifBlock);
		visit_block(ifElseStatement.elseBlock);
		return null;
	}

	@Override
	public Object visit(EqualityExpression equalityExpression) {
		ArrayList<Type> permittedTypes = new ArrayList<Type>(
				Arrays.asList(INTEGER_TYPE, FLOAT_TYPE, CHAR_TYPE, STRING_TYPE, BOOLEAN_TYPE));
		check_binary_expr(equalityExpression.lhsExpr, equalityExpression.rhsExpr, permittedTypes, "==",
				equalityExpression.line, equalityExpression.offset);
		return BOOLEAN_TYPE;
	}

	@Override
	public Object visit(LessThanExpression lessThanExpression) {
		ArrayList<Type> permittedTypes = new ArrayList<Type>(
				Arrays.asList(INTEGER_TYPE, FLOAT_TYPE, CHAR_TYPE, STRING_TYPE));
		check_binary_expr(lessThanExpression.lhsExpr, lessThanExpression.rhsExpr, permittedTypes, "<",
				lessThanExpression.line, lessThanExpression.offset);
		return BOOLEAN_TYPE;
	}

	@Override
	public Object visit(SubtractExpression subtractExpression) {
		ArrayList<Type> permittedTypes = new ArrayList<Type>(Arrays.asList(INTEGER_TYPE, FLOAT_TYPE, CHAR_TYPE));
		Type lhs = check_binary_expr(subtractExpression.lhsExpr, subtractExpression.rhsExpr, permittedTypes, "-",
				subtractExpression.line, subtractExpression.offset);
		return lhs;
	}

	@Override
	public Object visit(MultExpression multExpression) {
		ArrayList<Type> permittedTypes = new ArrayList<Type>(Arrays.asList(INTEGER_TYPE, FLOAT_TYPE));
		Type lhs = check_binary_expr(multExpression.lhsExpr, multExpression.rhsExpr, permittedTypes, "*",
				multExpression.line, multExpression.offset);
		return lhs;
	}

	@Override
	public Object visit(ParenExpression parenExpression) {
		return parenExpression.expr.accept(this);
	}

	@Override
	public Object visit(AddExpression addExpression) {
		ArrayList<Type> permittedTypes = new ArrayList<Type>(
				Arrays.asList(INTEGER_TYPE, FLOAT_TYPE, CHAR_TYPE, STRING_TYPE));
		Type lhs = check_binary_expr(addExpression.lhsExpr, addExpression.rhsExpr, permittedTypes, "+",
				addExpression.line, addExpression.offset);
		return lhs;
	}

	@Override
	public Object visit(IntegerLiteral integerLiteral) {
		return INTEGER_TYPE;
	}

	@Override
	public Object visit(FunctionCall functionCall) {
		if (!funcEnv.containsKey(functionCall.id.toString()))
			throw new SemanticException(functionCall.id.line, functionCall.id.offset,
					"use of undeclared function '" + functionCall.id.toString() + "'");
		FuncTypeValue ft = funcEnv.get(functionCall.id.toString());
		if (functionCall.exprList.size() != ft.paramTypes.size())
			throw new SemanticException(functionCall.id.line, functionCall.id.offset,
					"function '" + functionCall.id.toString() + "' expects '" + ft.paramTypes.size()
							+ "' argument(s) but '" + functionCall.exprList.size() + "' were given");
		for (int i = 0; i < ft.paramTypes.size(); i++) {
			Type t = (Type) functionCall.exprList.get(i).accept(this);
			if (!t.equals(ft.paramTypes.get(i)))
				throw new SemanticException(functionCall.exprList.get(i).line, functionCall.exprList.get(i).offset,
						"function '" + functionCall.id.toString() + "' expects argument '" + i + "' to be type '"
								+ ft.paramTypes.get(i) + "' but type '" + t + "' was given");
		}
		return ft.rType;
	}

	@Override
	public Object visit(ArrayReference arrayReference) {
		Type t = (Type) arrayReference.expr.accept(this);
		if (!t.equals(INTEGER_TYPE))
			throw new SemanticException(arrayReference.expr.line, arrayReference.expr.offset,
					"array index must be type 'int'");
		Type idType = (Type) arrayReference.id.accept(this);
		if (idType instanceof ArrayType) {
			ArrayType aType = (ArrayType) idType;
			return aType.element_type;
		}
		throw new SemanticException(arrayReference.line, arrayReference.offset,
				"variable '" + arrayReference.id + "' of type '" + idType + "' cannot be referenced as an array");
	}

	@Override
	public Object visit(StringLiteral stringLiteral) {
		return STRING_TYPE;
	}

	@Override
	public Object visit(CharLiteral charLiteral) {
		return CHAR_TYPE;
	}

	@Override
	public Object visit(FloatLiteral floatLiteral) {
		return FLOAT_TYPE;
	}

	@Override
	public Object visit(BooleanLiteral booleanLiteral) {
		return BOOLEAN_TYPE;
	}

	@Override
	public Object visit(AssignmentStatement assignmentStatement) {
		Type lhs = (Type) assignmentStatement.id.accept(this);
		Type rhs = (Type) assignmentStatement.expr.accept(this);
		if (!lhs.equals(rhs))
			throw new SemanticException(assignmentStatement.id.line, assignmentStatement.id.offset,
					"both sides of '=' must be the same type");
		return null;
	}

	@Override
	public Object visit(IfStatement ifStatement) {
		Type t = (Type) ifStatement.expr.accept(this);
		if (!t.equals(BOOLEAN_TYPE))
			throw new SemanticException(ifStatement.expr.line, ifStatement.expr.offset,
					"if statement condition must have type 'boolean'");
		visit_block(ifStatement.block);
		return null;
	}

	@Override
	public Object visit(PrintStatement printStatement) {
		ArrayList<Type> permittedTypes = new ArrayList<Type>(
				Arrays.asList(INTEGER_TYPE, FLOAT_TYPE, CHAR_TYPE, STRING_TYPE, BOOLEAN_TYPE));
		Type printType = (Type) printStatement.expr.accept(this);
		for (Type t : permittedTypes) {
			if (printType.equals(t))
				return null;
		}
		throw new SemanticException(printStatement.expr.line, printStatement.expr.offset,
				"type '" + printType.toString() + "' does not make sense with print statement");
	}

	@Override
	public Object visit(WhileStatement whileStatement) {
		Type t = (Type) whileStatement.expr.accept(this);
		if (!t.equals(BOOLEAN_TYPE))
			throw new SemanticException(whileStatement.expr.line, whileStatement.expr.offset,
					"while statement condition must have type 'boolean'");
		visit_block(whileStatement.block);
		return null;
	}

	@Override
	public Object visit(PrintlnStatement printlnStatement) {
		ArrayList<Type> permittedTypes = new ArrayList<Type>(
				Arrays.asList(INTEGER_TYPE, FLOAT_TYPE, CHAR_TYPE, STRING_TYPE, BOOLEAN_TYPE));
		Type printType = (Type) printlnStatement.expr.accept(this);
		for (Type t : permittedTypes) {
			if (printType.equals(t))
				return null;
		}
		throw new SemanticException(printlnStatement.expr.line, printlnStatement.expr.offset,
				"type '" + printType.toString() + "' does not make sense with println statement");
	}

	@Override
	public Object visit(ReturnStatement returnStatement) {
		Type expectedRType = funcEnv.get(currFunc).rType;
		Type statementRType = returnStatement.expr == null ? VOID_TYPE : (Type) returnStatement.expr.accept(this);
		if (!expectedRType.equals(statementRType))
			throw new SemanticException(returnStatement.line, returnStatement.offset, "type mismatch, cannot return '"
					+ statementRType + "' from function with return type '" + expectedRType + "'");
		return null;
	}

	@Override
	public Object visit(ArrayAssignmentStatement arrayAssignmentStatement) {
		Type idType = (Type) arrayAssignmentStatement.id.accept(this);
		if (!(idType instanceof ArrayType))
			throw new SemanticException(arrayAssignmentStatement.id.line, arrayAssignmentStatement.id.offset,
					"variable '" + arrayAssignmentStatement.id + "' of type '" + idType
							+ "' cannot be referenced as an array");
		ArrayType arrayType = (ArrayType) idType;
		Type indexType = (Type) arrayAssignmentStatement.index_expr.accept(this);
		Type assignType = (Type) arrayAssignmentStatement.assign_expr.accept(this);
		if (!indexType.equals(INTEGER_TYPE))
			throw new SemanticException(arrayAssignmentStatement.index_expr.line,
					arrayAssignmentStatement.index_expr.offset, "array index must be type 'int'");
		if (!arrayType.element_type.equals(assignType))
			throw new SemanticException(arrayAssignmentStatement.id.line, arrayAssignmentStatement.id.offset,
					"both sides of '=' must be the same type");
		return null;
	}

}
