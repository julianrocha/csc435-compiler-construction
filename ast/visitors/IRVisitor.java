package ast.visitors;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ast.*;
import ast.expression.*;
import ast.statement.*;
import ast.type.*;
import ir.*;
import ir.TempVar.TempSet;
import ir.irinstruction.IRCallInstruction;
import ir.irinstruction.IRConditionalGotoInstruction;
import ir.irinstruction.IRGotoInstruction;
import ir.irinstruction.IRInstruction;
import ir.irinstruction.IRLabelInstruction;
import ir.irinstruction.IRReturnInstruction;
import ir.irinstruction.PrintIRInstruction;
import ir.irinstruction.PrintlnIRInstruction;
import ir.irinstruction.assignment.IRArrayAssign;
import ir.irinstruction.assignment.IRArrayInit;
import ir.irinstruction.assignment.IRBinaryOp;
import ir.irinstruction.assignment.IRConstantAssign;
import ir.irinstruction.assignment.IRTempAssign;
import ir.irinstruction.assignment.IRTempAssignArrayRef;
import ir.irinstruction.assignment.IRUnaryOp;

public class IRVisitor implements Visitor {

	private TempAllocator tempAllocator;
	private LabelAllocator labelAllocator;
	private List<IRInstruction> instrList;
	private IRProgram irProgram;
	private HashMap<String, TempVar> varEnv;
	private HashMap<String, FuncTypeValue> funcEnv;
	private FuncTypeValue currentFtv;

	// Singletons
	private static BooleanType BOOLEAN_TYPE = new BooleanType();
	private static CharType CHAR_TYPE = new CharType();
	private static FloatType FLOAT_TYPE = new FloatType();
	private static IntegerType INTEGER_TYPE = new IntegerType();
	private static StringType STRING_TYPE = new StringType();
	private static VoidType VOID_TYPE = new VoidType();

	public IRVisitor(String programName) {
		this.irProgram = new IRProgram(programName);
		this.varEnv = new HashMap<String, TempVar>();
		this.funcEnv = new HashMap<String, FuncTypeValue>();
	}

