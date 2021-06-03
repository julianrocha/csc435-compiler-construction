package ast.expression;

import ast.ASTNode;

public abstract class Expression extends ASTNode {

    public Expression() {
        super();
    }

    public Expression(int l, int o) {
        super(l, o);
    }
}
