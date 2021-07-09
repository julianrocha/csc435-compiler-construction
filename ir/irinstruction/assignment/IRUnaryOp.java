package ir.irinstruction.assignment;

import ir.TempVar;
import ir.irinstruction.IRInstruction;

public class IRUnaryOp extends IRInstruction {

	TempVar lhs;
	String op;
	TempVar rhs;

	public IRUnaryOp(TempVar lhs, String op, TempVar rhs) {
		this.lhs = lhs;
		this.op = op;
		this.rhs = rhs;
	}

	@Override
	public String toString() {
		return lhs + " := " + op + " " + rhs + ";"; // TODO: ensure type is in op
	}
}