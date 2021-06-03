package ast.type;

import ast.*;

public abstract class Type extends ASTNode {

	/*
	 * Methods that Jason has defined: public abstract Type accept (TypeVisitor v)
	 * throws SemanticException; public abstract Temp accept (TempVisitor v); public
	 * abstract boolean equals (Object o);
	 */

	public Type() {
		super();
	}

	public Type(int l, int o) {
		super(l, o);
	}

}