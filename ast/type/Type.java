package ast.type;

import ast.*;

public abstract class Type extends ASTNode {

	public Type() {
		super();
	}

	public Type(int l, int o) {
		super(l, o);
	}

	public abstract String toString();

	public boolean equals(Type other) {
		return this.toString().equals(other.toString());
	}

}