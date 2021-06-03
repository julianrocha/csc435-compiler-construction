package ast;

import ast.expression.Identifier;
import ast.type.*;
import ast.visitors.Visitor;

public class VariableDeclaration extends ASTNode {

    public Type type;
    public Identifier id;

    public VariableDeclaration(Type type, Identifier id) {
        this.type = type;
        this.id = id;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
