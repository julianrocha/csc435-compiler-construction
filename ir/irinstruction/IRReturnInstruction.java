package ir.irinstruction;

import ir.TempVar;
import ir.visitors.Visitor;

public class IRReturnInstruction extends IRInstruction {

	public TempVar operand;

	public IRReturnInstruction(TempVar operand) {
		this.operand = operand;
	}

	@Override
	public String toString() {
		return operand == null ? "RETURN;" : "RETURN " + operand + ";";
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}

}
