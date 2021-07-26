package ir.irinstruction;

import ir.TempVar;
import ir.visitors.Visitor;

public class PrintlnIRInstruction extends IRInstruction {

	public TempVar t;

	public PrintlnIRInstruction(TempVar t) {
		this.t = t;
	}

	@Override
	public String toString() {
		return "PRINTLN" + t.type.toShortString() + " " + t + ";";
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}

}
