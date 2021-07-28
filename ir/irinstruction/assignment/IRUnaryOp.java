package ir.irinstruction.assignment;

import ir.TempVar;
import ir.irinstruction.IRInstruction;
import ir.visitors.Visitor;

public class IRUnaryOp extends IRInstruction {

	public TempVar lhs;
	public String op;
	public TempVar rhs;

	public IRUnaryOp(TempVar lhs, String op, TempVar rhs) {
		this.lhs = lhs;
		this.op = op;
		this.rhs = rhs;
	}

	@Override
	public String toString() {
		return lhs + " := " + op + " " + rhs + ";";
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}
}