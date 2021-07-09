package ir;

import java.util.List;

import ast.type.Type;
import ast.visitors.FuncTypeValue;
import ir.irinstruction.IRInstruction;

public class IRFunction {
	public String name;
	public Type returnType;
	public List<Type> paramTypes;
	public List<IRInstruction> instrList;
	public TempAllocator tempAllocator;

	public IRFunction(String name, FuncTypeValue ftv, List<IRInstruction> instrList, TempAllocator tempAllocator) {
		this.name = name;
		this.returnType = ftv.rType;
		this.paramTypes = ftv.paramTypes;
		this.instrList = instrList;
		this.tempAllocator = tempAllocator;
	}

	public String toString() {
		String s = "FUNC " + name + " (";
		for (Type t : paramTypes) {
			s += t.toShortString();
		}
		s += ")" + returnType.toShortString();
		return s;
	}
}
