package ir.irinstruction.assignment;

import ir.TempVar;
import ir.irinstruction.IRInstruction;
import ir.visitors.Visitor;

public class IRConstantAssign extends IRInstruction {

	public TempVar lhs;
	public String rhs;

	public IRConstantAssign(TempVar lhs, String rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	@Override
	public String toString() {
		return lhs + " := " + rhs + ";";
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}

}
