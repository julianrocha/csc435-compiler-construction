package ast.statement;

import ast.ASTNode;

public abstract class Statement extends ASTNode {

    public Statement() {
        super();
    }

    public Statement(int l, int o) {
        super(l, o);
    }
}
