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
	private LabelAllocator labelAllocator;

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

	private String getJasminLabel() {
		return "L_" + labelAllocator.allocate().number;
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
				labelAllocator = new LabelAllocator();
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
			indent = false;
			println(";\t\t\t\t" + irInstruction.toString());
			indent = true;
			irInstruction.accept(this);
		}
		indent = false;
		println(".end method");
		return null;
	}

	@Override
	public Object visit(Label label) {
		indent = false;
		println(label.toString() + ":");
		indent = true;
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
		String callString = "invokestatic " + className + "/" + irCallInstruction.functionName + "(";
		for (TempVar t : irCallInstruction.params) {
			println(t.type.toJasminPrefix() + "load " + t.number);
			callString += t.type.toJasminString();
		}
		if (irCallInstruction.result != null) { // non-void return function
			println(callString + ")" + irCallInstruction.result.type.toJasminString());
			println(irCallInstruction.result.type.toJasminPrefix() + "store " + irCallInstruction.result.number);
		} else { // void return function
			println(callString + ")V");
		}
		return null;
	}

	@Override
	public Object visit(IRConditionalGotoInstruction irConditionalGotoInstruction) {
		println("iload " + irConditionalGotoInstruction.operand.number);
		println("ifne " + irConditionalGotoInstruction.l.toString());
		return null;
	}

	@Override
	public Object visit(IRGotoInstruction irGotoInstruction) {
		println("goto " + irGotoInstruction.l.toString());
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
		Type type = irBinaryOp.lhs.type;
		String operation = irBinaryOp.op.substring(1);
		switch (operation) {
			case ("+"):
				if (type.equals(INTEGER_TYPE)) {
					intAddition(loadLhsNum, loadRhsNum, destStoreNum);
				} else if (type.equals(FLOAT_TYPE)) {
					floatAddition(loadLhsNum, loadRhsNum, destStoreNum);
				} else if (type.equals(CHAR_TYPE)) {
					charAddition(loadLhsNum, loadRhsNum, destStoreNum);
				} else if (type.equals(STRING_TYPE)) {
					stringAddition(loadLhsNum, loadRhsNum, destStoreNum);
				}
				break;
			case ("-"):
				if (type.equals(INTEGER_TYPE)) {
					intSubtraction(loadLhsNum, loadRhsNum, destStoreNum);
				} else if (type.equals(FLOAT_TYPE)) {
					floatSubtraction(loadLhsNum, loadRhsNum, destStoreNum);
				} else if (type.equals(CHAR_TYPE)) {
					charSubtraction(loadLhsNum, loadRhsNum, destStoreNum);
				}
				break;
			case ("*"):
				if (type.equals(INTEGER_TYPE)) {
					intMultiplication(loadLhsNum, loadRhsNum, destStoreNum);
				} else if (type.equals(FLOAT_TYPE)) {
					floatMultiplication(loadLhsNum, loadRhsNum, destStoreNum);
				}
				break;
			case ("<"):
				if (type.equals(INTEGER_TYPE)) {
					intLessThan(loadLhsNum, loadRhsNum, destStoreNum);
				} else if (type.equals(FLOAT_TYPE)) {
					floatLessThan(loadLhsNum, loadRhsNum, destStoreNum);
				} else if (type.equals(CHAR_TYPE)) {
					charLessThan(loadLhsNum, loadRhsNum, destStoreNum);
				} else if (type.equals(STRING_TYPE)) {
					stringLessThan(loadLhsNum, loadRhsNum, destStoreNum);
				}
				break;
			case ("=="):
				if (type.equals(INTEGER_TYPE)) {
					intEquality(loadLhsNum, loadRhsNum, destStoreNum);
				} else if (type.equals(FLOAT_TYPE)) {
					floatEquality(loadLhsNum, loadRhsNum, destStoreNum);
				} else if (type.equals(CHAR_TYPE)) {
					charEquality(loadLhsNum, loadRhsNum, destStoreNum);
				} else if (type.equals(STRING_TYPE)) {
					stringEquality(loadLhsNum, loadRhsNum, destStoreNum);
				} else if (type.equals(BOOLEAN_TYPE)) {
					booleanEquality(loadLhsNum, loadRhsNum, destStoreNum);
				}
				break;
		}
		return null;
	}

	private void binaryLoad(int loadLhsNum, int loadRhsNum, String type) {
		println(type + "load " + loadLhsNum);
		println(type + "load " + loadRhsNum);
	}

	private void store(int destStoreNum, String type) {
		println(type + "store " + destStoreNum);
	}

	private void addition(String type) {
		println(type + "add");
	}

	private void subtraction(String type) {
		println(type + "sub");
	}

	private void multiplication(String type) {
		println(type + "mul");
	}

	private void intAddition(int loadLhsNum, int loadRhsNum, int destStoreNum) {
		String type = INTEGER_TYPE.toJasminPrefix();
		binaryLoad(loadLhsNum, loadRhsNum, type);
		addition(type);
		store(destStoreNum, type);
	}

	private void floatAddition(int loadLhsNum, int loadRhsNum, int destStoreNum) {
		String type = FLOAT_TYPE.toJasminPrefix();
		binaryLoad(loadLhsNum, loadRhsNum, type);
		addition(type);
		store(destStoreNum, type);
	}

	private void charAddition(int loadLhsNum, int loadRhsNum, int destStoreNum) {
		String type = CHAR_TYPE.toJasminPrefix();
		binaryLoad(loadLhsNum, loadRhsNum, type);
		addition(type);
		println("i2c");
		store(destStoreNum, type);
	}

	private void stringAddition(int loadLhsNum, int loadRhsNum, int destStoreNum) {
		String stringType = STRING_TYPE.toJasminString();
		String stringPrefix = STRING_TYPE.toJasminPrefix();
		println("new java/lang/StringBuffer");
		println("dup");
		println("invokenonvirtual java/lang/StringBuffer/<init>()V");
		println(stringPrefix + "load " + loadLhsNum);
		println("invokevirtual java/lang/StringBuffer/append(" + stringType + ")Ljava/lang/StringBuffer;");
		println(stringPrefix + "load " + loadRhsNum);
		println("invokevirtual java/lang/StringBuffer/append(" + stringType + ")Ljava/lang/StringBuffer;");
		println("invokevirtual java/lang/StringBuffer/toString()" + stringType);
		println(stringPrefix + "store " + destStoreNum);
	}

	private void intSubtraction(int loadLhsNum, int loadRhsNum, int destStoreNum) {
		String type = INTEGER_TYPE.toJasminPrefix();
		binaryLoad(loadLhsNum, loadRhsNum, type);
		subtraction(type);
		store(destStoreNum, type);
	}

	private void floatSubtraction(int loadLhsNum, int loadRhsNum, int destStoreNum) {
		String type = FLOAT_TYPE.toJasminPrefix();
		binaryLoad(loadLhsNum, loadRhsNum, type);
		subtraction(type);
		store(destStoreNum, type);
	}

	private void charSubtraction(int loadLhsNum, int loadRhsNum, int destStoreNum) {
		String type = CHAR_TYPE.toJasminPrefix();
		binaryLoad(loadLhsNum, loadRhsNum, type);
		subtraction(type);
		println("i2c");
		store(destStoreNum, type);
	}

	private void intMultiplication(int loadLhsNum, int loadRhsNum, int destStoreNum) {
		String type = INTEGER_TYPE.toJasminPrefix();
		binaryLoad(loadLhsNum, loadRhsNum, type);
		multiplication(type);
		store(destStoreNum, type);
	}

	private void floatMultiplication(int loadLhsNum, int loadRhsNum, int destStoreNum) {
		String type = FLOAT_TYPE.toJasminPrefix();
		binaryLoad(loadLhsNum, loadRhsNum, type);
		multiplication(type);
		store(destStoreNum, type);
	}

	private void intLessThan(int loadLhsNum, int loadRhsNum, int destStoreNum) {
		String label1 = getJasminLabel();
		String label2 = getJasminLabel();
		String type = INTEGER_TYPE.toJasminPrefix();
		binaryLoad(loadLhsNum, loadRhsNum, type);
		subtraction(type);
		lessThan(destStoreNum, label1, label2);
	}

	private void floatLessThan(int loadLhsNum, int loadRhsNum, int destStoreNum) {
		String label1 = getJasminLabel();
		String label2 = getJasminLabel();
		String type = FLOAT_TYPE.toJasminPrefix();
		binaryLoad(loadLhsNum, loadRhsNum, type);
		println("fcmpg");
		lessThan(destStoreNum, label1, label2);
	}

	private void charLessThan(int loadLhsNum, int loadRhsNum, int destStoreNum) {
		String label1 = getJasminLabel();
		String label2 = getJasminLabel();
		String type = CHAR_TYPE.toJasminPrefix();
		binaryLoad(loadLhsNum, loadRhsNum, type);
		subtraction(type);
		lessThan(destStoreNum, label1, label2);
	}

	private void stringLessThan(int loadLhsNum, int loadRhsNum, int destStoreNum) {
		String label1 = getJasminLabel();
		String label2 = getJasminLabel();
		String jasminPrefix = STRING_TYPE.toJasminPrefix();
		String jasminType = STRING_TYPE.toJasminString();
		binaryLoad(loadLhsNum, loadRhsNum, jasminPrefix);
		println("invokevirtual java/lang/String/compareTo(" + jasminType + ")I");
		lessThan(destStoreNum, label1, label2);
	}

	private void intEquality(int loadLhsNum, int loadRhsNum, int destStoreNum) {
		String label1 = getJasminLabel();
		String label2 = getJasminLabel();
		String type = INTEGER_TYPE.toJasminPrefix();
		binaryLoad(loadLhsNum, loadRhsNum, type);
		subtraction(type);
		equality(destStoreNum, label1, label2);
	}

	private void floatEquality(int loadLhsNum, int loadRhsNum, int destStoreNum) {
		String label1 = getJasminLabel();
		String label2 = getJasminLabel();
		String type = FLOAT_TYPE.toJasminPrefix();
		binaryLoad(loadLhsNum, loadRhsNum, type);
		println("fcmpg");
		equality(destStoreNum, label1, label2);
	}

	private void charEquality(int loadLhsNum, int loadRhsNum, int destStoreNum) {
		String label1 = getJasminLabel();
		String label2 = getJasminLabel();
		String type = CHAR_TYPE.toJasminPrefix();
		binaryLoad(loadLhsNum, loadRhsNum, type);
		subtraction(type);
		equality(destStoreNum, label1, label2);
	}

	private void stringEquality(int loadLhsNum, int loadRhsNum, int destStoreNum) {
		String label1 = getJasminLabel();
		String label2 = getJasminLabel();
		String jasminPrefix = STRING_TYPE.toJasminPrefix();
		String jasminType = STRING_TYPE.toJasminString();
		binaryLoad(loadLhsNum, loadRhsNum, jasminPrefix);
		println("invokevirtual java/lang/String/compareTo(" + jasminType + ")I");
		equality(destStoreNum, label1, label2);
	}

	private void booleanEquality(int loadLhsNum, int loadRhsNum, int destStoreNum) {
		String type = BOOLEAN_TYPE.toJasminPrefix();
		binaryLoad(loadLhsNum, loadRhsNum, type);
		println("ixor");
		println("ldc 1");
		println("ixor");
		store(destStoreNum, type);
	}

	private void equality(int destStoreNum, String label1, String label2) {
		compare(destStoreNum, label1, label2, "ifeq");
	}

	private void lessThan(int destStoreNum, String label1, String label2) {
		compare(destStoreNum, label1, label2, "iflt");
	}

	private void compare(int destStoreNum, String label1, String label2, String cmpInstr) {
		String type = BOOLEAN_TYPE.toJasminPrefix();
		println(cmpInstr + " " + label1);
		println("ldc 0");
		println("goto " + label2);
		indent = false;
		println(label1 + ":");
		indent = true;
		println("ldc 1");
		indent = false;
		println(label2 + ":");
		indent = true;
		store(destStoreNum, type);
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
		int destStoreNum = irTempAssignArrayRef.lhs.number;
		String storePrefix = irTempAssignArrayRef.lhs.type.toJasminPrefix();
		String loadPrefix;
		if (irTempAssignArrayRef.lhs.type.equals(CHAR_TYPE)) {
			loadPrefix = "c";
		} else if (irTempAssignArrayRef.lhs.type.equals(BOOLEAN_TYPE)) {
			loadPrefix = "b";
		} else {
			loadPrefix = storePrefix;
		}
		println("aload " + arrayLoadNum);
		println("iload " + indexLoadNum);
		println(loadPrefix + "aload");
		println(storePrefix + "store " + destStoreNum);
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
