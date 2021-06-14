package ast.visitors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ast.*;
import ast.expression.*;
import ast.statement.*;
import ast.type.*;

public class TypeCheckVisitor implements Visitor {

	private HashMap<String, FuncTypeValue> funcEnv;
	private HashMap<String, Type> varEnv;

	public TypeCheckVisitor() {
		this.funcEnv = new HashMap<String, FuncTypeValue>();
		this.varEnv = new HashMap<String, Type>();
	}

	private class FuncTypeValue {
		public Type rType;
		public List<Type> paramTypes;

		public FuncTypeValue(Type rType, List<Type> paramTypes) {
			this.rType = rType;
			this.paramTypes = paramTypes;
		}
	}

	@Override
	public Object visit(Function function) {
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
		// TODO: check for reachable return statement or statement that exists?
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

		if (!main.rType.equals(new VoidType()))
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
		// TODO Auto-generated method stub
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
		if (formalParameter.type.equals(new VoidType()))
			throw new SemanticException(formalParameter.type.line, formalParameter.type.offset,
					"parameter '" + paramName + "' can not have type 'void'");
		varEnv.put(paramName, formalParameter.type);
		return null;
	}

	@Override
	public Object visit(Identifier identifier) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(CharType type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(FloatType type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(IntegerType type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(StringType type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(VoidType type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(BooleanType booleanType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ExpressionStatement expressionStatement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(IfElseStatement ifElseStatement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(EqualityExpression equalityExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(LessThanExpression lessThanExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(SubtractExpression subtractExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(MultExpression multExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ParenExpression parenExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(AddExpression addExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(IntegerLiteral integerLiteral) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(FunctionCall functionCall) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ArrayReference arrayReference) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(StringLiteral stringLiteral) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(CharLiteral charLiteral) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(FloatLiteral floatLiteral) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(BooleanLiteral booleanLiteral) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(AssignmentStatement assignmentStatement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(IfStatement ifStatement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(PrintStatement printStatement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(WhileStatement whileStatement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(PrintlnStatement printlnStatement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ReturnStatement returnStatement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ArrayAssignmentStatement arrayAssignmentStatement) {
		// TODO Auto-generated method stub
		return null;
	}

}
