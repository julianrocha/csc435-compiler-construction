package ir.irinstruction;

import ir.Label;
import ir.TempVar;
import ir.visitors.Visitor;

public class IRConditionalGotoInstruction extends IRInstruction {
	public TempVar operand;
	public Label l;

	public IRConditionalGotoInstruction(TempVar operand, Label l) {
		this.operand = operand;
		this.l = l;
	}

	@Override
	public String toString() {
		return "IF " + operand + " GOTO " + l + ";";
	}

	public Object accept(Visitor v) {
		return v.visit(this);
	}
}
