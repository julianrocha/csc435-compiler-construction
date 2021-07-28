package ir.visitors;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import ast.type.StringType;
import ast.type.*;
import ir.*;
import ir.irinstruction.*;
import ir.irinstruction.assignment.*;

public class JasminGenVisitor implements Visitor {

	private PrintWriter out;
	private boolean indent;
	private String className;

	// Singletons
	private static BooleanType BOOLEAN_TYPE = new BooleanType();
	private static CharType CHAR_TYPE = new CharType();
	private static FloatType FLOAT_TYPE = new FloatType();
	private static IntegerType INTEGER_TYPE = new IntegerType();
	private static StringType STRING_TYPE = new StringType();
	private static VoidType VOID_TYPE = new VoidType();

	public JasminGenVisitor() {
		indent = false;
	}

	private void println(String s) {
		if (indent) {
			out.print("\t");
		}
		out.println(s);
	}

	@Override
	public Object visit(IRProgram irProgram) {
		try {
			className = irProgram.name;
			out = new PrintWriter(new BufferedWriter(new FileWriter(className + ".j")));
			// file directives
			println(".source " + className + ".j");
			println(".class public " + className);
			println(".super java/lang/Object");
			println("");

			// standard initializer
			println(".method public <init>()V");
			indent = true;
			println("aload_0");
			println("invokenonvirtual java/lang/Object/<init>()V");
			println("return");
			indent = false;
			println(".end method");
			println("");

			// jasmin main
			println(".method public static main([Ljava/lang/String;)V");
			indent = true;
			println("invokestatic " + className + "/__main()V");
			println("return");
			indent = false;
			println(".end method");

			// translate IR functions to jasmin
			for (IRFunction f : irProgram.functionList) {
				println("");
				f.accept(this);
			}

			out.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return null;
	}

	@Override
	public Object visit(IRFunction irFunction) {
		// Function signature
		String funcName = irFunction.name.equals("main") ? "__main" : irFunction.name;
		String paramTypes = "";
		for (Type t : irFunction.paramTypes) {
			paramTypes += t.toJasminString();
		}
		String returnType = irFunction.returnType.toJasminString();
		println(".method public static " + funcName + "(" + paramTypes + ")" + returnType);
		indent = true;

		// Declare local variables
		println(".limit locals " + irFunction.tempAllocator.next);
		for (int i = 0; i < irFunction.tempAllocator.next; i++) {
			irFunction.tempAllocator.temps[i].accept(this);
		}

		// Translate IR instructions to jasmin
		println(".limit stack 16"); // TODO: how is this number calculated?
		for (IRInstruction irInstruction : irFunction.instrList) {
			println(";\t\t" + irInstruction.toString());
			irInstruction.accept(this);
		}
		indent = false;
		println(".end method");
		return null;
	}

	@Override
	public Object visit(Label label) {
		println(label.toString() + ":");
		return null;
	}

	@Override
	public Object visit(TempVar tempVar) {
		String variableDirective = ".var ";
		variableDirective += tempVar.number + " is ";
		variableDirective += tempVar.label == null ? tempVar.toString() : tempVar.label;
		variableDirective += " " + tempVar.type.toJasminString();
		// TODO: missing 'from label to label' directive
		println(variableDirective);
		return null;
	}

	@Override
	public Object visit(IRCallInstruction irCallInstruction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(IRConditionalGotoInstruction irConditionalGotoInstruction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(IRGotoInstruction irGotoInstruction) {
		// println("goto " + irGotoInstruction.l.toString());
		return null;
	}

	@Override
	public Object visit(IRLabelInstruction irLabelInstruction) {
		irLabelInstruction.l.accept(this);
		return null;
	}

	@Override
	public Object visit(IRReturnInstruction irReturnInstruction) {
		if (irReturnInstruction.operand != null) { // non-void return
			String prefix = irReturnInstruction.operand.type.toJasminPrefix();
			int number = irReturnInstruction.operand.number;
			println(prefix + "load " + number);
			println(prefix + "return");
		} else { // void return
			println("return");
		}
		return null;
	}

	@Override
	public Object visit(PrintIRInstruction printIRInstruction) {
		String prefix = printIRInstruction.t.type.toJasminPrefix();
		int loadNum = printIRInstruction.t.number;
		String printType = printIRInstruction.t.type.toJasminString();
		println("getstatic java/lang/System/out Ljava/io/PrintStream;");
		println(prefix + "load " + loadNum);
		println("invokevirtual java/io/PrintStream/print(" + printType + ")V");
		return null;
	}

	@Override
	public Object visit(PrintlnIRInstruction printlnIRInstruction) {
		String prefix = printlnIRInstruction.t.type.toJasminPrefix();
		int loadNum = printlnIRInstruction.t.number;
		String printType = printlnIRInstruction.t.type.toJasminString();
		println("getstatic java/lang/System/out Ljava/io/PrintStream;");
		println(prefix + "load " + loadNum);
		println("invokevirtual java/io/PrintStream/println(" + printType + ")V");
		return null;
	}

	@Override
	public Object visit(IRArrayAssign irArrayAssign) {
		int arrayLoadNum = irArrayAssign.lhs.number;
		int indexLoadNum = irArrayAssign.lhsindex.number;
		int sourceLoadNum = irArrayAssign.rhs.number;
		String prefix = irArrayAssign.rhs.type.toJasminPrefix();
		println("aload " + arrayLoadNum);
		println("iload " + indexLoadNum);
		println(prefix + "load " + sourceLoadNum);
		String arrayStorePrefix;
		if (irArrayAssign.rhs.type.equals(CHAR_TYPE)) {
			arrayStorePrefix = "c";
		} else if (irArrayAssign.rhs.type.equals(BOOLEAN_TYPE)) {
			arrayStorePrefix = "b";
		} else {
			arrayStorePrefix = irArrayAssign.rhs.type.toJasminPrefix();
		}
		println(arrayStorePrefix + "astore");
		return null;
	}

	@Override
	public Object visit(IRArrayInit irArrayInit) {
		int arrayLen = irArrayInit.size;
		int destStoreNum = irArrayInit.lhs.number;
		println("ldc " + arrayLen);
		if (irArrayInit.t.equals(STRING_TYPE)) {
			println("anewarray java/lang/String");
		} else {
			println("newarray " + irArrayInit.t.toString());
		}
		println("astore " + destStoreNum);
		return null;
	}

	@Override
	public Object visit(IRBinaryOp irBinaryOp) {
		int loadLhsNum = irBinaryOp.lhs.number;
		int loadRhsNum = irBinaryOp.rhs.number;
		int destStoreNum = irBinaryOp.result.number;
		Type type = irBinaryOp.result.type;
		String instructions = "";
		switch (irBinaryOp.op) {
			case ("+"):
				if (type.equals(INTEGER_TYPE)) {
					instructions = intAddition(loadLhsNum, loadRhsNum, destStoreNum);
				} else if (type.equals(FLOAT_TYPE)) {
					instructions = floatAddition(loadLhsNum, loadRhsNum, destStoreNum);
				} else if (type.equals(CHAR_TYPE)) {
					instructions = charAddition(loadLhsNum, loadRhsNum, destStoreNum);
				} else if (type.equals(STRING_TYPE)) {
					instructions = stringAddition(loadLhsNum, loadRhsNum, destStoreNum);
				}
				break;
			case ("-"):
				if (type.equals(INTEGER_TYPE)) {
					instructions = intSubtraction(loadLhsNum, loadRhsNum, destStoreNum);
				} else if (type.equals(FLOAT_TYPE)) {
					instructions = floatSubtraction(loadLhsNum, loadRhsNum, destStoreNum);
				} else if (type.equals(CHAR_TYPE)) {
					instructions = charSubtraction(loadLhsNum, loadRhsNum, destStoreNum);
				}
				break;
			case ("*"):
				if (type.equals(INTEGER_TYPE)) {
					instructions = intMultiplication(loadLhsNum, loadRhsNum, destStoreNum);
				} else if (type.equals(FLOAT_TYPE)) {
					instructions = floatMultiplication(loadLhsNum, loadRhsNum, destStoreNum);
				}
				break;
			case ("<"):
				if (type.equals(INTEGER_TYPE)) {
					instructions = intLessThan(loadLhsNum, loadRhsNum, destStoreNum);
				} else if (type.equals(FLOAT_TYPE)) {
					instructions = floatLessThan(loadLhsNum, loadRhsNum, destStoreNum);
				} else if (type.equals(CHAR_TYPE)) {
					instructions = charLessThan(loadLhsNum, loadRhsNum, destStoreNum);
				} else if (type.equals(STRING_TYPE)) {
					instructions = stringLessThan(loadLhsNum, loadRhsNum, destStoreNum);
				}
				break;
			case ("=="):
				if (type.equals(INTEGER_TYPE)) {
					instructions = intEquality(loadLhsNum, loadRhsNum, destStoreNum);
				} else if (type.equals(FLOAT_TYPE)) {
					instructions = floatEquality(loadLhsNum, loadRhsNum, destStoreNum);
				} else if (type.equals(CHAR_TYPE)) {
					instructions = charEquality(loadLhsNum, loadRhsNum, destStoreNum);
				} else if (type.equals(STRING_TYPE)) {
					instructions = stringEquality(loadLhsNum, loadRhsNum, destStoreNum);
				} else if (type.equals(BOOLEAN_TYPE)) {
					instructions = booleanEquality(loadLhsNum, loadRhsNum, destStoreNum);
				}
				break;
		}
		println(instructions);
		return null;
	}

	private String intAddition(int loadLhsNum, int loadRhsNum, int destStoreNum) {
		return "iload " + loadLhsNum + "\niload " + loadRhsNum + "\niadd\nistore " + destStoreNum;
	}

	private String floatAddition(int loadLhsNum, int loadRhsNum, int destStoreNum) {
		return "fload " + loadLhsNum + "\nfload " + loadRhsNum + "\nfadd\nfstore " + destStoreNum;
	}

	private String charAddition(int loadLhsNum, int loadRhsNum, int destStoreNum) {
		return "iload " + loadLhsNum + "\niload " + loadRhsNum + "\niadd\ni2c\nistore " + destStoreNum;
	}

	private String stringAddition(int loadLhsNum, int loadRhsNum, int destStoreNum) {
		return "new java/lang/StringBuffer\ndup\ninvokenonvirtual java/lang/StringBuffer/<init>()V\naload " + loadLhsNum
				+ "\ninvokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;\naload "
				+ loadRhsNum
				+ "invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;\ninvokevirtual java/lang/StringBuffer/toString()Ljava/lang/String;\nastore "
				+ destStoreNum;
	}

	private String intSubtraction(int loadLhsNum, int loadRhsNum, int destStoreNum) {
		return "iload " + loadLhsNum + "\niload " + loadRhsNum + "\nisub\nistore " + destStoreNum;
	}

	private String floatSubtraction(int loadLhsNum, int loadRhsNum, int destStoreNum) {
		return "fload " + loadLhsNum + "\nfload " + loadRhsNum + "\nfsub\nfstore " + destStoreNum;
	}

	private String charSubtraction(int loadLhsNum, int loadRhsNum, int destStoreNum) {
		return "iload " + loadLhsNum + "\niload " + loadRhsNum + "\nisub\ni2c\nistore " + destStoreNum;
	}

	private String intMultiplication(int loadLhsNum, int loadRhsNum, int destStoreNum) {
		return "iload " + loadLhsNum + "\niload " + loadRhsNum + "\nimul\nistore " + destStoreNum;
	}

	private String floatMultiplication(int loadLhsNum, int loadRhsNum, int destStoreNum) {
		return "fload " + loadLhsNum + "\nfload " + loadRhsNum + "\nfmul\nfstore " + destStoreNum;
	}

	private String intLessThan(int loadLhsNum, int loadRhsNum, int destStoreNum) {
		return "";
	}

	private String floatLessThan(int loadLhsNum, int loadRhsNum, int destStoreNum) {
		return "";
	}

	private String charLessThan(int loadLhsNum, int loadRhsNum, int destStoreNum) {
		return "";
	}

	private String stringLessThan(int loadLhsNum, int loadRhsNum, int destStoreNum) {
		return "";
	}

	private String intEquality(int loadLhsNum, int loadRhsNum, int destStoreNum) {
		return "";
	}

	private String floatEquality(int loadLhsNum, int loadRhsNum, int destStoreNum) {
		return "";
	}

	private String charEquality(int loadLhsNum, int loadRhsNum, int destStoreNum) {
		return "";
	}

	private String stringEquality(int loadLhsNum, int loadRhsNum, int destStoreNum) {
		return "";
	}

	private String booleanEquality(int loadLhsNum, int loadRhsNum, int destStoreNum) {
		return "iload " + loadLhsNum + "\niload " + loadRhsNum + "\nixor\nldc 1\nixor\nistore " + destStoreNum;
	}

	@Override
	public Object visit(IRConstantAssign irConstantAssign) {
		int destStoreNum = irConstantAssign.lhs.number;
		String prefix = irConstantAssign.lhs.type.toJasminPrefix();
		String constant;
		if (irConstantAssign.lhs.type.equals(CHAR_TYPE)) {
			constant = Integer.toString((int) irConstantAssign.rhs.charAt(1));
		} else if (irConstantAssign.lhs.type.equals(BOOLEAN_TYPE)) {
			if (irConstantAssign.rhs.equals("true")) {
				constant = "1";
			} else {
				constant = "0";
			}
		} else {
			constant = irConstantAssign.rhs;
		}
		println("ldc " + constant);
		println(prefix + "store " + destStoreNum);
		return null;
	}

	@Override
	public Object visit(IRTempAssign irTempAssign) {
		String prefix = irTempAssign.lhs.type.toJasminPrefix();
		int loadNum = irTempAssign.rhs.number;
		int storeNum = irTempAssign.lhs.number;
		println(prefix + "load " + loadNum);
		println(prefix + "store " + storeNum);
		return null;
	}

	@Override
	public Object visit(IRTempAssignArrayRef irTempAssignArrayRef) {
		int arrayLoadNum = irTempAssignArrayRef.rhs.number;
		int indexLoadNum = irTempAssignArrayRef.rhsindex.number;
		String prefix = irTempAssignArrayRef.lhs.type.toJasminPrefix();
		int destStoreNum = irTempAssignArrayRef.lhs.number;
		println("aload " + arrayLoadNum);
		println("iload " + indexLoadNum);
		println(prefix + "aload");
		println(prefix + "store " + destStoreNum);
		return null;
	}

	@Override
	public Object visit(IRUnaryOp irUnaryOp) {
		// TODO: this only handles Z!
		int loadNum = irUnaryOp.rhs.number;
		int storeNum = irUnaryOp.lhs.number;
		println("iload " + loadNum);
		println("ldc 1");
		println("ixor");
		println("istore " + storeNum);
		return null;
	}

}
