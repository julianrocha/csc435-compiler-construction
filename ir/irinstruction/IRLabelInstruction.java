package ir.irinstruction;

import ir.Label;

public class IRLabelInstruction extends IRInstruction {

	Label l;

	public IRLabelInstruction(Label l) {
		this.l = l;
	}

	@Override
	public String toString() {
		return l + ":;";
	}

}
