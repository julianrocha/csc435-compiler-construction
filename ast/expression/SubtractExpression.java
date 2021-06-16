package ast.expression;

import ast.visitors.Visitor;

public class SubtractExpression extends Expression {

    public Expression lhsExpr;
    public Expression rhsExpr;

    public SubtractExpression(Expression lhsExpr, Expression rhsExpr) {
        super(rhsExpr.line, rhsExpr.offset);
        this.lhsExpr = lhsExpr;
        this.rhsExpr = rhsExpr;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

}
