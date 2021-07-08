package ir.irinstruction.assignment;

import ir.TempVar;
import ir.irinstruction.IRInstruction;

public class IRConstantAssign extends IRInstruction {

	TempVar lhs;
	String rhs;

	public IRConstantAssign(TempVar lhs, String rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	@Override
	public String toString() {
		return lhs + " := " + rhs + ";";
	}

}
