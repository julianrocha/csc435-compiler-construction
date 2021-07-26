package ir.irinstruction.assignment;

import ir.TempVar;
import ir.irinstruction.IRInstruction;
import ir.visitors.Visitor;

public class IRTempAssign extends IRInstruction {

	public TempVar lhs;
	public TempVar rhs;

	public IRTempAssign(TempVar lhs, TempVar rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	@Override
	public String toString() {
		return lhs + " := " + rhs + ";";
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}

}
