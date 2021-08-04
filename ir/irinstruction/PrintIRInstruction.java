package ir.irinstruction;

import ir.TempVar;
import ir.visitors.Visitor;

public class PrintIRInstruction extends IRInstruction {

	public TempVar t;

	public PrintIRInstruction(TempVar t) {
		this.t = t;
	}

	@Override
	public String toString() {
		return "PRINT" + t.type.toShortString() + " " + t + ";";
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}

}
