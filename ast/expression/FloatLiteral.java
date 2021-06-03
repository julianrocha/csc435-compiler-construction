package ast.expression;

import ast.visitors.Visitor;

public class FloatLiteral extends Expression {

    public float value;

    public FloatLiteral(float value, int l, int o) {
        super(l, o);
        this.value = value;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

}
