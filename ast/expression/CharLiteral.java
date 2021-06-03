package ast.expression;

import ast.visitors.Visitor;

public class CharLiteral extends Expression {

    public char value;

    public CharLiteral(char value, int l, int o) {
        super(l, o);
        this.value = value;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

}
