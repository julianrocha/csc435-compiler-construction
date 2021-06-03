package ast.expression;

import ast.visitors.Visitor;

public class EqualityExpression extends Expression {

    public Expression lhsExpr;
    public Expression rhsExpr;

    public EqualityExpression(Expression lhsExpr, Expression rhsExpr) {
        this.lhsExpr = lhsExpr;
        this.rhsExpr = rhsExpr;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

}
