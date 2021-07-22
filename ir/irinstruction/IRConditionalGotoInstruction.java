package ir.irinstruction;

import ir.Label;
import ir.TempVar;

public class IRConditionalGotoInstruction extends IRInstruction {
	TempVar operand;
	Label l;

	public IRConditionalGotoInstruction(TempVar operand, Label l) {
		this.operand = operand;
		this.l = l;
	}

	@Override
	public String toString() {
		return "IF " + operand + " GOTO " + l + ";";
	}
}
