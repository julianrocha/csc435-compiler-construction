package ast.expression;

import ast.visitors.Visitor;

public class ParenExpression extends Expression {

    public Expression expr;

    public ParenExpression(Expression expr) {
        super(expr.line, expr.offset);
        this.expr = expr;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

}
