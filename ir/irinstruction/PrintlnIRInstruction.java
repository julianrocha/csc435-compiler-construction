package ir.irinstruction;

import ir.TempVar;

public class PrintlnIRInstruction extends IRInstruction {

	TempVar t;

	public PrintlnIRInstruction(TempVar t) {
		this.t = t;
	}

	@Override
	public String toString() {
		return "PRINTLN" + t.type.toShortString() + " " + t + ";";
	}

}
