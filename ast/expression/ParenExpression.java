package ast.expression;

import ast.visitors.Visitor;

public class ParenExpression extends Expression {

    public Expression expr;

    public ParenExpression(Expression expr) {
        this.expr = expr;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

}
