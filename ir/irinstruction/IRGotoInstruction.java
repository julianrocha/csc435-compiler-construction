package ir.irinstruction;

import ir.Label;

public class IRGotoInstruction extends IRInstruction {

	Label l;

	public IRGotoInstruction(Label l) {
		this.l = l;
	}

	@Override
	public String toString() {
		return "GOTO " + l + ";";
	}

}
