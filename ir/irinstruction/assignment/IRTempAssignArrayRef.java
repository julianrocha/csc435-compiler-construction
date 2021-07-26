package ir.irinstruction.assignment;

import ir.TempVar;
import ir.irinstruction.IRInstruction;
import ir.visitors.Visitor;

public class IRTempAssignArrayRef extends IRInstruction {

	public TempVar lhs;
	public TempVar rhs;
	public TempVar rhsindex;

	public IRTempAssignArrayRef(TempVar lhs, TempVar rhs, TempVar rhsindex) {
		this.lhs = lhs;
		this.rhs = rhs;
		this.rhsindex = rhsindex;
	}

	@Override
	public String toString() {
		return lhs + " := " + rhs + "[" + rhsindex + "];";
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}

}
