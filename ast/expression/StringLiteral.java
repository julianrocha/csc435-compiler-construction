package ast.expression;

import ast.visitors.Visitor;

public class StringLiteral extends Expression {

    public String value;

    public StringLiteral(String value, int l, int o) {
        super(l, o);
        this.value = value;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

}
