package ast.expression;

import ast.visitors.Visitor;

public class BooleanLiteral extends Expression {

    public boolean value;

    public BooleanLiteral(boolean value, int l, int o) {
        super(l, o);
        this.value = value;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

}
