package ir.irinstruction;

import ir.TempVar;

public class IRReturnInstruction extends IRInstruction {

	TempVar operand;

	public IRReturnInstruction(TempVar operand) {
		this.operand = operand;
	}

	@Override
	public String toString() {
		return operand == null ? "RETURN;" : "RETURN " + operand + ";";
	}

}
