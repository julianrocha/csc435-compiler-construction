package ir.irinstruction;

import ir.visitors.Visitor;

public abstract class IRInstruction {

	public abstract String toString();

	public abstract Object accept(Visitor v);
}
