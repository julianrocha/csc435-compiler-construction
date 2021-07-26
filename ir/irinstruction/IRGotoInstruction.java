package ir.irinstruction;

import ir.Label;
import ir.visitors.Visitor;

public class IRGotoInstruction extends IRInstruction {

	public Label l;

	public IRGotoInstruction(Label l) {
		this.l = l;
	}

	@Override
	public String toString() {
		return "GOTO " + l + ";";
	}

	public Object accept(Visitor v) {
		return v.visit(this);
	}

}
