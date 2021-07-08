package ir.irinstruction.assignment;

import ir.TempVar;
import ir.irinstruction.IRInstruction;

public class IRBinaryOp extends IRInstruction {

	TempVar result;
	TempVar lhs;
	String op;
	TempVar rhs;

	public IRBinaryOp(TempVar result, TempVar lhs, String op, TempVar rhs) {
		this.result = result;
		this.lhs = lhs;
		this.op = op;
		this.rhs = rhs;
	}

	@Override
	public String toString() {
		return result + " := " + lhs + op + rhs + ";"; // TODO: ensure op has type
	}

}
