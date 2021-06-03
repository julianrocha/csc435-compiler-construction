package ast.expression;

import ast.visitors.Visitor;

public class IntegerLiteral extends Expression {

    public int value;

    public IntegerLiteral(int value, int l, int o) {
        super(l, o);
        this.value = value;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

}
