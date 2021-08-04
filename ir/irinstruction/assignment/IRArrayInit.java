package ir.irinstruction.assignment;

import ast.type.Type;
import ir.TempVar;
import ir.irinstruction.IRInstruction;
import ir.visitors.Visitor;

public class IRArrayInit extends IRInstruction {

	public TempVar lhs;
	public Type t;
	public int size;

	public IRArrayInit(TempVar lhs, Type t, int size) {
		this.lhs = lhs;
		this.t = t;
		this.size = size;
	}

	@Override
	public String toString() {
		return lhs + " := NEWARRAY " + t.toShortString() + " " + size + ";";
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}
}
