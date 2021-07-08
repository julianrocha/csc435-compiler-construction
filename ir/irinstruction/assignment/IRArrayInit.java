package ir.irinstruction.assignment;

import ast.type.Type;
import ir.TempVar;
import ir.irinstruction.IRInstruction;

public class IRArrayInit extends IRInstruction {

	TempVar lhs;
	Type t;
	int size;

	public IRArrayInit(TempVar lhs, Type t, int size) {
		this.lhs = lhs;
		this.t = t;
		this.size = size;
	}

	@Override
	public String toString() {
		return lhs + " := NEWARRAY " + t.toShortString() + " " + size + ";";
	}
}
