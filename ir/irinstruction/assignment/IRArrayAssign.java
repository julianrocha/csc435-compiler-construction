package ir.irinstruction.assignment;

import ir.TempVar;
import ir.irinstruction.IRInstruction;

public class IRArrayAssign extends IRInstruction {

	TempVar lhs;
	TempVar lhsindex;
	TempVar rhs;

	public IRArrayAssign(TempVar lhs, TempVar lhsindex, TempVar rhs) {
		this.lhs = lhs;
		this.lhsindex = lhsindex;
		this.rhs = rhs;
	}

	@Override
	public String toString() {
		return lhs + "[" + lhsindex + "]" + " := " + rhs + ";";
	}

}
