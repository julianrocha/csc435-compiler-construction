package ir.irinstruction;

import java.util.List;

import ir.TempVar;
import ir.visitors.Visitor;

public class IRCallInstruction extends IRInstruction {

	public String functionName;
	public TempVar result;
	public List<TempVar> params;

	public IRCallInstruction(String functionName, TempVar result, List<TempVar> params) {
		this.functionName = functionName;
		this.result = result;
		this.params = params;
	}

	@Override
	public String toString() {
		String s = "";
		if (result != null)
			s += result + " := ";
		s += "CALL " + functionName + "(";
		for (TempVar t : params) {
			if (params.indexOf(t) != 0)
				s += " ";
			s += t;
		}
		s += ");";
		return s;
	}

	public Object accept(Visitor v) {
		return v.visit(this);
	}

}
