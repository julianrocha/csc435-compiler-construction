package ir.irinstruction;

import ir.TempVar;

public class PrintIRInstruction extends IRInstruction {

	TempVar t;

	public PrintIRInstruction(TempVar t) {
		this.t = t;
	}

	@Override
	public String toString() {
		return "PRINT" + t.type.toShortString() + " " + t + ";";
	}

}