	public void printInstructions() {
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(irProgram.name + ".ir")));
			out.println(irProgram);
			for (IRFunction f : irProgram.functionList) {
				out.println(f);
				out.println("{");
				for (int i = 0; i < f.tempAllocator.next; i++) {
					out.println("\t" + f.tempAllocator.temps[i].toLongString());
				}
				out.println();
				for (IRInstruction instr : f.instrList) {
					if (instr instanceof IRLabelInstruction) {
						out.println(instr);
					} else {
						out.println("\t\t" + instr);
					}
				}
				out.println("}");
			}
			out.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	@Override
	public Object visit(Program program) {
		funcEnv.clear();
		for (Function f : program.funcList) {
			f.funcDecl.accept(this);
		}
		for (Function f : program.funcList) {
			f.accept(this);
		}
		return irProgram;
	}

	@Override
	public Object visit(Function function) {
		tempAllocator = new TempAllocator();
		labelAllocator = new LabelAllocator();
		instrList = new ArrayList<IRInstruction>();
		varEnv.clear();
		for (FormalParameter fp : function.funcDecl.formalParameterList) {
			fp.accept(this);
		}
		String currFunction = function.funcDecl.id.toString();
		currentFtv = funcEnv.get(currFunction);
		function.funcBody.accept(this);
		IRFunction irFunction = new IRFunction(currFunction, currentFtv, instrList, tempAllocator);
		irProgram.addFunction(irFunction);
		return null;
	}

	@Override
	public Object visit(FunctionBody functionBody) {
		for (VariableDeclaration v : functionBody.vlist) {
			v.accept(this);
		}
		for (Statement s : functionBody.slist) {
			s.accept(this);
		}
		if (!(instrList.get(instrList.size() - 1) instanceof IRReturnInstruction)) {
			if (currentFtv.rType.equals(VOID_TYPE)) {
				instrList.add(new IRReturnInstruction(null)); // add void return statement if it is not there
			} else {
				TempVar returnTemp = tempAllocator.allocate(currentFtv.rType);
				instrList.add(new IRReturnInstruction(returnTemp)); // add non-void return with garbage value
			}
		}
		return null;
	}

	@Override
	public Object visit(FunctionDeclaration functionDeclaration) {
		List<Type> paramTypes = new ArrayList<Type>();
		for (FormalParameter fp : functionDeclaration.formalParameterList) {
			paramTypes.add(fp.type);
		}
		funcEnv.put(functionDeclaration.id.toString(), new FuncTypeValue(functionDeclaration.type, paramTypes));
		return null;
	}

	@Override
	public Object visit(FormalParameter formalParameter) {
		TempVar t = tempAllocator.allocate(formalParameter.type, formalParameter.id.toString(), TempSet.PARAMETER);
		varEnv.put(formalParameter.id.toString(), t); // TODO: do we need to init array for a param?
		return null;
	}

	@Override
	public Object visit(Identifier identifier) {
		return varEnv.get(identifier.toString());
	}

	@Override
	public Object visit(VariableDeclaration variableDeclaration) {
		TempVar t = tempAllocator.allocate(variableDeclaration.type, variableDeclaration.id.toString(),
				TempSet.LOCAL_VARIABLE);
		varEnv.put(variableDeclaration.id.toString(), t);

		if (t.type instanceof ArrayType) {
			ArrayType at = (ArrayType) t.type;
			instrList.add(new IRArrayInit(t, at.element_type, at.array_size));
		}

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
		return expressionStatement.expr.accept(this);
	}

	@Override
	public Object visit(IfElseStatement ifElseStatement) {
		Label else_start = labelAllocator.allocate();
		Label else_end = labelAllocator.allocate();
		TempVar condtion = (TempVar) ifElseStatement.ifExpr.accept(this);
		instrList.add(new IRUnaryOp(condtion, "Z!", condtion));
		instrList.add(new IRConditionalGotoInstruction(condtion, else_start));
		for (Statement s : ifElseStatement.ifBlock) {
			s.accept(this);
		}
		instrList.add(new IRGotoInstruction(else_end));
		instrList.add(new IRLabelInstruction(else_start));
		for (Statement s : ifElseStatement.elseBlock) {
			s.accept(this);
		}
		instrList.add(new IRLabelInstruction(else_end));
		return null;
	}

	@Override
	public Object visit(EqualityExpression equalityExpression) {
		TempVar lhs = (TempVar) equalityExpression.lhsExpr.accept(this);
		TempVar rhs = (TempVar) equalityExpression.rhsExpr.accept(this);
		TempVar result = tempAllocator.allocate(BOOLEAN_TYPE);
		String op = lhs.type.toShortString() + "==";
		instrList.add(new IRBinaryOp(result, lhs, op, rhs));
		return result;
	}

	@Override
	public Object visit(LessThanExpression lessThanExpression) {
		TempVar lhs = (TempVar) lessThanExpression.lhsExpr.accept(this);
		TempVar rhs = (TempVar) lessThanExpression.rhsExpr.accept(this);
		TempVar result = tempAllocator.allocate(BOOLEAN_TYPE);
		String op = lhs.type.toShortString() + "<";
		instrList.add(new IRBinaryOp(result, lhs, op, rhs));
		return result;
	}

	@Override
	public Object visit(SubtractExpression subtractExpression) {
		TempVar lhs = (TempVar) subtractExpression.lhsExpr.accept(this);
		TempVar rhs = (TempVar) subtractExpression.rhsExpr.accept(this);
		TempVar result = tempAllocator.allocate(lhs.type);
		String op = lhs.type.toShortString() + "-";
		instrList.add(new IRBinaryOp(result, lhs, op, rhs));
		return result;
	}

	@Override
	public Object visit(MultExpression multExpression) {
		TempVar lhs = (TempVar) multExpression.lhsExpr.accept(this);
		TempVar rhs = (TempVar) multExpression.rhsExpr.accept(this);
		TempVar result = tempAllocator.allocate(lhs.type);
		String op = lhs.type.toShortString() + "*";
		instrList.add(new IRBinaryOp(result, lhs, op, rhs));
		return result;
	}

	@Override
	public Object visit(ParenExpression parenExpression) {
		return parenExpression.expr.accept(this);
	}

	@Override
	public Object visit(AddExpression addExpression) {
		TempVar lhs = (TempVar) addExpression.lhsExpr.accept(this);
		TempVar rhs = (TempVar) addExpression.rhsExpr.accept(this);
		TempVar result = tempAllocator.allocate(lhs.type);
		String op = lhs.type.toShortString() + "+";
		instrList.add(new IRBinaryOp(result, lhs, op, rhs));
		return result;
	}

	@Override
	public Object visit(IntegerLiteral integerLiteral) {
		TempVar t = tempAllocator.allocate(INTEGER_TYPE);
		instrList.add(new IRConstantAssign(t, Integer.toString(integerLiteral.value)));
		return t;
	}

	@Override
	public Object visit(FunctionCall functionCall) {
		String funcName = functionCall.id.toString();
		FuncTypeValue ftv = funcEnv.get(funcName);
		TempVar result = null;
		if (!ftv.rType.equals(VOID_TYPE))
			result = tempAllocator.allocate(ftv.rType);
		List<TempVar> params = new ArrayList<TempVar>();
		for (Expression e : functionCall.exprList) {
			params.add((TempVar) e.accept(this));
		}
		instrList.add(new IRCallInstruction(funcName, result, params));
		return result;
	}

	@Override
	public Object visit(ArrayReference arrayReference) {
		TempVar index = (TempVar) arrayReference.expr.accept(this);
		TempVar arr = varEnv.get(arrayReference.id.toString());
		Type elementType = ((ArrayType) arr.type).element_type;
		TempVar result = tempAllocator.allocate(elementType);
		instrList.add(new IRTempAssignArrayRef(result, arr, index));
		return result;
	}

	@Override
	public Object visit(StringLiteral stringLiteral) {
		TempVar t = tempAllocator.allocate(STRING_TYPE);
		instrList.add(new IRConstantAssign(t, "\"" + stringLiteral.value + "\""));
		return t;
	}

	@Override
	public Object visit(CharLiteral charLiteral) {
		TempVar t = tempAllocator.allocate(CHAR_TYPE);
		instrList.add(new IRConstantAssign(t, "'" + String.valueOf(charLiteral.value) + "'"));
		return t;
	}

	@Override
	public Object visit(FloatLiteral floatLiteral) {
		TempVar t = tempAllocator.allocate(FLOAT_TYPE);
		instrList.add(new IRConstantAssign(t, Float.toString(floatLiteral.value)));
		return t;
	}

	@Override
	public Object visit(BooleanLiteral booleanLiteral) {
		TempVar t = tempAllocator.allocate(BOOLEAN_TYPE);
		String literal = booleanLiteral.value ? "TRUE" : "FALSE";
		instrList.add(new IRConstantAssign(t, literal));
		return t;
	}

	@Override
	public Object visit(AssignmentStatement assignmentStatement) {
		TempVar rhs = (TempVar) assignmentStatement.expr.accept(this); // TODO: may create 1 extra tmp: `x =arr[2];`
		TempVar lhs = varEnv.get(assignmentStatement.id.toString());
		instrList.add(new IRTempAssign(lhs, rhs));
		return null;
	}

	@Override
	public Object visit(IfStatement ifStatement) {
		Label end = labelAllocator.allocate();
		TempVar condtion = (TempVar) ifStatement.expr.accept(this);
		instrList.add(new IRUnaryOp(condtion, "Z!", condtion));
		instrList.add(new IRConditionalGotoInstruction(condtion, end));
		for (Statement s : ifStatement.block) {
			s.accept(this);
		}
		instrList.add(new IRLabelInstruction(end));
		return null;
	}

	@Override
	public Object visit(PrintStatement printStatement) {
		TempVar t = (TempVar) printStatement.expr.accept(this);
		PrintIRInstruction instr = new PrintIRInstruction(t);
		instrList.add(instr);
		return null;
	}

	@Override
	public Object visit(WhileStatement whileStatement) {
		Label start = labelAllocator.allocate();
		Label end = labelAllocator.allocate();
		instrList.add(new IRLabelInstruction(start));
		TempVar condition = (TempVar) whileStatement.expr.accept(this);
		instrList.add(new IRUnaryOp(condition, "Z!", condition));
		instrList.add(new IRConditionalGotoInstruction(condition, end));
		for (Statement s : whileStatement.block) {
			s.accept(this);
		}
		instrList.add(new IRGotoInstruction(start));
		instrList.add(new IRLabelInstruction(end));
		return null;
	}

	@Override
	public Object visit(PrintlnStatement printlnStatement) {
		TempVar t = (TempVar) printlnStatement.expr.accept(this);
		PrintlnIRInstruction instr = new PrintlnIRInstruction(t);
		instrList.add(instr);
		return null;
	}

	@Override
	public Object visit(ReturnStatement returnStatement) {
		TempVar result = null;
		if (returnStatement.expr != null)
			result = (TempVar) returnStatement.expr.accept(this);
		instrList.add(new IRReturnInstruction(result));
		return null;
	}

	@Override
	public Object visit(ArrayAssignmentStatement arrayAssignmentStatement) {
		TempVar rhs = (TempVar) arrayAssignmentStatement.assign_expr.accept(this);
		TempVar lhsindex = (TempVar) arrayAssignmentStatement.index_expr.accept(this);
		TempVar lhs = varEnv.get(arrayAssignmentStatement.id.toString());
		instrList.add(new IRArrayAssign(lhs, lhsindex, rhs));
		return null;
	}

}
