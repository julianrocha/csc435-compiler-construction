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
import ir.irinstruction.IRInstruction;

public class IRVisitor implements Visitor {
	private TempAllocator allocator;
	private List<IRInstruction> instrList;
	private IRProgram irProgram;
	private HashMap<String, TempVar> varEnv;
	private HashMap<String, FuncTypeValue> funcEnv;

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
				for (int i = 0; i < f.allocator.next; i++) {
					out.println("\t" + f.allocator.temps[i].toLongString());
				}
				out.println();
				for (IRInstruction instr : f.instrList) {
					out.println("\t\t" + instr);
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
		return null;
	}

	@Override
	public Object visit(Function function) {
		allocator = new TempAllocator();
		instrList = new ArrayList<IRInstruction>();
		varEnv.clear();
		for (FormalParameter fp : function.funcDecl.formalParameterList) {
			fp.accept(this);
		}
		function.funcBody.accept(this);
		String currFunction = function.funcDecl.id.toString();
		FuncTypeValue ftv = funcEnv.get(currFunction);
		IRFunction irFunction = new IRFunction(currFunction, ftv, instrList, allocator);
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
		TempVar t = allocator.allocate(formalParameter.type, formalParameter.id.toString(), TempSet.PARAMETER);
		varEnv.put(formalParameter.id.toString(), t);
		return null;
	}

	@Override
	public Object visit(Identifier identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(VariableDeclaration variableDeclaration) {
		// TODO: if instance of ArrayType, then we need to add an array init instr
		TempVar t = allocator.allocate(variableDeclaration.type, variableDeclaration.id.toString(),
				TempSet.LOCAL_VARIABLE);
		varEnv.put(variableDeclaration.id.toString(), t);
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
