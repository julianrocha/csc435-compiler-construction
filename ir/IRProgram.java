package ir;

import java.util.ArrayList;
import java.util.List;

import ir.visitors.Visitor;

public class IRProgram {
	public String name;
	public List<IRFunction> functionList;

	public IRProgram(String name) {
		this.name = name;
		this.functionList = new ArrayList<IRFunction>();
	}

	public void addFunction(IRFunction f) {
		functionList.add(f);
	}

	public String toString() {
		return "PROG " + name;
	}

	public Object accept(Visitor v) {
		return v.visit(this);
	}

}