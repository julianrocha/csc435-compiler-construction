package ir.irinstruction.assignment;

import ir.TempVar;
import ir.irinstruction.IRInstruction;

public class IRTempAssign extends IRInstruction {

	TempVar lhs;
	TempVar rhs;

	public IRTempAssign(TempVar lhs, TempVar rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	@Override
	public String toString() {
		return lhs + " := " + rhs + ";";
	}

}
