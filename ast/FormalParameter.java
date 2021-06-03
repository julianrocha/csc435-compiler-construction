package ast;

import ast.expression.Identifier;
import ast.type.*;
import ast.visitors.Visitor;

public class FormalParameter extends ASTNode {

    public Type type;
    public Identifier id;

    public FormalParameter(Type type, Identifier id) {
        this.type = type;
        this.id = id;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
