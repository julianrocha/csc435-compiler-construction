package ir.irinstruction.assignment;

import ir.TempVar;
import ir.irinstruction.IRInstruction;

public class IRTempAssignArrayRef extends IRInstruction {

	TempVar lhs;
	TempVar rhs;
	TempVar rhsindex;

	public IRTempAssignArrayRef(TempVar lhs, TempVar rhs, TempVar rhsindex) {
		this.lhs = lhs;
		this.rhs = rhs;
		this.rhsindex = rhsindex;
	}

	@Override
	public String toString() {
		return lhs + " := " + rhs + "[" + rhsindex + "];";
	}

}
