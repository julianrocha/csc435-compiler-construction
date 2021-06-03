package ast;

import ast.visitors.Visitor;

public abstract class ASTNode {
	public int line;
	public int offset;

	public ASTNode() {
		line = -1;
		offset = -1;
	}

	public ASTNode(int l, int o) {
		line = l;
		offset = o;
	}

	public abstract Object accept(Visitor v);

}