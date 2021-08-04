package ir.irinstruction.assignment;

import ir.TempVar;
import ir.irinstruction.IRInstruction;
import ir.visitors.Visitor;

public class IRBinaryOp extends IRInstruction {

	public TempVar result;
	public TempVar lhs;
	public String op;
	public TempVar rhs;

	public IRBinaryOp(TempVar result, TempVar lhs, String op, TempVar rhs) {
		this.result = result;
		this.lhs = lhs;
		this.op = op;
		this.rhs = rhs;
	}

	@Override
	public String toString() {
		return result + " := " + lhs + " " + op + " " + rhs + ";";
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}

}
