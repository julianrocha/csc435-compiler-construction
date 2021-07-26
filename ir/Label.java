package ir;

import ir.visitors.Visitor;

public class Label {
	int number;

	public Label(int number) {
		this.number = number;
	}

	public String toString() {
		return "L" + number;
	}

	public Object accept(Visitor v) {
		return v.visit(this);
	}

}
