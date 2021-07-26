package ir.irinstruction.assignment;

import ir.TempVar;
import ir.irinstruction.IRInstruction;
import ir.visitors.Visitor;

public class IRArrayAssign extends IRInstruction {

	public TempVar lhs;
	public TempVar lhsindex;
	public TempVar rhs;

	public IRArrayAssign(TempVar lhs, TempVar lhsindex, TempVar rhs) {
		this.lhs = lhs;
		this.lhsindex = lhsindex;
		this.rhs = rhs;
	}

	@Override
	public String toString() {
		return lhs + "[" + lhsindex + "]" + " := " + rhs + ";";
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}

}
