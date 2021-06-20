package ast.expression;

import ast.visitors.Visitor;

public class ArrayReference extends Expression {

    public Identifier id;
    public Expression expr;

    public ArrayReference(Identifier id, Expression expr) {
        super(id.line, id.offset);
        this.id = id;
        this.expr = expr;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

}
