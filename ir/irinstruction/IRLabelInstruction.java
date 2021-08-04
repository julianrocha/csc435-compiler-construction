package ir.irinstruction;

import ir.Label;
import ir.visitors.Visitor;

public class IRLabelInstruction extends IRInstruction {

	public Label l;

	public IRLabelInstruction(Label l) {
		this.l = l;
	}

	@Override
	public String toString() {
		return l + ":;";
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}

}
